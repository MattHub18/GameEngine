package com.company.resources.loaders;

import java.io.File;
import java.util.ArrayList;

public abstract class Loader {

    private final String rootDir;
    protected ArrayList<String> filenames;

    public Loader(String rootDir, ArrayList<String> filenames) {
        this.rootDir = rootDir;
        this.filenames = filenames;
    }

    public void load() {
        File mainDir = new File(rootDir);

        if (mainDir.exists() && mainDir.isDirectory()) {
            File[] arr = mainDir.listFiles();
            if (arr != null)
                extractResources(arr, 0);
        }
    }

    protected abstract void extractResources(File[] arr, int index);
}
