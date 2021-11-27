package com.company.network;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class EntityList {
    private final List<EntityConnectionWrapper> entities = new ArrayList<>();
    private final Semaphore mutex = new Semaphore(1);

    public void add(EntityConnectionWrapper player) throws InterruptedException {
        start();
        entities.add(player);
        finish();
    }

    public void remove(EntityConnectionWrapper player) throws InterruptedException {
        start();
        entities.remove(indexOf(player));
        finish();
    }

    public void update(EntityConnectionWrapper player) throws InterruptedException {
        start();
        int index = indexOf(player);
        if (index != -1)
            entities.get(index).update(player);
        finish();
    }

    public List<EntityConnectionWrapper> get() throws InterruptedException {
        start();
        List<EntityConnectionWrapper> eList = new ArrayList<>(entities);
        finish();
        return eList;
    }

    private void start() throws InterruptedException {
        mutex.acquire();
    }

    private void finish() {
        mutex.release();
    }

    private int indexOf(EntityConnectionWrapper player) {
        for (EntityConnectionWrapper p : entities) {
            if (p.equals(player))
                return entities.indexOf(p);
        }
        return -1;
    }

}
