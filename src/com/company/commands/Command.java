package com.company.commands;

import java.io.Serializable;

public abstract class Command implements Serializable {

    private final int key;
    private final InputMode mode;
    private final CommandType type;

    public Command(CommandType type, int key, InputMode mode) {
        this.key = key;
        this.mode = mode;
        this.type = type;
    }

    public abstract <T> void execute(T element);

    public int getKey() {
        return key;
    }

    public InputMode getMode() {
        return mode;
    }

    public CommandType getType() {
        return type;
    }
}
