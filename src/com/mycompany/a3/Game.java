package com.mycompany.a3;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;

import java.util.Random;


public class Game extends Form implements Runnable {
    private GameWorld gw;
    private MapView mv;
    private PointsView pv;
    private BackgroundSound bgSound;

    private boolean paused;
    private UITimer timer;

    public Game() {
        this.setLayout(new BorderLayout());
        timer = new UITimer(this);
        gw = new GameWorld();
        mv = new MapView(gw);
        pv = new PointsView(gw);
        gw.addObserver(mv);
        gw.addObserver(pv);
        bgSound = new BackgroundSound("audio.wav");

        //play();
        startTimer(timer);

        Container leftContainer = new Container();
        leftContainer.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        leftContainer.getAllStyles().setPaddingLeft(5);
        leftContainer.setScrollableY(true);

        Toolbar toolbar = new Toolbar();
        setToolbar(toolbar);

        toolbar.setTitle("Asteroids");

        AboutCommand myAbout = new AboutCommand(gw);
        SoundCommand mySound = new SoundCommand(gw);
        QuitCommand myQuit = new QuitCommand(gw);

        Command sideMenuItem1 = new Command("New");
        Command sideMenuItem2 = new Command("Save");
        Command sideMenuItem3 = new Command("Undo");

        CheckBox sound = new CheckBox("Sound");
        sound.setSelected(true);
        sound.setCommand(mySound);
        sound.getAllStyles().setBgTransparency(255);
        sound.getUnselectedStyle().setBgColor(ColorUtil.rgb(255,255,255));
        sound.getAllStyles().setFgColor(ColorUtil.BLACK);

        toolbar.addComponentToSideMenu(sound);
        toolbar.addCommandToSideMenu(sideMenuItem1);
        toolbar.addCommandToSideMenu(sideMenuItem2);
        toolbar.addCommandToSideMenu(sideMenuItem3);
        toolbar.addCommandToSideMenu(myAbout);
        toolbar.addCommandToSideMenu(myQuit);

        //-----BUTTONS-----//
        Button addAsteroid = new Button("Add Asteroid"); //a
        Button addPS = new Button("Add Player Ship"); //s
        Button addNPS = new Button("Add Non-Player Ship");
        Button addSpaceStation = new Button("Add Space Station");//b
        Button accelerate = new Button("Accelerate");
        Button decelerate = new Button("Decelerate");
        Button turnLeft = new Button("Turn Left");
        Button turnRight = new Button("Turn Right");
        Button turnLauncher = new Button("Turn Launcher");
        Button firePS = new Button("Fire PS");
        Button fireNPS = new Button("Fire NPS");
        Button jumpHS = new Button("Jump HyperSpace");
        Button reload = new Button("Reload"); //n
        /*
        Button killAsteroid = new Button("Kill Asteroid"); //k
        Button killNPS = new Button("Kill NPS");
        Button killPS = new Button("Kill PS");
        Button crash = new Button("Crash into Asteroid"); //c
        Button hit = new Button("Crash into NPS");
        Button collide = new Button("Collide Asteroids"); //x
        Button impact = new Button("Collide NPS & Asteroid");
        Button tick = new Button("Tick"); //t
        */
        Button pause = new Button();

        //-----------Asteroid-------------//
        addAsteroid.getAllStyles().setBgTransparency(255);
        addAsteroid.getUnselectedStyle().setBgColor(ColorUtil.rgb(0,50,150));
        addAsteroid.getAllStyles().setFgColor(ColorUtil.BLACK);

        addAsteroid.getAllStyles().setPadding(TOP, 1);
        addAsteroid.getAllStyles().setPadding(BOTTOM, 1);

        AddAsteroidCommand myAddAsteroid = new AddAsteroidCommand(gw);
        addAsteroid.setCommand(myAddAsteroid);

        //-----------PS------------//
        addPS.getAllStyles().setBgTransparency(255);
        addPS.getUnselectedStyle().setBgColor(ColorUtil.rgb(0,50,150));
        addPS.getAllStyles().setFgColor(ColorUtil.BLACK);

        addPS.getAllStyles().setPadding(TOP, 1);
        addPS.getAllStyles().setPadding(BOTTOM, 1);

        AddPSCommand myAddPS = new AddPSCommand(gw);
        addPS.setCommand(myAddPS);

        //----------NPS----------//
        addNPS.getAllStyles().setBgTransparency(255);
        addNPS.getUnselectedStyle().setBgColor(ColorUtil.rgb(0,50,150));
        addNPS.getAllStyles().setFgColor(ColorUtil.BLACK);

        addNPS.getAllStyles().setPadding(TOP, 1);
        addNPS.getAllStyles().setPadding(BOTTOM, 1);

        AddNPSCommand myAddNPS = new AddNPSCommand(gw);
        addNPS.setCommand(myAddNPS);

        //----------SpaceStation---------//
        addSpaceStation.getAllStyles().setBgTransparency(255);
        addSpaceStation.getUnselectedStyle().setBgColor(ColorUtil.rgb(0,50,150));
        addSpaceStation.getAllStyles().setFgColor(ColorUtil.BLACK);

        addSpaceStation.getAllStyles().setPadding(TOP, 1);
        addSpaceStation.getAllStyles().setPadding(BOTTOM, 1);

        AddSpaceStationCommand myAddSpaceStation = new AddSpaceStationCommand(gw);
        addSpaceStation.setCommand(myAddSpaceStation);

        //---------Accelerate----------//
        accelerate.getAllStyles().setBgTransparency(255);
        accelerate.getUnselectedStyle().setBgColor(ColorUtil.rgb(0,50,150));
        accelerate.getAllStyles().setFgColor(ColorUtil.BLACK);

        accelerate.getAllStyles().setPadding(TOP, 1);
        accelerate.getAllStyles().setPadding(BOTTOM, 1);

        IncreaseSpeedCommand myAccelerate = new IncreaseSpeedCommand(gw);
        accelerate.setCommand(myAccelerate);

        //---------Decelerate----------//
        decelerate.getAllStyles().setBgTransparency(255);
        decelerate.getUnselectedStyle().setBgColor(ColorUtil.rgb(0,50,150));
        decelerate.getAllStyles().setFgColor(ColorUtil.BLACK);

        decelerate.getAllStyles().setPadding(TOP, 1);
        decelerate.getAllStyles().setPadding(BOTTOM, 1);

        DecreaseSpeedCommand myDecelerate = new DecreaseSpeedCommand(gw);
        decelerate.setCommand(myDecelerate);

        //---------Left----------//
        turnLeft.getAllStyles().setBgTransparency(255);
        turnLeft.getUnselectedStyle().setBgColor(ColorUtil.rgb(0,50,150));
        turnLeft.getAllStyles().setFgColor(ColorUtil.BLACK);

        turnLeft.getAllStyles().setPadding(TOP, 1);
        turnLeft.getAllStyles().setPadding(BOTTOM, 1);

        TurnLeftCommand myLeft = new TurnLeftCommand(gw);
        turnLeft.setCommand(myLeft);

        //---------Right----------//
        turnRight.getAllStyles().setBgTransparency(255);
        turnRight.getUnselectedStyle().setBgColor(ColorUtil.rgb(0,50,150));
        turnRight.getAllStyles().setFgColor(ColorUtil.BLACK);

        turnRight.getAllStyles().setPadding(TOP, 1);
        turnRight.getAllStyles().setPadding(BOTTOM, 1);

        TurnRightCommand myRight = new TurnRightCommand(gw);
        turnRight.setCommand(myRight);

        //---------TurnLauncher----------//
        turnLauncher.getAllStyles().setBgTransparency(255);
        turnLauncher.getUnselectedStyle().setBgColor(ColorUtil.rgb(0,50,150));
        turnLauncher.getAllStyles().setFgColor(ColorUtil.BLACK);

        turnLauncher.getAllStyles().setPadding(TOP, 1);
        turnLauncher.getAllStyles().setPadding(BOTTOM, 1);

        TurnLauncherCommand myLauncher = new TurnLauncherCommand(gw);
        turnLauncher.setCommand(myLauncher);

        //---------FirePS----------//
        firePS.getAllStyles().setBgTransparency(255);
        firePS.getUnselectedStyle().setBgColor(ColorUtil.rgb(0,50,150));
        firePS.getAllStyles().setFgColor(ColorUtil.BLACK);

        firePS.getAllStyles().setPadding(TOP, 1);
        firePS.getAllStyles().setPadding(BOTTOM, 1);

        FirePSCommand myPSFire = new FirePSCommand(gw);
        firePS.setCommand(myPSFire);

        //---------FirePS----------//
        fireNPS.getAllStyles().setBgTransparency(255);
        fireNPS.getUnselectedStyle().setBgColor(ColorUtil.rgb(0,50,150));
        fireNPS.getAllStyles().setFgColor(ColorUtil.BLACK);

        fireNPS.getAllStyles().setPadding(TOP, 1);
        fireNPS.getAllStyles().setPadding(BOTTOM, 1);

        FireNPSCommand myNPSFire = new FireNPSCommand(gw);
        fireNPS.setCommand(myNPSFire);

        //---------JumpHS----------//
        jumpHS.getAllStyles().setBgTransparency(255);
        jumpHS.getUnselectedStyle().setBgColor(ColorUtil.rgb(0,50,150));
        jumpHS.getAllStyles().setFgColor(ColorUtil.BLACK);

        jumpHS.getAllStyles().setPadding(TOP, 1);
        jumpHS.getAllStyles().setPadding(BOTTOM, 1);

        JumpHyperSpaceCommand myJumpHS = new JumpHyperSpaceCommand(gw);
        jumpHS.setCommand(myJumpHS);

        //---------reload----------//
        reload.getAllStyles().setBgTransparency(255);
        reload.getUnselectedStyle().setBgColor(ColorUtil.rgb(0,50,150));
        reload.getAllStyles().setFgColor(ColorUtil.BLACK);

        reload.getAllStyles().setPadding(TOP, 1);
        reload.getAllStyles().setPadding(BOTTOM, 1);

        ReloadCommand myReload = new ReloadCommand(gw);
        reload.setCommand(myReload);

        //-------------Pause-------------//
        pause.getAllStyles().setBgTransparency(255);
        pause.getUnselectedStyle().setBgColor(ColorUtil.rgb(0,50,150));
        pause.getAllStyles().setFgColor(ColorUtil.BLACK);

        pause.getAllStyles().setPadding(TOP, 1);
        pause.getAllStyles().setPadding(BOTTOM, 1);

        PauseCommand myPause = new PauseCommand(timer, gw, this);
        pause.setCommand(myPause);

        //------------Keybinds---------------//

        playKeyBindings();

        //------------adding buttons to container-----------//
        leftContainer.add(addPS);
        leftContainer.add(reload);
        leftContainer.add(pause);
        leftContainer.add(addNPS);
        leftContainer.add(addSpaceStation);
        leftContainer.add(addAsteroid);
        leftContainer.add(accelerate);
        leftContainer.add(decelerate);
        leftContainer.add(turnLeft);
        leftContainer.add(turnRight);
        leftContainer.add(turnLauncher);
        leftContainer.add(firePS);
        leftContainer.add(fireNPS);
        leftContainer.add(jumpHS);

        //---------------OverFlow Menu----------------//
        //toolbar.addCommandToOverflowMenu(myAddAsteroid);
        //toolbar.addCommandToOverflowMenu(myAddSpaceStation);
        //toolbar.addCommandToOverflowMenu(myAddNPS);
        //toolbar.addCommandToOverflowMenu(myReload);
        //toolbar.addCommandToOverflowMenu(myTick);
        //toolbar.addCommandToOverflowMenu(myQuit);

        this.add(BorderLayout.CENTER, mv);
        this.add(BorderLayout.NORTH, pv);
        this.add(BorderLayout.WEST, leftContainer);
        this.show();

        gw.setGameHeight(mv.getHeight());
        gw.setGameWidth(mv.getWidth());

        start();
    }

