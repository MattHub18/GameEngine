package com.company.audio;

import com.company.resources.file_system.Archive;

import java.io.Serializable;

public class AudioComponent implements Serializable {

    private final byte audioFilename;
    boolean singleFrame = false;

    public AudioComponent(byte audioFilename) {
        this.audioFilename = audioFilename;
    }

    public void update() {
        if (!singleFrame) {
            singleFrame = true;
            Sound sound = new Sound(Archive.SOUND.get(audioFilename));
            sound.setVolume(-80f);
            sound.play();
        }
    }

    public void reset() {
        singleFrame = false;
    }
}
