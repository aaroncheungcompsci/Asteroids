package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class TurnLauncherCommand extends Command {
    private GameWorld gw;

    public TurnLauncherCommand(GameWorld gw) {
        super ("PS Launcher turned");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getKeyEvent() != -1)
            gw.turnMissileLauncher();
    }
}
