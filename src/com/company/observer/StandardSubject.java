package com.company.observer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class StandardSubject implements Subject, Serializable {
    private final HashMap<String, Observer> observers;

    public StandardSubject() {
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
    public <T> void notifyChange(String name, T value) {
        observers.get(name).updateValue(value);
    }

    @Override
    public void clearObserver(Subject subject) {
        Iterator<Map.Entry<String, Observer>> observerIterator = observers.entrySet().iterator();
        while (observerIterator.hasNext()) {
            Map.Entry<String, Observer> observer = observerIterator.next();
            observerIterator.remove();
            observer.getValue().unregisterEntityToObserver(subject);
        }
    }
}
