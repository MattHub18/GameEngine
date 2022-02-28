package com.company.resources.file_system;

import java.util.ArrayList;

public class Archive {
    public static final ArrayList<String> DATA = new ArrayList<>();
    public static final ArrayList<String> SOUND = new ArrayList<>();
    public static final ArrayList<String> TEXTURES = new ArrayList<>();
    public static final ArrayList<String> FONT = new ArrayList<>();
    public static final ArrayList<String> MAP = new ArrayList<>();
    public static final ArrayList<String> DIALOG = new ArrayList<>();
    private ArrayList<Object> data;

    public void close() {
        if (data != null)
            data.clear();
        DATA.clear();
    }

    public void saveData(Filter filter, String path, Object object) {
        filter.writeData(path, object);
    }

    public void load() {
        new Loader("res\\texture", TEXTURES).load();
        new Loader("res\\audio", SOUND).load();
        new Loader("res\\font", FONT).load();
        new Loader("res\\map", MAP).load();
        new Loader("res\\dialog", DIALOG).load();
    }

    public void loadData(Filter filter) {
        new Loader("saves", Archive.DATA).load();
        data = new ArrayList<>();
        data.addAll(filter.readData());
    }

    public boolean exist(String objectName) {
        return DATA.stream().anyMatch(s -> s.contains(objectName));
    }

    public Object loadObject(String objectName) {
        for (int i = 0; i < DATA.size(); i++) {
            if (DATA.get(i).contains(objectName))
                return data.get(i);
        }
        return null;
    }
}
