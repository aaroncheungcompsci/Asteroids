package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

import java.util.Observable;
import java.util.Observer;

public class PointsView extends Container implements Observer {
    private Label pointsValueLabel;
    private Label missileCountLabel;
    private Label timePassedLabel;
    private Label soundStatLabel;
    private Label livesLabel;

    public PointsView(GameWorld gw) {
        this.setLayout(new BoxLayout(BoxLayout.X_AXIS));

        //Instantiate text labels
        Label pointsTextLabel = new Label("Points: ");
        //Instantiating value labels
        pointsValueLabel = new Label("XXX");
        //Set color
        pointsTextLabel.getAllStyles().setFgColor(ColorUtil.rgb(0,0,255));

        Label missileTextLabel = new Label("Missiles: ");
        missileCountLabel = new Label("XXX");
        missileTextLabel.getAllStyles().setFgColor(ColorUtil.rgb(0,0,255));

        Label timeLabel = new Label("Time: ");
        timePassedLabel = new Label("XXX");
        timeLabel.getAllStyles().setFgColor(ColorUtil.rgb(0,0,255));

        Label soundLabel = new Label("Sound: ");
        soundStatLabel = new Label("XXX");
        soundLabel.getAllStyles().setFgColor(ColorUtil.rgb(0,0,255));

        Label livesTextLabel = new Label("Lives: ");
        livesLabel = new Label("XXX");
        livesTextLabel.getAllStyles().setFgColor(ColorUtil.rgb(0,0,255));

        //adding container with a BoxLayout

        this.add(pointsTextLabel);
        this.add(pointsValueLabel);
        this.add(missileTextLabel);
        this.add(missileCountLabel);
        this.add(timeLabel);
        this.add(timePassedLabel);
        this.add(soundLabel);
        this.add(soundStatLabel);
        this.add(livesTextLabel);
        this.add(livesLabel);
        this.getAllStyles().setPaddingRight(1);
    }

    @Override
    public void update(Observable o, Object arg) {
        IGameWorld gw = (IGameWorld) arg;

        int score = gw.getPlayerScore();
        pointsValueLabel.setText("" + (score > 99 ? "" : "0") + (score > 9 ? "" : "0") + score);
        missileCountLabel.setText("" + gw.getPSMissileCount());
        timePassedLabel.setText("" + gw.getClock());
        if (gw.getSound()) {
            soundStatLabel.setText("On");
        } else {
            soundStatLabel.setText("Off");
        }
        livesLabel.setText("" + gw.getLives());
        this.repaint();
    }
}
