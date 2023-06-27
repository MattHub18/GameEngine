package com.company.observer;

public interface Subject {
    void addObserver(String name, Observer observer);

    void removeObserver(String name);

    void notifyChange(String name, Object value);

    void clearObserver();
}
