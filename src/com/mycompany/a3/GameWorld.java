package com.mycompany.a3;
import com.codename1.charts.util.ColorUtil;

import com.codename1.ui.Dialog;
import com.codename1.ui.geom.Point2D;
import java.util.Random;
import java.util.Scanner;
import java.util.Observable;

public class GameWorld extends Observable implements IGameWorld {
    private Random r;
    private GameCollection store;
    private GameCollection garbage;
    //private ArrayList<GameObject> store;
    private int lives;
    private Scanner kbd;
    private int score;
    private int gameTime;
    private boolean sound;
    private GameSound gameSound;
    private static int ID;

    //------COLORS-------//

    private int asteroidColor;
    private int psColor;
    private int npsColor;
    private int spaceStationColor;
    private int missileColor;

    //-------------------//

    private double gameHeight;
    private double gameWidth;
    private boolean paused;

    public GameWorld() {
        this.init();
    }

    public void init() {
        store = new GameCollection();
        garbage = new GameCollection();

        //store = new ArrayList<>();
        r = new Random();
        kbd = new Scanner(System.in);
        lives = 3;
        score = 0;
        gameTime = 0;
        ID = 1;

        asteroidColor = -1;
        psColor = -1;
        npsColor = -1;
        spaceStationColor = -1;
        missileColor = -1;
        sound = true;
        gameSound = new GameSound();
    }

    public void setGameHeight(double height) {
        gameHeight = height;
    }

    public void setGameWidth(double width) {
        gameWidth = width;
    }

    public int getPlayerScore() {
        return score;
    }

    public void setPlayerScore(int score) {
        this.score = score;
    }

