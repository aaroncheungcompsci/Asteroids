package com.mycompany.a3.oldcommands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class DecreaseSpeedCommand extends Command {
    private GameWorld gw;

    public DecreaseSpeedCommand(GameWorld gw) {
        super ("Decrease PS speed");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getKeyEvent() != -1)
            gw.decreaseSpeed();
    }
}
