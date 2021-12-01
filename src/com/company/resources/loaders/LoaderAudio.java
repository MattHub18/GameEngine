package com.company.resources.loaders;

import com.company.resources.file_system.Archive;

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
}
