package com.company.network;

import com.company.entities.human.Entity;

import java.io.Serializable;
import java.net.InetAddress;

public class EntityConnectionWrapper implements Serializable {

    private Entity entity;
    private int currentRoomId;
    private InetAddress ipAddress;
    private int port;

    public EntityConnectionWrapper(Entity entity, int currentRoomId) {
        this.entity = entity;
        this.currentRoomId = currentRoomId;
        this.ipAddress = null;
        this.port = -1;
    }

    public Entity getEntity() {
        return entity;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(InetAddress ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getUniqueId() {
        return entity.getUniqueId();
    }

    public void update(EntityConnectionWrapper player) {
        currentRoomId = player.currentRoomId;
        entity = entity.copy(player.entity);
    }

    public int getCurrentRoomId() {
        return currentRoomId;
    }
}

