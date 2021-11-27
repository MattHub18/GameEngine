package com.company.network;

import javax.swing.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Streaming {
    public static byte[] serialize(Object obj) {
        ByteArrayOutputStream array = null;
        try {
            array = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(array);
            out.writeObject(obj);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return array.toByteArray();
    }

    public static Object deserialize(byte[] data) {
        ByteArrayInputStream array = new ByteArrayInputStream(data);
        try {
            ObjectInputStream in = new ObjectInputStream(array);
            return in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public static void encrypt() {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress("google.com", 80));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "You are not connected to internet!");
        }
        char[] alpha = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        String address = socket.getLocalAddress().toString();
        address = address.substring(1);
        String[] numbers = address.split("\\.");
        StringBuilder sb = new StringBuilder();
        for (String number : numbers) {
            int num;
            try {
                num = Integer.parseInt(number);
            } catch (NumberFormatException e) {
                break;
            }

            sb.append(alpha[num / 16]);
            sb.append(alpha[num % 16]);
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Address", JOptionPane.INFORMATION_MESSAGE);
    }

    public static String decrypt(String address) {

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
}
