package com.company.network.packets;

import com.company.entities.PlayerConnectionWrapper;
import com.company.network.Client;
import com.company.network.Server;
import com.company.resources.Streaming;

import java.io.Serializable;
import java.net.InetAddress;

public class Packet implements Serializable {

    private final PacketType packetId;
    private final PlayerConnectionWrapper player;

    public Packet(PacketType packetId, PlayerConnectionWrapper player) {
        this.packetId = packetId;
        this.player = player;
    }

    public PacketType getPacketId() {
        return packetId;
    }

    public PlayerConnectionWrapper getPlayer() {
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
