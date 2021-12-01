package com.company.resources.loaders;

import com.company.resources.file_system.Archive;

public class LoaderFont extends Loader {

    private static LoaderFont instance = null;

    private LoaderFont() {
        super("res\\font", Archive.FONT);
    }

    public static LoaderFont getInstance() {
        if (instance == null)
            instance = new LoaderFont();
        return instance;
    }
}
