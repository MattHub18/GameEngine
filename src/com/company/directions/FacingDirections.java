package com.company.directions;

public enum FacingDirections {
    FRONT((byte) 0), LEFT((byte) 1), BACK((byte) 2), RIGHT((byte) 3);

    public byte value;

    FacingDirections(byte value) {
        this.value = value;
    }
}
