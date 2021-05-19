package com.company.network;

import javax.swing.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private final int port;
    private final List<ClientHandler> clients = new ArrayList<>();
    ServerSocket serverSocket;

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        encrypt();
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server has started to running.\nWaiting for connection...");
            do {
                Socket socket;
                System.out.println("Waiting for a player...");
                try {
                    socket = serverSocket.accept();
                    ClientHandler client = new ClientHandler(socket, clients);
                    client.addClient(client);
                    System.out.println("Player " + clients.size() + " connected");
                    new Thread(client).start();
                } catch (SocketException e) {
                    System.out.println("Shutdown server");
                    for (ClientHandler ch : clients) {
                        ch.interrupt();
                    }
                    clients.clear();
                }
            } while (!clients.isEmpty());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
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


    public void interrupt() throws IOException {
        serverSocket.close();
        Thread.currentThread().interrupt();
    }
}
