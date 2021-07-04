package com.company.resources;

import java.io.File;

public abstract class Loader {
    public static void load() {
        File mainDir = new File("res");

        if (mainDir.exists() && mainDir.isDirectory()) {
            File[] arr = mainDir.listFiles();
            if (arr != null)
                extractResources(arr, 0);
        }
    }

    private static void extractResources(File[] arr, int index) {
        if (index == arr.length)
            return;

        if (arr[index].isFile())
            Resources.TEXTURES.add(arr[index].getAbsolutePath());
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
