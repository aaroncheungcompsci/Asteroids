package com.mycompany.a3.oldcommands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class FireNPSCommand extends Command {
    private GameWorld gw;

    public FireNPSCommand(GameWorld gw) {
        super ("NPS fired a missile");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //if (e.getKeyEvent() != -1)
            //gw.launch();
    }
}
