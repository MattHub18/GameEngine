package com.company.event;

import java.io.*;
import java.util.Properties;

public abstract class Event {
    private static final Properties events = new Properties();
    private static final String path = "res/event/events.xml";

    public static void loadEvents() {
        try {
            InputStream read = new FileInputStream(path);
            events.loadFromXML(read);
            read.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isDone(String key) {
        return Boolean.parseBoolean(events.getProperty(key));
    }

    public static void updateEvent(String key) {
        store(key, "true");
    }

    public static void resetEvents() {
        for (String key : events.stringPropertyNames())
            store(key, "false");
    }

    private static void store(String key, String val) {
        try {
            events.setProperty(key, val);
            OutputStream write = new FileOutputStream(path);
            events.storeToXML(write, null);
            write.close();
            loadEvents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
