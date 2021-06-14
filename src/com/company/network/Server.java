package com.company.network;

import com.company.entities.human.Enemy;
import com.company.entities.human.Entity;
import com.company.entities.human.Player;
import com.company.network.logs.LogScreen;
import com.company.network.logs.LogType;
import com.company.network.packets.Packet;
import com.company.network.packets.PacketType;
import com.company.resources.Streaming;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server implements Runnable {
    private final List<EntityConnectionWrapper> connectedPlayers = new ArrayList<>();
    private final Map<Integer, List<EntityConnectionWrapper>> enemies = new HashMap<>();
    private DatagramSocket socket;
    private Thread thread;
    private LogScreen screen;
    private final int bufferSize;

    public Server(int port, int bufferSize) {
        this.bufferSize = bufferSize;
        try {
            this.socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        screen = LogScreen.getInstance();
        Streaming.encrypt();
        thread = new Thread(this);
        thread.start();
        screen.appendToPane("Server has started", LogType.SERVER);
    }

    public void run() {
        while (true) {
            byte[] data = new byte[bufferSize];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                break;
            }
            parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
        }
    }

    public void parsePacket(byte[] data, InetAddress address, int port) {
        Packet packet = (Packet) Streaming.deserialize(data);
        assert packet != null;
        PacketType type = packet.getPacketId();
        switch (type) {
            default:
            case INVALID:
                break;

            case LOGIN:
                screen.appendToPane("Client [" + address + " : " + port + "] has connected", LogType.PLAYER);
                addConnection(packet, address, port);
                sendEnemy(packet);
                break;

            case DISCONNECT:
                screen.appendToPane("Client [" + address + " : " + port + "] has disconnected", LogType.PLAYER);
                removeConnection(packet);
                break;

            case MOVE:
                handleMove(packet);
                break;
        }
    }

    public void addConnection(Packet packet, InetAddress address, int port) {
        boolean alreadyConnected = false;
        EntityConnectionWrapper player = packet.getPlayer();
        player.setIpAddress(address);
        player.setPort(port);

        for (EntityConnectionWrapper e : connectedPlayers) {
            if (e.getUniqueId() == player.getUniqueId()) {
                alreadyConnected = true;
            } else {
                sendData(packet.getData(), e.getIpAddress(), e.getPort());
                packet = new Packet(PacketType.LOGIN, e);
                sendData(packet.getData(), player.getIpAddress(), player.getPort());
            }
        }
        if (!alreadyConnected) {
            connectedPlayers.add(player);
        }
    }

    private void sendEnemy(Packet packet) {
        int currentRoomId = packet.getPlayer().getCurrentRoomId();

        for (EntityConnectionWrapper e : enemies.get(currentRoomId)) {
            Packet sendEnemy = new Packet(PacketType.LOGIN, e);
            sendData(sendEnemy.getData(), packet.getAddress(), packet.getPort());
        }
    }

    public void removeConnection(Packet packet) {
        connectedPlayers.remove(getPlayer(packet.getPlayer()));
        packet.writeData(this);
    }

    private void handleMove(Packet packet) {
        EntityConnectionWrapper player = getPlayer(packet.getPlayer());
        if (player != null) {
            int oldRoomId = player.getCurrentRoomId();
            player.update(packet.getPlayer());
            packet.writeData(this);
            if (player.getEntity() instanceof Player) {
                if (player.getCurrentRoomId() != oldRoomId)
                    sendEnemy(new Packet(PacketType.INVALID, player));
            }
        }
    }

    private EntityConnectionWrapper getPlayer(EntityConnectionWrapper player) {
        if (player.getEntity() instanceof Player)
            return search(connectedPlayers, player);
        else if (player.getEntity() instanceof Enemy)
            return search(enemies.get(player.getCurrentRoomId()), player);
        return null;
    }

    private EntityConnectionWrapper search(List<EntityConnectionWrapper> list, EntityConnectionWrapper player) {
        for (EntityConnectionWrapper pl : list) {
            if (pl.getUniqueId() == player.getUniqueId()) {
                return pl;
            }
        }
        return null;
    }

    public void sendDataToAllClients(byte[] data) {
        for (EntityConnectionWrapper pl : connectedPlayers)
            sendData(data, pl.getIpAddress(), pl.getPort());
    }

    private void sendData(byte[] data, InetAddress ipAddress, int port) {
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void interrupt() {
        socket.close();
        screen.interrupt();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }

    public void insertEnemy(int i, List<Entity> entities) {
        List<EntityConnectionWrapper> pcwEntities = null;
        if (entities != null) {
            pcwEntities = new ArrayList<>();
            for (Entity e : entities)
                pcwEntities.add(new EntityConnectionWrapper(e, i));
        }
        enemies.put(i, pcwEntities);
    }
}
