package com.company.observer;

public interface Subject {
    void addObserver(String name, Observer observer);

    void removeObserver(String name);

    <T> void notifyChange(String name, T value);

    void clearObserver(Subject subject);
}
