package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class KillPSCommand extends Command {
    private GameWorld gw;

    public KillPSCommand(GameWorld gw) {
        super ("Kill PS");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getKeyEvent() != -1)
            gw.killPS();
    }
}
