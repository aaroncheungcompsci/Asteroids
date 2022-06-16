package com.mycompany.a3.oldcommands;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class AboutCommand extends Command {
    private GameWorld gw;

    public AboutCommand(GameWorld gw) {
        super ("About");
        this.gw = gw;
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        Dialog.show("Assignment 3", "Aaron Cheung, CSC 133",
                "OK", null);
    }
}
