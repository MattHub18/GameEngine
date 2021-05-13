package com.company.network;

import javax.swing.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static final ArrayList<ClientHandler> clients = new ArrayList<>();
    private final int port;

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        encrypt();
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server has started to running.\nWaiting for a player...\nWaiting for connection...");
            do {
                socket = serverSocket.accept();
                System.out.println("Player connected");
                ClientHandler clientThread = new ClientHandler(socket, clients);
                clients.add(clientThread);
                new Thread(clientThread).start();
            } while (!clients.isEmpty());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null)
                    socket.close();
                if (serverSocket != null)
                    serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void encrypt() {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress("google.com", 80));
        } catch (IOException e) {
            e.printStackTrace();
        }
        char[] alpha = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        String address = socket.getLocalAddress().toString();
        address = address.substring(1);
        String[] numbers = address.split("\\.");
        StringBuilder sb = new StringBuilder();
        for (String number : numbers) {
            int num = Integer.parseInt(number);
            sb.append(alpha[num / 16]);
            sb.append(alpha[num % 16]);
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Address", JOptionPane.INFORMATION_MESSAGE);
    }
}
