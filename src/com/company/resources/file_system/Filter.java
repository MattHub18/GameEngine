package com.company.resources.file_system;

import java.io.*;
import java.util.ArrayList;

public abstract class Filter {

    protected abstract Object decrypt(Object data);

    protected abstract Object encrypt(Object object);

    private Object read(String filename) {
        try {
            return new ObjectInputStream(new FileInputStream(filename)).readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void write(String filename, Object obj) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(obj);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeData(String path, Object object) {
        Object raw = encrypt(object);
        write(path, raw);
    }

    public ArrayList<Object> readData() {
        ArrayList<Object> data = new ArrayList<>();
        for (String filename : Archive.DATA)
            data.add(decrypt(read(filename)));
        return data;
    }
}
