package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CollideCommand extends Command {
    private GameWorld gw;

    public CollideCommand(GameWorld gw) {
        super ("Collide Asteroids");
        this.gw = gw;
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        if (e.getKeyEvent() != -1)
            gw.collide();
    }
}
