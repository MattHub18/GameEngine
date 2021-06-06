package com.company.entities;

import com.company.graphic.Graphic;
import com.company.graphic.primitives.GameLoop;
import com.company.graphic.primitives.Render;
import com.company.zzzfake.fakeobjects.FakePlayer;

import java.io.Serializable;
import java.net.InetAddress;

public class PlayerConnectionWrapper implements Serializable, Graphic {

    private Entity entity;
    private InetAddress ipAddress;
    private int port;

    public PlayerConnectionWrapper(Entity entity) {
        this.entity = entity;
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

    public void update(PlayerConnectionWrapper player) {
        if (player.entity instanceof FakePlayer)
            entity = new FakePlayer((FakePlayer) player.entity);//todo try to abstract
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

