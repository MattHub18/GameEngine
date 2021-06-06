package com.company.network;

import com.company.entities.PlayerConnectionWrapper;
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
import java.util.List;

public class Server implements Runnable {
    private final List<PlayerConnectionWrapper> connectedPlayers = new ArrayList<>();
    private DatagramSocket socket;
    private Thread thread;
    private LogScreen screen;

    public Server(int port) {
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
            byte[] data = new byte[2048];
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
                PlayerConnectionWrapper player = packet.getPlayer();
                player.setIpAddress(address);
                player.setPort(port);
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

    public void addConnection(Packet packet) {
        boolean alreadyConnected = false;
        PlayerConnectionWrapper player = packet.getPlayer();

        for (PlayerConnectionWrapper e : connectedPlayers) {
            if (e.getUniqueId() == player.getUniqueId()) {
                if (e.getIpAddress() == null)
                    e.setIpAddress(player.getIpAddress());
                if (e.getPort() == -1)
                    e.setPort(player.getPort());

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
        PlayerConnectionWrapper player = getPlayer(packet.getPlayer());
        if (player != null) {
            player.update(packet.getPlayer());
            packet.writeData(this);
        }
    }

    private PlayerConnectionWrapper getPlayer(PlayerConnectionWrapper player) {
        for (PlayerConnectionWrapper pl : connectedPlayers) {
            if (pl.getUniqueId() == player.getUniqueId()) {
                return pl;
            }
        }
        return null;
    }

    public void sendDataToAllClients(byte[] data) {
        for (PlayerConnectionWrapper pl : connectedPlayers)
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
}
