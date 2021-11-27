package com.company.network.packets;

import com.company.network.Client;
import com.company.network.EntityConnectionWrapper;
import com.company.network.Server;
import com.company.network.Streaming;

import java.io.Serializable;
import java.net.InetAddress;

public class Packet implements Serializable {

    private final PacketType packetType;
    private final EntityConnectionWrapper player;

    public Packet(PacketType packetType, EntityConnectionWrapper player) {
        this.packetType = packetType;
        this.player = player;
    }

    public PacketType getPacketType() {
        return packetType;
    }

    public EntityConnectionWrapper getPlayer() {
        return player;
    }

    public void writeData(Client client) {
        client.sendData(getData());
    }

    public void writeData(Server server) {
        server.sendDataToAllClients(getData());
    }

    public byte[] getData() {
        return Streaming.serialize(this);
    }

    public InetAddress getAddress() {
        return player.getIpAddress();
    }

    public int getPort() {
        return player.getPort();
    }
}
