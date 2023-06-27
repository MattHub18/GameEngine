package com.company.resources.file_system;

import java.io.File;
import java.util.ArrayList;

public class Loader {

    private static Loader instance;

    private final ArrayList<String> filenames;

    private Loader(ArrayList<String> filenames) {
        this.filenames = filenames;
    }

    public static Loader getInstance(ArrayList<String> filenames) {
        if (instance == null)
            instance = new Loader(filenames);
        return instance;
    }

    public void load(String rootDir) {
        File mainDir = new File(rootDir);

        if (mainDir.exists() && mainDir.isDirectory()) {
            File[] arr = mainDir.listFiles();
            if (arr != null)
                extractResources(arr, 0);
        }

        instance = null;
    }

    private void extractResources(File[] source, int index) {
        if (index == source.length)
            return;

        if (source[index].isFile())
            filenames.add(source[index].getAbsolutePath());
        else if (source[index].isDirectory()) {
            File[] files = source[index].listFiles();
            if (files != null)
                extractResources(files, 0);
        }
        extractResources(source, ++index);
    }
}
