package com.company.resources.loaders;

import com.company.resources.file_system.Archive;

import java.io.File;

public class LoaderResources extends Loader {

    private static LoaderResources instance = null;

    private LoaderResources() {
        super("res\\texture", Archive.TEXTURES);
    }

    public static LoaderResources getInstance() {
        if (instance == null)
            instance = new LoaderResources();
        return instance;
    }

    @Override
    protected void extractResources(File[] arr, int index) {
        if (index == arr.length)
            return;

        if (arr[index].isFile())
            filenames.add(arr[index].getAbsolutePath());
        else if (arr[index].isDirectory()) {
            if (!arr[index].getName().contains("font")) {
                File[] files = arr[index].listFiles();
                if (files != null)
                    extractResources(files, 0);
            }
        }
        extractResources(arr, ++index);
    }
}
