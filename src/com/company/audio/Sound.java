package com.company.audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class Sound implements Serializable {
    private Clip clip;
    private FloatControl gainControl;

    public Sound(String path) {
        try {
            clip = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
            clip.open(ais);
            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip == null)
            return;
        stop();
        clip.setFramePosition(0);
        if (!clip.isRunning())
            clip.start();
    }

    private void stop() {
        if (clip.isRunning())
            clip.stop();
    }

    public void close() {
        stop();
        clip.drain();
        clip.close();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        play();
    }

    public void setVolume(float value) {
        gainControl.setValue(value);
    }

}
