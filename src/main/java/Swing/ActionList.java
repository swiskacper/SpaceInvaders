package Swing;

import Audio.AudioPlayer;
import Game.Bonuses;
import Game.Boss;
import Game.Death;
import Game.Ducks;
import Game.Shots;
import Game.SpaceShip;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ActionList implements java.awt.event.ActionListener {
    private static final ArrayList<Shots> shots = new ArrayList<>();
    private final JFrame window2;
    private final AudioPlayer player;
    private boolean bool;

    public ActionList(JFrame window2, AudioPlayer player, boolean bool) {
        this.bool = bool;
        this.player = player;
        this.window2 = window2;

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        player.pause();
        window2.dispose();
        JFrame window = new JFrame("GAME");
        window.setUndecorated(true);
        ArrayList<String> ducksCoordinates = new ArrayList<>();
        ArrayList<Integer> boolDucks = new ArrayList<>();
        ArrayList<String> ducksShots = new ArrayList<>();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        boolean alive = true;
        ArrayList<Bonuses> bonuses = new ArrayList<>();
        Boss boss = null;
        try {
            boss = new Boss(0, 0, 120, false);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        Ducks ducks = null;
        try {
            ducks = new Ducks(ducksCoordinates, boolDucks, boss, 1);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!bool) {
            ducks.setDifficultyLevel(2);
        }
        SpaceShip spaceShip = new SpaceShip(500, 700, alive, 3, bonuses);
        Death death = null;
        try {
            death = new Death(spaceShip, ducks, shots, ducksShots, boss);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        int speed = 5;
        ActionsKeyboard AK = null;
        try {
            AK = new ActionsKeyboard(spaceShip, speed);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        window.addKeyListener(AK);
        MyJPanel panel = null;
        try {
            panel = new MyJPanel(window, spaceShip, ducks, death, shots, AK, boss);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        window.add(panel);
        panel.setLayout(null);
        window.pack();
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }
}
