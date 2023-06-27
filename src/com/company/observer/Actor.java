package com.company.observer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Actor implements Subject {
    private final HashMap<String, Observer> observers;

    public Actor() {
        this.observers = new HashMap<>();
    }

    @Override
    public void addObserver(String name, Observer observer) {
        observers.put(name, observer);
    }

    @Override
    public void removeObserver(String name) {
        observers.remove(name);
    }

    @Override
    public void notifyChange(String name, Object value) {
        Observer observer = observers.get(name);
        if (observer != null)
            observer.updateValue(value);
    }

    @Override
    public void clearObserver() {
        Iterator<Map.Entry<String, Observer>> observerIterator = observers.entrySet().iterator();
        while (observerIterator.hasNext()) {
            Map.Entry<String, Observer> observer = observerIterator.next();
            observerIterator.remove();
            observer.getValue().unregisterEntityToObserver(this);
        }
    }
}
