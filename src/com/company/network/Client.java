package com.company.network;

import com.company.network.packets.Packet;
import com.company.network.packets.PacketType;

import java.io.IOException;
import java.net.*;

public class Client implements Runnable {
    private final int port;
    private final int bufferSize;
    private final EntityList list;
    private InetAddress ipAddress;
    private DatagramSocket socket;
    private Thread thread;

    public Client(EntityList list, int port, int bufferSize) {
        this.list = list;
        this.port = port;
        this.bufferSize = bufferSize;
        try {
            this.socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void start(String ipAddress) {
        try {
            this.ipAddress = InetAddress.getByName(ipAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        thread = new Thread(this);
        thread.start();
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
            parsePacket(packet.getData());
        }
    }

    public void parsePacket(byte[] data) {
        Packet packet = (Packet) Streaming.deserialize(data);
        assert packet != null;
        PacketType type = packet.getPacketType();
        try {
            switch (type) {
                default:
                case INVALID:
                    break;
                case LOGIN:
                    //"Client [" + packet.getAddress() + " : " + packet.getPort() + "] has joined";
                    handleLogin(packet);
                    break;
                case DISCONNECT:
                    //"Client [" + packet.getAddress() + " : " + packet.getPort() + "] has left";
                    handleDisconnect(packet);
                    break;
                case MOVE:
                    handleMove(packet);
                    break;
                case SHUTDOWN:
                    //"Server has closed";
                    handleShutdown();
                    break;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void handleLogin(Packet packet) throws InterruptedException {
        EntityConnectionWrapper player = packet.getPlayer();
        list.add(player);
    }

    private void handleDisconnect(Packet packet) throws InterruptedException {
        EntityConnectionWrapper player = packet.getPlayer();
        list.remove(player);
    }

    private void handleMove(Packet packet) throws InterruptedException {
        EntityConnectionWrapper player = packet.getPlayer();
        list.update(player);
    }

    private void handleShutdown() {
        interrupt();
    }

    private void interrupt() {
        socket.close();
        thread.interrupt();
    }

    public void sendData(byte[] data) {
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
        try {
            socket.send(packet);
        } catch (IOException ignored) {
        }
    }
}
