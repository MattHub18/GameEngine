package com.company.graphic.primitives;

import com.company.states.State;

import java.util.ArrayList;

public class Stack {
    private final ArrayList<State> gameStack;
    private int tos;

    public Stack() {
        tos = -1;
        gameStack = new ArrayList<>();
    }

    public void push(State game) {
        tos++;
        gameStack.add(tos, game);
    }

    public void pop() {
        gameStack.remove(tos);
        tos--;
    }

    public State getTop() {
        return gameStack.get(tos);
    }
}
