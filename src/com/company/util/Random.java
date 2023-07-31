package com.company.util;

public class Random {
    private long seed;

    public Random(long seed) {
        this.seed = seed;
    }

    public Random() {
        this(0);
    }

    public int nextInt() {
        seed = (1103515245 * seed + 12345) % (1L << 31);
        return (int) seed;
    }

    public int nextInt(int lowerbound, int upperbound) {
        int value = nextInt();
        return (value % (upperbound - lowerbound - 1)) + lowerbound;
    }
}
