package com.company.network;

import com.company.entities.PlayerConnectionWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class EntityList {
    private final List<PlayerConnectionWrapper> entities = new ArrayList<>();
    private final Semaphore mutex = new Semaphore(1);

    public void add(PlayerConnectionWrapper player) throws InterruptedException {
        start();
        entities.add(player);
        finish();
    }

    public void remove(PlayerConnectionWrapper player) throws InterruptedException {
        start();
        entities.remove(indexOf(player));
        finish();
    }

    public void update(PlayerConnectionWrapper player) throws InterruptedException {
        start();
        int index = indexOf(player);
        if (index != -1)
            entities.get(index).update(player);
        finish();
    }

    public List<PlayerConnectionWrapper> get() throws InterruptedException {
        start();
        List<PlayerConnectionWrapper> eList = new ArrayList<>(entities);
        finish();
        return eList;
    }

    private void start() throws InterruptedException {
        mutex.acquire();
    }

    private void finish() {
        mutex.release();
    }

    private int indexOf(PlayerConnectionWrapper player) {
        int index = -1;
        for (PlayerConnectionWrapper p : entities) {
            ++index;
            if (p.getUniqueId() == player.getUniqueId())
                return index;
        }
        return -1;
    }

}
