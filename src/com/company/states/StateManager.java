package com.company.states;

public abstract class StateManager {

    protected State currentState;
    protected State gameState;

    public StateManager() {
        currentState = null;
        gameState = null;
    }

    public State getCurrentState() {
        return currentState;
    }

    public abstract void init();

    public void nextState(String state) {
        currentState = createNewState(state);
    }

    protected abstract State createNewState(String state);
}
