package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class FirePSCommand extends Command {
    private GameWorld gw;

    public FirePSCommand(GameWorld gw) {
        super ("PS fired a missile");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getKeyEvent() != -1)
            gw.fire();
    }
}
