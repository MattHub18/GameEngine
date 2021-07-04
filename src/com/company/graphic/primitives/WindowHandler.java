package com.company.graphic.primitives;

import com.company.entities.human.Entity;
import com.company.network.Client;
import com.company.network.EntityConnectionWrapper;
import com.company.network.Server;
import com.company.network.packets.Packet;
import com.company.network.packets.PacketType;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowHandler extends WindowAdapter {
    private Entity player;
    private Client client;
    private JFrame frame;
    private Server server;

    @Override
    public void windowClosing(WindowEvent e) {
        if (client != null) {
            Packet packet = new Packet(PacketType.DISCONNECT, new EntityConnectionWrapper(player, (byte) -1));
            packet.writeData(client);
        }

        if (server != null) {
            Packet packet = new Packet(PacketType.SHUTDOWN, new EntityConnectionWrapper(player, (byte) -1));
            packet.writeData(server);
            server.interrupt();
        }

        GameLoop.running = false;
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public void setPlayer(Entity player) {
        this.player = player;
    }
}
