package com.company.states;

import com.company.graphic.primitives.Stack;

import java.util.HashMap;

public abstract class StateManager {

    private final Stack gameStack;

    private final HashMap<String, State> stateMap;

    public StateManager() {
        gameStack = new Stack();
        stateMap = new HashMap<>();
        insertStates();
    }

    public State head() {
        return gameStack.getTop();
    }

    public void remove() {
        gameStack.pop();
    }

    public void insert(String state) {
        gameStack.push(stateMap.get(state));
    }

    protected abstract void insertStates();

    protected void addState(String stateName, State state) {
        stateMap.put(stateName, state);
    }

    public abstract void init();

    public void clear() {
        gameStack.clear();
    }
}
