package com.mycompany.a3.oldcommands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class SoundCommand extends Command {
    private GameWorld gw;

    public SoundCommand(GameWorld gw) {
        super ("Sound");
        this.gw = gw;
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        gw.switchSound();
    }
}
