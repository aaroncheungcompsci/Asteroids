package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class KillAsteroidCommand extends Command {
    private GameWorld gw;

    public KillAsteroidCommand(GameWorld gw) {
        super ("Kill Asteroid");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getKeyEvent() != -1)
            gw.killAsteroid();
    }
}