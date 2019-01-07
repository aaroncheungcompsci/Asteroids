package com.mycompany.a3;

import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class BackgroundSound {

    private Media m;

    public BackgroundSound(String soundFile) {
        try {
            InputStream stream = Display.getInstance().getResourceAsStream(getClass(), "/" + soundFile);

            m = MediaManager.createMedia(stream, "audio/wav");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        m.pause();
    }

    public void play (int vol) {
        m.setTime(0);
        m.setVolume(vol);
        m.play();
    }

}
