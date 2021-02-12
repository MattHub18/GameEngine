package com.company.network;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class Client {
    private final int port;
    private NetworkObjectWrapper obj;

    public Client(int port) {
        this.port = port;
        obj = null;
    }

    public void start() {
        String address = JOptionPane.showInputDialog(null, "Enter address:", "Enter address:", JOptionPane.INFORMATION_MESSAGE);
        if (address != null) {
            address = decrypt(address);
            //System.out.println("Finding server...\nConnecting ... ");
            try {
                Socket socket = new Socket(address, port);
                //System.out.println("Client is connecting...");
                ServerConnection serverConn = new ServerConnection(socket);
                //System.out.println("Client connected");
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                new Thread(serverConn).start();

                while (obj != null) {
                    out.writeObject(obj);
                    out.reset();
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Server Not Found! Try again", "ERROR", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private String decrypt(String address) {

        if (address.length() == 8) {

            Character[] hexVal = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
            ArrayList<Character> alpha = new ArrayList<>(Arrays.asList(hexVal));

            StringBuilder sb = new StringBuilder();

            address = address.toUpperCase();

            char[] addr = address.toCharArray();

            for (int i = 0; i < addr.length; i += 2) {
                int value = 16 * alpha.indexOf(addr[i]) + alpha.indexOf(addr[i + 1]);
                sb.append(value);
                if (i != 6)
                    sb.append(".");
            }
            return sb.toString();
        }
        return null;
    }

    public void setObj(NetworkObjectWrapper obj) {
        this.obj = obj;
    }
}
