package com.company.states;

import java.util.HashMap;

public abstract class StateManager {

    protected final HashMap<String, State> stateMap;
    protected final HashMap<String, String> roomToWorld;
    protected State currentState;

    public StateManager() {
        currentState = null;
        stateMap = new HashMap<>();
        roomToWorld = new HashMap<>();
        insertStates();
        mapNames();
    }

    protected abstract void mapNames();

    protected void addRoomWorld(String room, String world) {
        roomToWorld.put(room, world);
    }

    protected abstract void insertStates();

    protected void addState(String stateName) {
        stateMap.put(stateName, null);
    }

    public State getCurrentState() {
        return currentState;
    }

    public abstract void init();

    public void nextState(String state) {
        currentState = stateMap.get(state);
        if (currentState == null)
            currentState = createNewState(state);
    }

    protected abstract State createNewState(String state);

    public void clear() {
        stateMap.clear();
    }

    protected void empty() {
        for (String key : stateMap.keySet())
            stateMap.replace(key, null);
    }
}
