package com.company.resources.file_system;

import com.company.resources.loaders.*;

import java.util.ArrayList;

public class Archive {
    public static final ArrayList<String> DATA = new ArrayList<>();
    public static final ArrayList<String> SOUND = new ArrayList<>();
    public static final ArrayList<String> TEXTURES = new ArrayList<>();
    public static final ArrayList<String> FONT = new ArrayList<>();
    public static final ArrayList<String> MAP = new ArrayList<>();
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
        LoaderResource.getInstance().load();
        LoaderAudio.getInstance().load();
        LoaderFont.getInstance().load();
        LoaderMap.getInstance().load();
    }

    public void loadData(Filter filter) {
        LoaderData.getInstance().load();
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
