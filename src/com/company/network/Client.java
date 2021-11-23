package com.company.network;

import com.company.ai.AiInterface;
import com.company.graphic.Graphic;
import com.company.graphic.gfx.Font;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.graphic.primitives.Window;
import com.company.network.packets.Packet;
import com.company.network.packets.PacketType;
import com.company.resources.Streaming;

import java.io.IOException;
import java.net.*;

public class Client implements Runnable, Graphic {
    private final int port;
    private final int bufferSize;
    private final int maxTimeToLive;
    private final int color;
    private final EntityList list;
    private InetAddress ipAddress;
    private DatagramSocket socket;
    private Thread thread;
    private String message;
    private boolean running;
    private int messageTimeToLive;

    public Client(EntityList list, int port, int bufferSize, int ttl, int color) {
        this.list = list;
        this.port = port;
        this.bufferSize = bufferSize;
        this.messageTimeToLive = ttl;
        this.maxTimeToLive = ttl;
        this.color = color;
        try {
            this.socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        running = false;
    }

    public void start(String ipAddress) {
        try {
            this.ipAddress = InetAddress.getByName(ipAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        thread = new Thread(this);
        thread.start();
        running = true;
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
                    if (!(packet.getPlayer().getEntity() instanceof AiInterface))
                        message = "Client [" + packet.getAddress() + " : " + packet.getPort() + "] has joined";
                    handleLogin(packet);
                    break;
                case DISCONNECT:
                    message = "Client [" + packet.getAddress() + " : " + packet.getPort() + "] has left";
                    handleDisconnect(packet);
                    break;
                case MOVE:
                    handleMove(packet);
                    break;
                case SHUTDOWN:
                    message = "Server has closed";
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
        running = false;
    }

    public void sendData(byte[] data) {
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
        try {
            socket.send(packet);
        } catch (IOException ignored) {
        }
    }

    @Override
    public void update(GameLoop gl, float dt) {

        if (messageTimeToLive <= 0) {
            message = null;
            messageTimeToLive = maxTimeToLive;
        } else
            --messageTimeToLive;
    }

    @Override
    public void render(GameLoop gl, Render r) {
        if (message != null)
            r.addFont(new Font("res/font/client.png", message, Window.WIDTH / 5, 0, color));

    }

    public boolean isRunning() {
        return running;
    }
}
