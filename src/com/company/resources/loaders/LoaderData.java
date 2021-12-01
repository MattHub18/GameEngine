package com.company.resources.loaders;

import com.company.resources.file_system.Archive;

public class LoaderData extends Loader {
    private static LoaderData instance = null;

    private LoaderData() {
        super("saves", Archive.DATA);
    }

    public static LoaderData getInstance() {
        if (instance == null)
            instance = new LoaderData();
        return instance;
    }
}
