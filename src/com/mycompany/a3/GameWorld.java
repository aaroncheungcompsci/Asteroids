package com.mycompany.a3;
import com.codename1.charts.util.ColorUtil;

import com.codename1.ui.Dialog;
import com.codename1.ui.geom.Point2D;

import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameWorld extends Observable implements IGameWorld {
    private Random r;
    private GameCollection store;
    private GameCollection garbage;
    private int lives;
    private int score;
    private int gameTime;
    private boolean sound;
    private GameSound gameSound;
    private int gameId;

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
    private static final Logger LOGGER = Logger.getLogger(GameWorld.class.getName());
    private static final String NO_PS = "There is no PS!";
    private static final String GAMEOVER = "Game Over!";
    private static final String DIFFERROR = "Another error has occurred.";

    public GameWorld() {
        this.init();
    }

    public void init() {
        store = new GameCollection();
        garbage = new GameCollection();

        r = new Random();
        lives = 3;
        score = 0;
        gameTime = 0;
        gameId = 1;

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
        PS player;
        try {
            for (int i = 0; i < store.getSize(); i++) {
                if (store.getElement(i) instanceof PS) {
                    player = (PS) store.getElement(i);
                    return player.getMissileCount();
                }
            }
        } catch (NoSuchElementException e) {
            LOGGER.log(Level.WARNING, "No player ship!");
        }
        return 0;
    }

    public int getLives() {
        return lives;
    }

    private void setLives(int lives) {
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

    public void setPaused() { paused = !paused; }

    public boolean isPaused() {
        return paused;
    }

    public void switchSound() {
        sound = !sound;
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
                randomCoord(gameWidth), randomCoord(gameHeight)
        ), asteroidColor);
        store.add(asteroid);
        LOGGER.log(Level.INFO, "A new ASTEROID has been created.");
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
            randomCoord(1024.0), randomCoord(768.0)
        ), r.nextInt(360), npsColor);
        store.add(nps);
        LOGGER.log(Level.INFO, "A new NPS has been created.");
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
                randomCoord(gameWidth), randomCoord(gameHeight)
        ), spaceStationColor, gameId);
        gameId++;
        store.add(ss);
        LOGGER.log(Level.INFO, "A new SPACESTATION has been created.");
        notifyObserver();
    }

    public void addNewPS() { //s
        if (this.isPaused()) {
            return;
        }
        try {
            for (int i = 0; i < store.getSize(); i++) {
                if (store.getElement(i) instanceof PS) {
                    throw new IllegalArgumentException();
                }
            }
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "A player ship already exists.");
        }
        if (psColor == -1) {
            psColor = generateColor();
        } else {
            PS player = new PS(0, 0, psColor, new Point2D(gameWidth / 2.0, gameHeight / 2.0));
            store.add(player);
            LOGGER.log(Level.INFO, "A new PLAYERSHIP has been created.");
            notifyObserver();
        }
    }

    public void increaseSpeed() { //i
        if (this.isPaused()) {
            if (store.getSize() == 0) {
                LOGGER.log(Level.WARNING, NO_PS);
            }
            return;
        }
        try {
            for (int i = 0; i < store.getSize(); i++) {
                if (store.getElement(i) instanceof PS) {
                    PS player = (PS) store.getElement(i);
                    if (player.getSpeed() >= 10) {
                        LOGGER.log(Level.INFO, "PS speed is max! Max value is 10.");
                        return;
                    }
                    player.setSpeed(player.getSpeed() + 1);
                    LOGGER.log(Level.INFO, "PS speed increased!");
                    this.notifyObserver();
                    return;
                }
            }
            throw new NoSuchElementException();
        } catch (NoSuchElementException e) {
            LOGGER.log(Level.WARNING, NO_PS);
        } catch (Exception e) {
                LOGGER.log(Level.SEVERE, DIFFERROR);
        }
    }

    public void decreaseSpeed() { //d
        if (this.isPaused()) {
            return;
        }
        if (store.getSize() == 0) {
            LOGGER.log(Level.WARNING, NO_PS);
            return;
        }
        try {
            for (int i = 0; i < store.getSize(); i++) {
                if (store.getElement(i) instanceof PS) {
                    PS player = (PS) store.getElement(i);
                    if (player.getSpeed() <= 0) {
                        LOGGER.log(Level.INFO, "PS cannot go any slower!");
                        return;
                    }
                    player.setSpeed(player.getSpeed() - 1);
                    LOGGER.log(Level.INFO, "PS speed decreased!");
                    notifyObserver();
                    return;
                }
            }
            throw new NoSuchElementException();
        } catch (NoSuchElementException e) {
            LOGGER.log(Level.WARNING, NO_PS);
        }
    }

    public void turnLeft() { //l
        if (this.isPaused()) {
            return;
        }
        if (store.getSize() == 0) {
            LOGGER.log(Level.WARNING, NO_PS);
            return;
        }
        try {
            for (int i = 0; i < store.getSize(); i++) {
                if (store.getElement(i) instanceof PS) {
                    PS player = (PS) store.getElement(i);
                    player.steerLeft();
                    LOGGER.log(Level.INFO, "Ship turned left slightly!");
                    notifyObserver();
                    return;
                }
            }
            throw new NoSuchElementException();
        } catch (NoSuchElementException e) {
            LOGGER.log(Level.WARNING, NO_PS);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, DIFFERROR);
        }
    }

    public void turnRight() { //r
        if (this.isPaused()) {
            return;
        }
        if (store.getSize() == 0) {
            LOGGER.log(Level.WARNING, NO_PS);
            return;
        }
        try {
            for (int i = 0; i < store.getSize(); i++) {
                if (store.getElement(i) instanceof PS) {
                    PS player = (PS) store.getElement(i);
                    player.steerRight();
                    LOGGER.log(Level.INFO, "Ship turned right slightly!");
                    this.notifyObserver();
                    return;
                }
            }
            throw new NoSuchElementException();
        } catch (NoSuchElementException e) {
            LOGGER.log(Level.WARNING, NO_PS);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, DIFFERROR);
        }
    }

    public void turnMissileLauncher() { //<
        if (this.isPaused()) {
            return;
        }
        if (store.getSize() == 0) {
            LOGGER.log(Level.WARNING, NO_PS);
            return;
        }
        try {
            for (int i = 0; i < store.getSize(); i++) {
                if (store.getElement(i) instanceof PS) {
                    PS player = (PS) store.getElement(i);
                    player.steerLauncher();
                    LOGGER.log(Level.INFO, "Launcher turned counter-clockwise slightly!");
                    this.notifyObserver();
                    return;
                }
            }
            throw new NoSuchElementException();
        } catch (NoSuchElementException e) {
            LOGGER.log(Level.WARNING, NO_PS);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, DIFFERROR);
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
            LOGGER.log(Level.WARNING, NO_PS);
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
            throw new NullPointerException();
        } catch (NullPointerException e) {
            LOGGER.log(Level.WARNING, NO_PS);
        } catch (Exception e) {
            System.out.println("A different error has occcured.");
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
                        Missile missile = new Missile(npc.getDirection(), npc.getSpeed(), point, missileColor, 5);
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
            LOGGER.log(Level.WARNING, NO_PS);
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
            LOGGER.log(Level.WARNING, NO_PS);
        }
    }

    public void reload() { //n
        if (this.isPaused()) {
            return;
        }
        if (store.getSize() == 0) {
            LOGGER.log(Level.WARNING, NO_PS);
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
            LOGGER.log(Level.WARNING, NO_PS);
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

            if (missile && aster) {
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

            if (missile && NPS) {
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
            if (missile && PS) {
                removeMissileNPS();
                if (lives > 0) {
                    player.setLocation(new Point2D(gameWidth/2.0, gameHeight/2.0));
                    lives--;
                } else {
                    removePS();
                    LOGGER.log(Level.INFO, GAMEOVER);
                    return;
                }
            } else {
                throw new Exception();
            }
            System.out.println("A missile has hit you!");
            notifyObserver();
        } catch (Exception e) {
            System.out.println("No missile or PS in game");
        }
    }

    public void crash() { //c PS crash into asteroid
        boolean aster = false;
        boolean ps = false;
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
                    ps = true;
                    player = (PS) store.getElement(i);
                }
            }

            if (aster && ps) {
                removeAsteroid();
                if (lives > 0) {
                    player.setLocation(new Point2D(gameWidth/2.0, gameHeight/2.0));
                    lives--;
                } else {
                    removePS();
                    LOGGER.log(Level.INFO, GAMEOVER);
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

            if (NPS && PS) {
                removeNPS();
                if (lives > 0) {
                    player.setLocation(new Point2D(gameWidth/2.0, gameHeight/2.0));
                    lives--;
                } else {
                    removePS();
                    LOGGER.log(Level.INFO, GAMEOVER);
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

            if (aster1 && aster2) {
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

            if (aster && NPS) {
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

    public void quit() { //q
        System.exit(0);
    }

    public void advanceGameTime() {
        gameTime++;
        tick();
        if (gameTime % 500 == 0) {
            int roll = randomVal();
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
        IIterator remIterator = store.getIterator();
        Object object;
        while(remIterator.hasNext()) {
            object = remIterator.getNext();
            if(((GameObject) object).getFlag()) {
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
            if (target instanceof Asteroid && ((Asteroid) target).getMissileFlag()) {
                Asteroid aster = (Asteroid) target;
                if (aster.getSize() >= 20) {
                    setPlayerScore(getPlayerScore() + 200);
                } else if (aster.getSize() >= 10 && aster.getSize() < 20) {
                    setPlayerScore(getPlayerScore() + 100);
                } else {
                    setPlayerScore(getPlayerScore() + 50);
                }
            }

            if (target instanceof NPS && ((NPS) target).getMissileFlag()) {
                setPlayerScore(getPlayerScore() + 300);
            }

            if (target instanceof SpaceStation && ((SpaceStation) target).getSpaceStationFlag()) {
                reload();
            }
        }
    }
    //additional methods
    
    public void notifyObserver() {
        this.setChanged();
        this.notifyObservers(new GameWorldProxy(this));
    }

    private double randomCoord(double max) {
        r = new Random();
        return 0.0 + (max - 0.0) * r.nextDouble();
    }

    private int randomVal() {
        r = new Random();
        return 1 + (10 - 1) * r.nextInt();
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
