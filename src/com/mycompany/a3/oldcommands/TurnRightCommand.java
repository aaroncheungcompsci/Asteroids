package com.mycompany.a3.oldcommands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

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
