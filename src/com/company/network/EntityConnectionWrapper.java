package com.company.network;

import com.company.entities.human.Enemy;
import com.company.entities.human.Entity;
import com.company.entities.human.Player;
import com.company.graphic.Graphic;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;

import java.io.Serializable;
import java.net.InetAddress;

public class EntityConnectionWrapper implements Serializable, Graphic {

    private Entity entity;
    private InetAddress ipAddress;
    private int port;

    private int currentRoomId;

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
        if (player.entity instanceof Player)
            entity = new Player((Player) player.entity);
        else if (player.entity instanceof Enemy)
            entity = new Enemy((Enemy) player.entity);
    }

    public int getCurrentRoomId() {
        return currentRoomId;
    }

    @Override
    public void update(GameLoop gl, float dt) {
        entity.update(gl, dt);
    }

    @Override
    public void render(GameLoop gl, Render r) {
        entity.render(gl, r);
    }
}

