package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class TurnRightCommand extends Command {
    private GameWorld gw;

    public TurnRightCommand(GameWorld gw) {
        super ("PS turn right");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getKeyEvent() != -1)
            gw.turnRight();
    }
}
