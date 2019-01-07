package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CrashCommand extends Command {
    private GameWorld gw;

    public CrashCommand(GameWorld gw) {
        super ("Crash into Asteroid");
        this.gw = gw;
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        if (e.getKeyEvent() != -1)
            gw.crash();
    }
}
