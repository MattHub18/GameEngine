package com.company.network;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ServerConnection implements Runnable {

    private ObjectInputStream objIn;
    private final Socket socket;

    public ServerConnection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //System.out.println("Connected a player...");
        try {
            objIn = new ObjectInputStream(socket.getInputStream());
            if (socket.isConnected()) {
                while (true) {
                    NetworkObjectWrapper obj = (NetworkObjectWrapper) objIn.readObject();
                    if (obj == null)
                        break;
                    obj.setObjectGame(obj.getObjectGame());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Server crashed! Recreate!", "ERROR", JOptionPane.INFORMATION_MESSAGE);
                run();
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                objIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