    public int getPSMissileCount() {
        PS player = null;
        try {
            for (int i = 0; i < store.getSize(); i++) {
                if (store.getElement(i) instanceof PS) {
                    player = (PS) store.getElement(i);
                    return player.getMissileCount();
                }
            }
        } catch (Exception e) {
            System.out.println("No player ship!");
        }
        return 0;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public boolean getSound() {
        return sound;
    }

    public int getClock() {
        return gameTime;
    }

    public GameCollection getGameCollection() {
        return store;
    }

    public void setPaused() {
        if (paused) {
            paused = false;
        } else {
            paused = true;
        }
    }

    public boolean isPaused() {
        return paused;
    }

    public void setSound() {
        gameSound.soundToggle();
    }

    public void switchSound() {
        if (sound == true) {
            sound = false;
        } else {
            sound = true;
        }
        this.notifyObserver();
    }

    public void addNewAsteroid() { //a
        if (this.isPaused()) {
            return;
        }
        if (asteroidColor == -1) { //generates random color then saves value
            asteroidColor = ColorUtil.BLUE;
        }
        Asteroid asteroid = new Asteroid(new Point2D(
                randomCoord(0.0, gameWidth), randomCoord(0.0, gameHeight)
        ), asteroidColor);
        store.add(asteroid);
        System.out.println("A new ASTEROID has been created.");
        this.notifyObserver();
    }

    public void addNewNPS() { //y
        if (this.isPaused()) {
            return;
        }
        if (npsColor == -1) {
            npsColor = generateColor();
        }
        NPS nps = new NPS(new Point2D(
            randomCoord(0.0, 1024.0), randomCoord(0.0, 768.0)
        ), r.nextInt(360), npsColor);
        store.add(nps);
        System.out.println("A new NPS has been created.");
        this.notifyObserver();
    }

    public void addNewSpaceStation() { //b
        if (this.isPaused()) {
            return;
        }
        if (spaceStationColor == -1) {
            spaceStationColor = ColorUtil.CYAN;
        }

        SpaceStation ss = new SpaceStation(new Point2D (
                randomCoord(0.0, gameWidth), randomCoord(0.0, gameHeight)
        ), spaceStationColor, ID);
        ID++;
        store.add(ss);
        System.out.println("A new SPACESTATION has been created.");
        notifyObserver();
    }

    public void addNewPS() { //s
        if (this.isPaused()) {
            return;
        }
        try {
            for (int i = 0; i < store.getSize(); i++) {
                if (store.getElement(i) instanceof PS) {
                    throw new Exception();
                }
            }
            if (psColor == -1) {
                psColor = generateColor();
            }
            PS player = new PS(0, 0, psColor, new Point2D(gameWidth/2.0, gameHeight/2.0));
            store.add(player);
            MissileLauncher launcher = new MissileLauncher(player.getLocation(),
                    player.getSpeed(), player.getDirection(), player.getColor());
            System.out.println("A new PLAYERSHIP has been created.");
            notifyObserver();
        } catch (Exception e) {
            System.out.println("A player ship already exists.");
        }
    }

    public void increaseSpeed() { //i
        if (this.isPaused()) {
            return;
        }
        if (store.getSize() == 0) {
            System.out.println("There is no PS!");
            return;
        }
        try {
            for (int i = 0; i < store.getSize(); i++) {
                if (store.getElement(i) instanceof PS) {
                    PS player = (PS) store.getElement(i);
                    if (player.getSpeed() >= 10) {
                        System.out.println("PS speed is max!");
                        System.out.println("PS speed is " + player.getSpeed());
                        return;
                    }
                    player.setSpeed(player.getSpeed() + 1);
                    System.out.println("PS speed increased!");
                    System.out.println("PS speed is now " + player.getSpeed() + "!");
                    this.notifyObserver();
                    return;
                }
            }
            throw new Exception();
        } catch (Exception e) {
            System.out.println("There is no PS!");
        }
    }

    public void decreaseSpeed() { //d
        if (this.isPaused()) {
            return;
        }
        if (store.getSize() == 0) {
            System.out.println("There is no PS!");
            return;
        }
        try {
            for (int i = 0; i < store.getSize(); i++) {
                if (store.getElement(i) instanceof PS) {
                    PS player = (PS) store.getElement(i);
                    if (player.getSpeed() <= 0) {
                        System.out.println("PS can't go any slower!");
                        System.out.println("PS speed is " + player.getSpeed() + "!");
                        return;
                    }
                    player.setSpeed(player.getSpeed() - 1);
                    System.out.println("PS speed decreased!");
                    System.out.println("PS speed is now " + player.getSpeed() + "!");
                    notifyObserver();
                    return;
                }
            }
            throw new Exception();
        } catch (Exception e) {
            System.out.println("There is no PS!");
        }
    }

    public void turnLeft() { //l
        if (this.isPaused()) {
            return;
        }
        if (store.getSize() == 0) {
            System.out.println("There is no PS!");
            return;
        }
        try {
            for (int i = 0; i < store.getSize(); i++) {
                if (store.getElement(i) instanceof PS) {
                    PS player = (PS) store.getElement(i);
                    player.steerLeft();
                    System.out.println("Ship turned left slightly!");
                    this.notifyObserver();
                    return;
                }
            }
            throw new Exception();
        } catch (Exception e) {
            System.out.println("There is no PS!");
        }
    }

    public void turnRight() { //r
        if (this.isPaused()) {
            return;
        }
        if (store.getSize() == 0) {
            System.out.println("There is no PS!");
            return;
        }
        try {
            for (int i = 0; i < store.getSize(); i++) {
                if (store.getElement(i) instanceof PS) {
                    PS player = (PS) store.getElement(i);
                    player.steerRight();
                    System.out.println("Ship turned right slightly!");
                    this.notifyObserver();
                    return;
                }
            }
            throw new Exception();
        } catch (Exception e) {
            System.out.println("There is no PS!");
        }
    }

    public void turnMissileLauncher() { //<
        if (this.isPaused()) {
            return;
        }
        if (store.getSize() == 0) {
            System.out.println("There is no PS!");
            return;
        }
        try {
            for (int i = 0; i < store.getSize(); i++) {
                if (store.getElement(i) instanceof PS) {
                    PS player = (PS) store.getElement(i);
                    player.steerLauncher();
                    System.out.println("Launcher turned counter-clockwise slightly!");
                    this.notifyObserver();
                    return;
                }
            }
            throw new Exception();
        } catch (Exception e) {
            System.out.println("There is no PS!");
        }
    }

    public void refuel() {
        if (this.isPaused()) {
            return;
        }
        try {
            Missile missile;
            for (int i = 0; i < store.getSize(); i++) {
                if(store.getElement(i) instanceof Missile) {
                    missile = (Missile) store.getElement(i);
                    missile.refillFuel();
                }
            }
            System.out.println("Missiles refueled!");
        } catch (Exception e) {
            System.out.println("No player missiles to refuel!");
        }
    }

    public void fire() { //f
        if (this.isPaused()) {
            return;
        }
        if (store.getSize() == 0) {
            System.out.println("There is no PS!");
            return;
        }
        try {
            for (int i = 0; i < store.getSize(); i++) {
                if (store.getElement(i) instanceof PS) {
                    PS player = (PS) store.getElement(i);
                    if (player.getMissileCount() == 0) {
                        System.out.println("PS has no more missiles!");
                        return;
                    } else {
                        if (missileColor == -1) {
                            missileColor = ColorUtil.BLACK;
                        }
                        Point2D point = new Point2D(((PS) store.getElement(i)).getLocation().getX(),
                                ((PS) store.getElement(i)).getLocation().getY());
                        Missile missile = new Missile(player.getDirection(),
                                player.getSpeed(), point, missileColor, 5);
                        player.fire(missile);
                        gameSound.missileLaunchSound();
                        store.add(missile);
                        System.out.println("PS missile fired!");
                        this.notifyObserver();
                        return;
                    }
                }
            }
            throw new Exception();
        } catch (Exception e) {
            System.out.println("There is no PS!");
        }
    }

    public void launch() { //L
        if (this.isPaused()) {
            return;
        }
        if (store.getSize() == 0) {
            System.out.println("There is no NPS!");
            return;
        }
        try {
            for (int i = 0; i < store.getSize(); i++) {
                if (store.getElement(i) instanceof NPS) {
                    NPS npc = (NPS) store.getElement(i);
                    if (npc.getMissileCount() == 0) {
                        System.out.println("NPS has no more missiles!");
                    } else {
                        if (missileColor == -1) {
                            missileColor = generateColor();
                        }
                        Point2D point = new Point2D(((NPS) store.getElement(i)).getLocation().getX(),
                                ((NPS) store.getElement(i)).getLocation().getY());
                        MissileNPS missile = new MissileNPS(point, npc.getSpeed(), npc.getDirection(), missileColor, 5);
                        npc.fire(missile);
                        gameSound.missileLaunchSound();
                        System.out.println("NPS has fired a missile!");
                        store.add(missile);
                        this.notifyObserver();
                        return;
                    }
                }
            }
            throw new Exception();
        } catch (Exception e) {
            System.out.println("There is no NPS!");
        }
    }

    public void jumpHyperspace() { //j
        if (this.isPaused()) {
            return;
        }
        if (store.getSize() == 0) {
            System.out.println("There is no PS!");
            return;
        }
        try {
            for (int i = 0; i < store.getSize(); i++) {
                if (store.getElement(i) instanceof PS) {
                    PS player = (PS) store.getElement(i);
                    player.setLocation(new Point2D(gameWidth/2.0, gameHeight/2.0));
                    this.notifyObserver();
                    return;
                }
            }
            throw new Exception();
        } catch (Exception e) {
            System.out.println("There is no PS!");
        }
    }

    public void reload() { //n
        if (this.isPaused()) {
            return;
        }
        if (store.getSize() == 0) {
            System.out.println("There is no PS!");
            return;
        }
        try {
            for (int i = 0; i < store.getSize(); i++) {
                if (store.getElement(i) instanceof PS) {
                    PS player = (PS) store.getElement(i);
                    player.setMissileCount(10);
                    this.notifyObserver();
                    return;
                }
            }
            throw new Exception();
        } catch (Exception e) {
            System.out.println("There is no PS!");
        }
    }

    public void killAsteroid() { //k PS killed asteroid
        if (this.isPaused()) {
            return;
        }
        boolean missile = false;
        boolean aster = false;
        if (store.getSize() == 0) {
            System.out.println("No asteroid or missile in game");
        }
        try {
            for (int i = 0; i < store.getSize(); i++) {
                if (store.getElement(i) instanceof Missile) { //look for asteroid
                    missile = true;
                }
            }
            for (int i = 0; i < store.getSize(); i++) {
                if (store.getElement(i) instanceof Asteroid) { //look for PS
                    aster = true;
                }
            }

            if (missile == true && aster == true) {
                removeMissile();
                removeAsteroid();
            } else {
                throw new Exception();
            }
            score += 30;
            System.out.println("A PS missile has hit an asteroid!");
            notifyObserver();
        } catch (Exception e) {
            System.out.println("No missile or asteroid in game");
        }
    }

    public void killNPS() { //e PS killed NPS
        boolean missile = false;
        boolean NPS = false;
        if (store.getSize() == 0) {
            System.out.println("No missile or NPS in game");
        }
        try {
            for (int i = 0; i < store.getSize(); i++) {
                if (store.getElement(i) instanceof NPS) { //look for asteroid
                    NPS = true;
                }
            }
            for (int i = 0; i < store.getSize(); i++) {
                if (store.getElement(i) instanceof Missile) { //look for PS
                    missile = true;
                }
            }

            if (missile == true && NPS == true) {
                removeMissile();
                removeNPS();
            } else {
                throw new Exception();
            }
            System.out.println("A PS missile has hit an NPS!");
            score += 30;
            notifyObserver();
        } catch (Exception e) {
            System.out.println("No missile or NPS in game");
        }
    }

    public void killPS() { //E enemy missile killed PS
        boolean missile = false;
        boolean PS = false;
        PS player = null;
        if (store.getSize() == 0) {
            System.out.println("No missileNPS or PS in game");
        }
        try {
            for (int i = 0; i < store.getSize(); i++) {
                if (store.getElement(i) instanceof MissileNPS) { //look for asteroid
                    missile = true;
                }
            }
            for (int i = 0; i < store.getSize(); i++) {
                if (store.getElement(i) instanceof PS) { //look for PS
                    PS = true;
                    player = (PS) store.getElement(i);
                }
            }
            if (missile == true && PS == true) {
                removeMissileNPS();
                if (lives > 0) {
                    player.setLocation(new Point2D(gameWidth/2.0, gameHeight/2.0));
                    lives--;
                } else {
                    removePS();
                    System.out.println("Game Over!");
                    return;
                }
            } else {
                throw new Exception();
            }
            System.out.println("A NPS missile has hit you! " + lives + " lives left!");
            notifyObserver();
        } catch (Exception e) {
            System.out.println("No missileNPS or PS in game");
        }
    }

    public void crash() { //c PS crash into asteroid
        boolean aster = false;
        boolean PS = false;
        PS player = null;
        if (store.getSize() == 0) {
            System.out.println("No asteroid or PS in game");
        }
        try {
            for (int i = 0; i < store.getSize(); i++) {
                if (store.getElement(i) instanceof Asteroid) { //look for asteroid
                    aster = true;
                }
            }
            for (int i = 0; i < store.getSize(); i++) {
                if (store.getElement(i) instanceof PS) { //look for PS
                    PS = true;
                    player = (PS) store.getElement(i);
                }
            }

            if (aster == true && PS == true) {
                removeAsteroid();
                if (lives > 0) {
                    player.setLocation(new Point2D(gameWidth/2.0, gameHeight/2.0));
                    lives--;
                } else {
                    removePS();
                    System.out.println("Game Over!");
                    return;
                }
            } else {
                throw new Exception();
            }
            System.out.println("An asteroid has hit you! " + lives + " lives left!");
            notifyObserver();
        } catch (Exception e) {
            System.out.println("No asteroid or PS in game");
        }
    }

    public void hit() { //h NPS hit PS
        boolean NPS = false;
        boolean PS = false;
        PS player = null;
        if (store.getSize() == 0) {
            System.out.println("No NPS or PS in game");
            return;
        }
        try {
            for (int i = 0; i < store.getSize(); i++) {
                if (store.getElement(i) instanceof NPS) {
                    NPS = true;
                }
            }
            for (int i = 0; i < store.getSize(); i++) {
                if (store.getElement(i) instanceof PS) {
                    PS = true;
                    player = (PS) store.getElement(i);
                }
            }

            if (NPS == true && PS == true) {
                removeNPS();
                if (lives > 0) {
                    player.setLocation(new Point2D(gameWidth/2.0, gameHeight/2.0));
                    lives--;
                } else {
                    removePS();
                    System.out.println("Game Over!");
                    return;
                }
            } else {
                throw new Exception();
            }
            System.out.println("You crashed into an NPS! " + lives + " lives left!");
            notifyObserver();
        } catch (Exception e) {
            System.out.println("No NPS or PS in game");
        }
    }

    public void collide() { //x 2 asteroids hit eachother
        boolean aster1 = false;
        boolean aster2 = false;
        int hold = 0;
        if (store.getSize() == 0) {
            System.out.println("There are no asteroids at all.");
            return;
        }
        try {
            for (int i = 0; i < store.getSize(); i++) {
                if (store.getElement(i) instanceof Asteroid) {
                    aster1 = true;
                    hold = i;
                }
            }
            for (int i = hold; i < store.getSize(); i++) {
                if (store.getElement(i) instanceof Asteroid) {
                    aster2 = true;
                }
            }

            if (aster1 == true && aster2 == true) {
                removeAsteroid();
                removeAsteroid();
            } else {
                throw new Exception();
            }
            System.out.println("Two asteroids have hit each other!");
            notifyObserver();
        } catch (Exception e) {
            System.out.println("There are no asteroids at all, or there is only one.");
        }
    }

    public void impact() { //I NPS and asteroid hit eachother
        boolean NPS = false;
        boolean aster = false;
        if (store.getSize() == 0) {
            System.out.println("There is no NPS or Asteroid");
            return;
        }
        try {
            for (int i = 0; i < store.getSize(); i++) {
                if (store.getElement(i) instanceof NPS) {
                    NPS = true;
                }
            }
            for (int i = 0; i < store.getSize(); i++) {
                if (store.getElement(i) instanceof Asteroid) {
                    aster = true;
                }
            }

            if (aster == true && NPS == true) {
                removeAsteroid();
                removeNPS();
            } else {
                throw new Exception();
            }
            System.out.println("NPS and an asteroid have hit each other!");
            notifyObserver();
        } catch (Exception e) {
            System.out.println("There is no NPS or Asteroid");
        }
    }

    public void tick() { //t

        for (int i = 0; i < store.getSize(); i++) {
            if (store.getElement(i) instanceof IMoveable) {
                IMoveable mObj = (IMoveable) store.getElement(i);
                mObj.move();
            }
        }
        for (int i = 0; i < store.getSize(); i++) {
            if (store.getElement(i) instanceof Missile) {
                Missile missile = (Missile) store.getElement(i);
                missile.useFuel();
                if (missile.getFuel() == 0) {
                    store.removeAtIndex(i);
                    i--;
                }
            }
            if (store.getElement(i) instanceof MissileNPS) {
                MissileNPS missile = (MissileNPS) store.getElement(i);
                missile.useFuel();
                if (missile.getFuel() == 0) {
                    store.removeAtIndex(i);
                    i--;
                }
            }
            if (store.getElement(i) instanceof SpaceStation) {
                SpaceStation ss = (SpaceStation) store.getElement(i);
                if ((gameTime * 1000) % ss.getBlinkRate() == 0) {
                    ss.blink();
                }
            }
        }

        this.notifyObserver();
    }

    public void print() { //p
        PS player = null;
        if (store.getSize() == 0) {
            //do nothing?
        } else {
            for (int i = 0; i < store.getSize(); i++) {
                if (store.getElement(i) instanceof PS) {
                    player = (PS) store.getElement(i);
                }
            }
        }

        System.out.println("Current Score: " + score);
        if (player != null) {
            System.out.println("PS missiles remaining: " + player.getMissileCount());
        } else {
            System.out.println("There is no PS!");
        }
        System.out.println("Current game time: " + gameTime);
    }

    public void map() { //m
        if (store.getSize() == 0) {
            System.out.println("No objects in game!");
            return;
        }
        for (int i = 0; i < store.getSize(); i++) {
            if (store.getElement(i) instanceof PS) {
                PS player = (PS) store.getElement(i);
                System.out.print("PS: ");
                System.out.println(player.toString());
            } else if (store.getElement(i) instanceof NPS) {
                NPS npc = (NPS) store.getElement(i);
                System.out.print("NPS: ");
                System.out.println(npc.toString());
            } else if (store.getElement(i) instanceof Asteroid) {
                Asteroid asteroid = (Asteroid) store.getElement(i);
                System.out.print("Asteroid: ");
                System.out.println(asteroid.toString());
            } else if (store.getElement(i) instanceof SpaceStation) {
                SpaceStation ss = (SpaceStation) store.getElement(i);
                System.out.print("Station: ");
                System.out.println(ss.toString());
            } else if (store.getElement(i) instanceof Missile) {
                Missile missile = (Missile) store.getElement(i);
                System.out.print("PS Missile: ");
                System.out.println(missile.toString());
            } else if (store.getElement(i) instanceof MissileNPS) {
                MissileNPS missile = (MissileNPS) store.getElement(i);
                System.out.print("NPS Missile: ");
                System.out.println(missile.toString());
            }
        }
    }

    public void quit() { //q
        System.exit(0);
    }

    public void advanceGameTime() {
        gameTime++;
        tick();
        if (gameTime % 500 == 0) {
            int roll = randomVal(1, 10);
            if (roll >= 5) {
                addNewNPS();
                for (int i = 0; i < 4; i++) {
                    addNewAsteroid();
                }
            }
            if (roll >= 9) {
                launch();
            }
        }
        collisionCheck();
    }

    public void collisionCheck() {

        IIterator colIterator = store.getIterator();
        while(colIterator.hasNext()) {
            ICollider curObj = (ICollider)colIterator.getNext();
            // get a collidable object
            // check if this object collides with any OTHER object
            IIterator iter2 = store.getIterator();
            while(iter2.hasNext()) {
                ICollider otherObj = (ICollider)iter2.getNext();
                // get a collidable object
                // check for collision
                if(otherObj!=curObj) {
                    // make sure it's not the SAME object
                    if(curObj.collidesWith(otherObj)) {
                        curObj.handleCollision(otherObj);
                    }
                }
            }
        }
        removeCollided();
    }

    public void removeCollided() {
        //GameCollection garbage = new GameCollection();
        IIterator remIterator = store.getIterator();
        Object object;
        while(remIterator.hasNext()) {
            object = remIterator.getNext();
            if(((GameObject)object).getFlag() == true) {
                garbage.add((GameObject) object);
                remIterator.removeObject(object);
            }
        }

        garbageRemover(garbage);
    }

    public void garbageRemover(GameCollection garbageCollection) {
        IIterator iterator = garbageCollection.getIterator();
        while (iterator.hasNext()) {
            Object target = iterator.getNext();
            if (target instanceof Asteroid || target instanceof NPS) {
                gameSound.genericSound();
                iterator.removeObject(target);
            }
            if (target instanceof PS) {
                gameSound.genericSound();
                iterator.removeObject(target);
                setLives(getLives() - 1);
                if (getLives() > 0) {
                    System.out.println("player has lives: " + getLives());
                    addNewPS();
                    jumpHyperspace();
                } else {
                    gameSound.gameOverSound();
                    if(Dialog.show("Game Over! You scored: " + getPlayerScore(), "Try again?", "Yes", "No")) {
                        gameSound.pauseMusic();
                        new Game();
                    } else {
                        quit();
                    }
                }
            }
            if (target instanceof Missile || target instanceof MissileNPS) {
                gameSound.genericSound();
                iterator.removeObject(target);
            }
            if (target instanceof Asteroid && ((Asteroid) target).getMissileFlag() == true) {
                Asteroid aster = (Asteroid) target;
                if (aster.getSize() >= 20) {
                    setPlayerScore(getPlayerScore() + 200);
                } else if (aster.getSize() >= 10 && aster.getSize() < 20) {
                    setPlayerScore(getPlayerScore() + 100);
                } else {
                    setPlayerScore(getPlayerScore() + 50);
                }
            }

            if (target instanceof NPS && ((NPS) target).getMissileFlag() == true) {
                setPlayerScore(getPlayerScore() + 300);
            }

            if (target instanceof SpaceStation && ((SpaceStation) target).getSpaceStationFlag() == true) {
                reload();
            }
        }
    }
    //additional methods
    
    public void notifyObserver() {
        this.setChanged();
        this.notifyObservers(new GameWorldProxy(this));
    }

    private double randomCoord(double min, double max) {
        Random r = new Random();
        return min + (max - min) * r.nextDouble();
    }

    private int randomVal(int min, int max) {
        r = new Random();
        return min + (max - min) * r.nextInt();
    }

    private int generateColor() {
        return ColorUtil.rgb(r.nextInt(255), r.nextInt(255),
                r.nextInt(255));
    }

    private void removeAsteroid() {
        for (int i = 0; i < store.getSize(); i++) {
            if (store.getElement(i) instanceof Asteroid) {
                store.removeAtIndex(i);
            }
        }
    }

    private void removeNPS() {
        for (int i = 0; i < store.getSize(); i++) {
            if (store.getElement(i) instanceof NPS) {
                store.removeAtIndex(i);
            }
        }
        for (int i = 0; i < store.getSize(); i++) {
            if (store.getElement(i) instanceof MissileLauncherNPS) {
                store.removeAtIndex(i);
            }
        }
    }

    private void removePS() {
        for (int i = 0; i < store.getSize(); i++) {
            if (store.getElement(i) instanceof PS) {
                store.removeAtIndex(i);
            }
        }
        for (int i = 0; i < store.getSize(); i++) {
            if (store.getElement(i) instanceof MissileLauncher) {
                store.removeAtIndex(i);
            }
        }
    }

    private void removeMissile() {
        for (int i = 0; i < store.getSize(); i++) {
            if (store.getElement(i) instanceof Missile) {
                store.removeAtIndex(i);
            }
        }
    }

    private void removeMissileNPS() {
        for (int i = 0; i < store.getSize(); i++) {
            if (store.getElement(i) instanceof MissileNPS) {
                store.removeAtIndex(i);
            }
        }
    }
}
