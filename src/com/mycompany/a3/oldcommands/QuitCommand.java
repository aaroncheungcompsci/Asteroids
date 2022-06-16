package com.mycompany.a3.oldcommands;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class QuitCommand extends Command {
    private GameWorld gw;

    public QuitCommand(GameWorld gw) {
        super ("Quit game");
        this.gw = gw;
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        if (Dialog.show("Are you sure you want to quit?", "If you quit, unsaved progress will be lost!",
                "Yes", "No")) {
            gw.quit();
        } else {
            System.out.println("Decided not to quit");
        }
    }
}
