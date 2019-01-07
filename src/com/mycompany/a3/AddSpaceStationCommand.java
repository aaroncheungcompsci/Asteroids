package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class AddSpaceStationCommand extends Command {

    private GameWorld gw;

    public AddSpaceStationCommand (GameWorld gw) {
        super ("Add Space Station");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getKeyEvent() != -1)
            gw.addNewSpaceStation();
    }
}
