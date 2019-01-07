package com.mycompany.a3;

public class GameSound {

    private Sound missileLaunch, collision, gameOver, missileHit;
    private BackgroundSound backgroundSound;
    private int bgVol, gameVol;
    private boolean enable;

    public GameSound() {
        missileLaunch = new Sound("PSShoot.wav");
        missileHit = new Sound("MissileDie.wav");
        gameOver = new Sound("GameOver.wav");
        collision = new Sound("AsterDie.wav");

        backgroundSound = new BackgroundSound("audio.wav");

        bgVol = 30;
        gameVol = 30;

        enable = true;

        playMusic();
    }

    public void genericSound() {
        if (enable) {
            collision.play(getVol());
        }
    }

    /*
    public void shipCrashSound() {
        if (enable) {
            shipCrash.play(getVol());
        }
    }
    */

    public void missileHitSound() {
        if (enable) {
            missileHit.play(getVol());
        }
    }

    public void gameOverSound() {
        if (enable) {
            gameOver.play(getVol());
        }
    }

    public void missileLaunchSound() {
        if (enable){
            missileLaunch.play(getVol());
        }
    }

    public void playMusic() {
        if(enable) {
            backgroundSound.play(getBackgroundVol());
        }
    }

    public void pauseMusic() { //pause
        backgroundSound.pause();
    }

    public void soundToggle() {
        enable = !enable;
        if (enable == false){
            pauseMusic();
        } else {
            playMusic();
        }
    }

    public void soundToggle(boolean e) {
        enable = !e;
        if (enable == false) {
            pauseMusic();
        } else {
            playMusic();
        }
    }

    public boolean getSound() {
        return enable;
    }

    public void setVol(int v) {
        gameVol = v;
    }

    public void setBackgroundVol(int v) {
        bgVol = v;
        playMusic();
    }

    public int getVol() {
        return gameVol;
    }

    public int getBackgroundVol() {
        return bgVol;
    }
}
