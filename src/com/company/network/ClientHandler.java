package com.company.network;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    private final ArrayList<ClientHandler> clients;
    private ObjectInputStream objIn;
    private ObjectOutputStream objOut;
    private final Socket socket;

    public ClientHandler(Socket socket, ArrayList<ClientHandler> clients) throws IOException {
        this.socket = socket;
        this.clients = clients;
    }

    @Override
    public void run() {
        try {
            //System.out.println("Connected to server...");
            objIn = new ObjectInputStream(socket.getInputStream());
            objOut = new ObjectOutputStream(socket.getOutputStream());
            if (socket.isConnected()) {
                while (true) {
                    NetworkObjectWrapper obj = (NetworkObjectWrapper) objIn.readObject();
                    if (obj == null)
                        break;

                    for (ClientHandler ch : clients) {
                        ch.objOut.writeObject(obj);
                        ch.objOut.reset();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Disconnected from the server! Try again", "ERROR", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                objOut.close();
                objIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
