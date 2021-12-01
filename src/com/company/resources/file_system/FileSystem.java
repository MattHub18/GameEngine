package com.company.resources.file_system;

public class FileSystem {

    private static FileSystem instance = null;
    private static FileSystem dataInstance = null;
    private final Archive archive;

    private FileSystem() {
        archive = new Archive();
    }

    private FileSystem(Filter filter) {
        archive = new Archive();
        archive.loadData(filter);
    }

    public static FileSystem getInstance() {
        if (instance == null)
            instance = new FileSystem();
        return instance;
    }

    public static FileSystem getDataInstance() {
        if (dataInstance == null)
            dataInstance = new FileSystem(new SimpleFilter());
        return dataInstance;
    }

    public void loadResources() {
        archive.load();
    }

    public void shutdown() {
        archive.close();
        instance = null;
        dataInstance = null;
    }

    public void save(Object gameObject) {
        String path = "saves\\" + gameObject.getClass().getSimpleName() + ".data";
        Filter filter = new SimpleFilter();
        archive.saveData(filter, path, gameObject);
    }

    public Object get(String objectName) {
        if (archive.exist(objectName))
            return archive.loadObject(objectName);
        else
            return null;
    }
}
