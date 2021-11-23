package com.company.resources.file_system;

public class FileSystem {

    private static FileSystem instance = null;
    private final Archive archive;

    private FileSystem() {
        archive = new Archive();
    }

    private FileSystem(Filter filter) {
        archive = new Archive();
        archive.load(filter);
    }

    public static FileSystem getInstance() {
        if (instance == null)
            instance = new FileSystem();
        return instance;
    }

    public static FileSystem getLoaderInstance() {
        if (instance == null)
            instance = new FileSystem(new SimpleFilter());
        return instance;
    }

    public void shutdown() {
        archive.close();
        instance = null;
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
