package com.company.observer;

public interface Observer {
    void registerEntityToObserver(Subject subject);

    void unregisterEntityToObserver(Subject subject);

    void updateValue(Object value);
}
