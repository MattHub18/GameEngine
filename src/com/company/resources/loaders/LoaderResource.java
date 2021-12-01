package com.company.resources.loaders;

import com.company.resources.file_system.Archive;

public class LoaderResource extends Loader {

    private static LoaderResource instance = null;

    private LoaderResource() {
        super("res\\texture", Archive.TEXTURES);
    }

    public static LoaderResource getInstance() {
        if (instance == null)
            instance = new LoaderResource();
        return instance;
    }
}
