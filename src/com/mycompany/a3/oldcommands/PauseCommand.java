package com.mycompany.a3.oldcommands;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.util.UITimer;
import com.mycompany.a3.Game;
import com.mycompany.a3.GameWorld;

public class PauseCommand extends Command {

    UITimer timer;
    GameWorld gw;
    Game game;
    private static PauseCommand pause;

    public PauseCommand(UITimer timer, GameWorld gw, Game game) {
        super ("Pause/Play");
        this.gw = gw;
        this.timer = timer;
        this.game = game;
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        if (e.getKeyEvent() != -1) {
            if (gw.isPaused()) {
                game.startTimer(timer);
                gw.setPaused();
                game.playKeyBindings();
                Dialog.show("Paused", "The game is now unpaused.",
                        "OK", null);
            } else {
                game.stopTimer(timer);
                gw.setPaused();
                game.pauseKeyBindings();
                Dialog.show("Paused", "The game is now paused.",
                        "OK", null);
            }

        }
    }

    public static PauseCommand pause(UITimer timer, GameWorld gw, Game game) {
        if (pause == null) {
            return new PauseCommand(timer, gw, game);
        }
        return pause;
    }
}
