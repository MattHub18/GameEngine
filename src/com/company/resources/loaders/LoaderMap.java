package com.company.resources.loaders;

import com.company.resources.file_system.Archive;

public class LoaderMap extends Loader {
    private static LoaderMap instance = null;

    private LoaderMap() {
        super("res\\map", Archive.MAP);
    }

    public static LoaderMap getInstance() {
        if (instance == null)
            instance = new LoaderMap();
        return instance;
    }
}
