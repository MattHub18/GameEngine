package com.company.network;

public abstract class Network {
    private static final int PORT = 1024;

    public static void createServer() {
        new Server(PORT).start();
    }

    public static void participate() {
        new Client(PORT).start();
    }
}
