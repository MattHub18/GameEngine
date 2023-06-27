package com.company.resources.file_system;

public class FileSystem {
    private static FileSystem instance;
    private final Archive archive;
    private final Filter filter;

    private FileSystem(Filter filter) {
        this.archive = new Archive();
        this.filter = filter;
    }

    public static FileSystem getInstance(Filter filter) {
        if (instance == null)
            instance = new FileSystem(filter);
        return instance;
    }

    public void loadResources() {
        archive.load();
    }

    public void shutdown() {
        archive.close();
        instance = null;
    }

    public void save(String name, Object gameObject) {
        String path = "saves\\" + name + ".data";
        archive.saveData(filter, path, gameObject);
    }

    public Object get(String objectName) {
        archive.loadData(filter);
        if (archive.exist(objectName))
            return archive.loadObject(objectName);
        else
            return null;
    }
}