    public void start() {
        gw.addNewPS();
        gw.addNewSpaceStation();
        for (int i = 0; i < 20; i++) {
            gw.addNewAsteroid();
        }
    }

    public void startTimer(UITimer t) {
        t.schedule(1, true, this);
    }

    public void stopTimer(UITimer t) {
        t.cancel();
    }

    public void playKeyBindings()
    {
        /*
        addKeyListener('a', AddAsteroidCommand.asteroid(gw));
        addKeyListener('y', );
        addKeyListener('b', AddSpaceStationCommand.spaceStation(gw));
        addKeyListener('s', AddShipCommand.ship(gw));
        addKeyListener(-91, myAccelerate);
        addKeyListener(-92, myDecelerate);
        addKeyListener(-93, myLeft);
        addKeyListener(-94, myRight);
        addKeyListener(-90, myPSFire);
        addKeyListener('j', myJumpHS);//jump hyperspace
        addKeyListener('n', LoadMissileCommand.loadMissile(gw));
        addKeyListener('q', QuitCommand.quit());
        mv.setHandlesInput(false);
        */


        addKeyListener(-91, new IncreaseSpeedCommand(gw));
        addKeyListener(-92, new DecreaseSpeedCommand(gw));
        addKeyListener(-93, new TurnLeftCommand(gw));
        addKeyListener(-94, new TurnRightCommand(gw));
        addKeyListener(-90, new FirePSCommand(gw));
        addKeyListener('j', new JumpHyperSpaceCommand(gw));//jump hyperspace
    }


