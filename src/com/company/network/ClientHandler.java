package com.company.network;

import javax.swing.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final List<ClientHandler> clients;
    private ObjectOutputStream streamOut;
    private boolean readList = false;
    private Object objectToSend = null;
    private ObjectInputStream streamIn;

    public ClientHandler(Socket socket, List<ClientHandler> clients) {
        this.socket = socket;
        this.clients = clients;
        try {
            streamOut = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("Client connected to server");
        try {
            streamIn = new ObjectInputStream(socket.getInputStream());

            while (true) {
                if (socket.isConnected()) {

                    Object obj = null;
                    try {
                        obj = streamIn.readObject();
                    } catch (SocketException | EOFException e) {
                        System.out.println("Client is disconnected");
                        removeClient(this);
                    }

                    if (obj == null)
                        break;

                    readList = true;
                    cycleList(obj);

                } else {
                    JOptionPane.showMessageDialog(null, "Disconnected from the server! Try again", "ERROR", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void write(ClientHandler ch, Object obj) throws IOException, InterruptedException {

        if (!ch.equals(this)) {
            ch.write(obj);
            streamWrite(ch);
        }
    }

    private void streamWrite(ClientHandler ch) throws IOException, InterruptedException {
        Object obj = ch.read();
        ch.streamOut.writeObject(obj);
        ch.streamOut.flush();
        ch.streamOut.reset();
    }

    public synchronized void addClient(ClientHandler ch) throws InterruptedException {
        while (readList)
            wait();
        readList = true;
        clients.add(ch);
        notifyAll();
    }

    private synchronized void removeClient(ClientHandler ch) throws InterruptedException {
        while (readList)
            wait();
        readList = true;
        clients.remove(ch);
        notifyAll();
    }

    private synchronized void cycleList(Object obj) throws InterruptedException, IOException {
        while (!readList)
            wait();

        for (ClientHandler ch : clients) {
            write(ch, obj);
        }

        readList = false;
        notifyAll();
    }

    public void interrupt() throws IOException {
        socket.close();
        streamOut.close();
        streamIn.close();
    }

    private synchronized void write(Object object) throws InterruptedException {
        while (this.objectToSend != null)
            wait();
        this.objectToSend = object;
        notifyAll();
    }

    private synchronized Object read() throws InterruptedException {
        while (this.objectToSend == null)
            wait();
        Object o = ((SharedObject) objectToSend).copy(objectToSend);
        objectToSend = null;
        notifyAll();
        return o;
    }
}
