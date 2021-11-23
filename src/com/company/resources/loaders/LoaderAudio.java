package com.company.resources.loaders;

import com.company.resources.file_system.Archive;

import java.io.File;

public class LoaderAudio extends Loader {
    private static LoaderAudio instance = null;

    private LoaderAudio() {
        super("res\\audio", Archive.SOUND);
    }

    public static LoaderAudio getInstance() {
        if (instance == null)
            instance = new LoaderAudio();
        return instance;
    }

    @Override
    protected void extractResources(File[] arr, int index) {
        if (index == arr.length)
            return;

        if (arr[index].isFile())
            filenames.add(arr[index].getAbsolutePath());
        else if (arr[index].isDirectory()) {
            File[] files = arr[index].listFiles();
            if (files != null)
                extractResources(files, 0);
        }
        extractResources(arr, ++index);
    }
}
