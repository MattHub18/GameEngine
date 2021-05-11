package com.company.resources;

import java.io.*;

public class SaveNLoad {
    public static void writeToFile(String filename, Object obj) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(obj);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object readFile(String filename) {
        try {
            return new ObjectInputStream(new FileInputStream(filename)).readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
