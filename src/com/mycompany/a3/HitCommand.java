package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class HitCommand extends Command {
    private GameWorld gw;

    public HitCommand(GameWorld gw) {
        super ("Crash PS into NPS");
        this.gw = gw;
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        if (e.getKeyEvent() != -1)
            gw.hit();
    }
}