    public void pauseKeyBindings() {
        removeKeyListener(-91, new IncreaseSpeedCommand(gw));
        removeKeyListener(-92, new DecreaseSpeedCommand(gw));
        removeKeyListener(-93, new TurnLeftCommand(gw));
        removeKeyListener(-94, new TurnRightCommand(gw));
        removeKeyListener(-90, new FirePSCommand(gw));
        removeKeyListener('j', new JumpHyperSpaceCommand(gw));//jump hyperspace
    }

    @Override
    public void run() {
        mv.repaint();
        //mv.setHandlesInput(true);
        gw.advanceGameTime();
    }

    /*
    private void play() {
        Label myLabel = new Label("Enter a command:");
        this.addComponent(myLabel);
        final TextField myTextField = new TextField();
        this.addComponent(myTextField);
        this.show();
        myTextField.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent evt) {
               String aCommand = myTextField.getText();
               myTextField.clear();

               switch (aCommand.charAt(0)) {
                   case 'a':
                       gw.addNewAsteroid();
                       break;
                   case 'y':
                       gw.addNewNPS();
                       break;
                   case 'b':
                       gw.addNewSpaceStation();
                       break;
                   case 's':
                       gw.addNewPS();
                       break;
                   case 'i':
                       gw.increaseSpeed();
                       break;
                   case 'd':
                       gw.decreaseSpeed();
                       break;
                   case 'l':
                       gw.turnLeft();
                       break;
                   case 'r':
                       gw.turnRight();
                       break;
                   case '<':
                       gw.turnMissileLauncher();
                       break;
                   case 'f':
                       gw.fire();
                       break;
                   case 'L':
                       gw.launch();
                       break;
                   case 'j':
                       gw.jumpHyperspace();
                       break;
                   case 'n':
                       gw.reload();
                       break;
                   case 'k':
                       gw.killAsteroid();
                       break;
                   case 'e':
                       gw.killNPS();
                       break;
                   case 'E':
                       gw.killPS();
                       break;
                   case 'c':
                       gw.crash();
                       break;
                   case 'h':
                       gw.hit();
                       break;
                   case 'x':
                       gw.collide();
                       break;
                   case 'I':
                       gw.impact();
                       break;
                   case 't':
                       gw.tick();
                       break;
                   case 'p':
                       gw.print();
                       break;
                   case 'm':
                       gw.map();
                       break;
                   case 'q':
                       gw.quit();
                       break;
                   default:
                       System.out.println("Invalid command.");
                       break;
               }
           }
        });

    }
    */
}
