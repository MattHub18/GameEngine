package com.company.network;

import javax.swing.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;

public class Client implements Runnable {
    private final int port;
    private final ReadWriteThreadSafe helper;
    private Socket socket;
    private ObjectOutputStream streamOut;
    private ObjectInputStream streamIn;

    public Client(int port, ReadWriteThreadSafe helper) {
        this.port = port;
        this.helper = helper;
    }

    public void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        String address = "";
        while (address.equals("")) {
            address = JOptionPane.showInputDialog(null, "Enter address:", "Enter address:", JOptionPane.INFORMATION_MESSAGE);
        }
        address = decrypt(address);
        System.out.println("Finding server...\nConnecting ... ");
        try {
            socket = new Socket(address, port);
            System.out.println("Client connected");

            streamOut = new ObjectOutputStream(socket.getOutputStream());
            streamIn = new ObjectInputStream(socket.getInputStream());

            while (true) {
                if (socket.isConnected()) {
                    send(streamOut);
                    if (!receive(streamIn))
                        break;
                } else {
                    JOptionPane.showMessageDialog(null, "Server crashed! Recreate!", "ERROR", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (ConnectException e) {
            JOptionPane.showMessageDialog(null, "Server Not Found! Try again", "ERROR", JOptionPane.INFORMATION_MESSAGE);
            run();
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void send(ObjectOutputStream streamOut) throws InterruptedException, IOException {
        Object myObject = helper.getMyObject();
        streamOut.writeObject(myObject);
        streamOut.flush();
        streamOut.reset();
    }

    private boolean receive(ObjectInputStream streamIn) throws IOException, ClassNotFoundException, InterruptedException {
        Object obj = null;
        try {
            obj = streamIn.readObject();
        } catch (SocketException e) {
            System.out.println("Close Connection");
        } catch (EOFException e) {
            System.out.println("Server close");
            interrupt();
        }
        if (obj == null)
            return false;
        helper.setOtherObject(obj);
        return true;
    }

    private String decrypt(String address) {

        if (address.length() == 8) {

            Character[] hexVal = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
            ArrayList<Character> alpha = new ArrayList<>(Arrays.asList(hexVal));

            StringBuilder sb = new StringBuilder();

            address = address.toUpperCase();

            char[] addressCharArray = address.toCharArray();

            for (int i = 0; i < addressCharArray.length; i += 2) {
                int value = 16 * alpha.indexOf(addressCharArray[i]) + alpha.indexOf(addressCharArray[i + 1]);
                sb.append(value);
                if (i != 6)
                    sb.append(".");
            }
            return sb.toString();
        }
        return null;
    }

    public void interrupt() throws IOException {
        System.out.println("Disconnecting...");
        socket.close();
        streamIn.close();
        streamOut.close();
        System.out.println("Client disconnected");
        Thread.currentThread().interrupt();
    }
}