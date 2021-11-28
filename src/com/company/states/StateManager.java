package com.company.states;

import java.util.HashMap;

public abstract class StateManager {

    private State currentState;

    private final HashMap<String, State> stateMap;

    public StateManager() {
        currentState = null;
        stateMap = new HashMap<>();
        insertStates();
    }

    protected abstract void insertStates();

    protected void addState(String stateName, State state) {
        stateMap.put(stateName, state);
    }

    public State getCurrentState() {
        return currentState;
    }


    public abstract void init();


    public void nextState(String state) {
        currentState = stateMap.get(state);
    }

    public void clear() {
        stateMap.clear();
    }
}
