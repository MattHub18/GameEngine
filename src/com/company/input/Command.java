package com.company.input;

public abstract class Command {

    private final int key;
    public static final byte KEYBOARD = 0;
    public static final byte MOUSE = 1;
    public static final byte BASIC = 0;
    public static final byte UP = 1;
    public static final byte DOWN = -1;
    private final byte mode;
    private final byte type;

    public Command(byte type, int key, byte mode) {
        this.key = key;
        this.mode = mode;
        this.type = type;
    }

    public abstract void execute(Object element);

    public int getKey() {
        return key;
    }

    public byte getMode() {
        return mode;
    }

    public byte getType() {
        return type;
    }
}
