package com.company.network;

import com.company.network.logs.LogScreen;
import com.company.network.logs.LogType;
import com.company.network.packets.Packet;
import com.company.network.packets.PacketType;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {
    private final List<EntityConnectionWrapper> connectedPlayers = new ArrayList<>();
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
        screen = LogScreen.getInstance(this);
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
        EntityConnectionWrapper player = packet.getPlayer();
        player.setIpAddress(address);
        player.setPort(port);
        PacketType type = packet.getPacketType();
        switch (type) {
            default:
            case INVALID:
                break;
            case LOGIN:
                screen.appendToPane("Client [" + address + " : " + port + "] has connected", LogType.PLAYER);
                addConnection(packet);
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

    private void addConnection(Packet packet) {
        boolean alreadyConnected = false;
        EntityConnectionWrapper player = packet.getPlayer();

        for (EntityConnectionWrapper e : connectedPlayers) {
            if (e.equals(player)) {
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

    public void removeConnection(Packet packet) {
        connectedPlayers.remove(getPlayer(packet.getPlayer()));
        packet.writeData(this);
    }

    private void handleMove(Packet packet) {
        EntityConnectionWrapper player = getPlayer(packet.getPlayer());
        if (player != null) {
            int oldRoom = player.getCurrentRoomId();
            if (player.getCurrentRoomId() == oldRoom) {
                player.update(packet.getPlayer());
                packet.writeData(this);
            }
        }
    }

    private EntityConnectionWrapper getPlayer(EntityConnectionWrapper player) {
        return search(connectedPlayers, player);
    }

    private EntityConnectionWrapper search(List<EntityConnectionWrapper> list, EntityConnectionWrapper player) {
        for (EntityConnectionWrapper pl : list) {
            if (pl.equals(player)) {
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
        connectedPlayers.clear();
        socket.close();
        thread.interrupt();
    }
}
