package com.company.resources.loaders;

import com.company.resources.file_system.Archive;

public class LoaderDialog extends Loader {
    private static LoaderDialog instance = null;

    private LoaderDialog() {
        super("res\\dialog", Archive.DIALOG);
    }

    public static LoaderDialog getInstance() {
        if (instance == null)
            instance = new LoaderDialog();
        return instance;
    }
}
