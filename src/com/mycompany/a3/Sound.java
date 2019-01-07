package com.mycompany.a3;

import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class Sound{
    private Media m;

    public Sound(String fileName) {
        try {
            InputStream stream = Display.getInstance().getResourceAsStream(getClass(), "/" + fileName);
            m = MediaManager.createMedia(stream, "audio/wav,");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        m.pause();
    }

    public void play(int v) {
        m.setTime(0);
        m.setVolume(v);
        m.play();
    }
}
