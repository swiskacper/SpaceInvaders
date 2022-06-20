package Swing;

import Audio.ShortAudio;
import Config.AppConfiguration;
import Game.SpaceShip;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class ActionsKeyboard implements KeyListener {
    private SpaceShip spaceShip;
    private long start;
    private float elapsedTimeSec;
    private long elapsedTimeMillis;
    private int move;
    private long gameTime;
    private int numberOfShots;
    private double timeWithoutShoot = 1;
    private final AppConfiguration appConfiguration = new AppConfiguration();
    ShortAudio audio = new ShortAudio(appConfiguration.getProperties().getProperty("shotSound"));


    public ActionsKeyboard(SpaceShip spaceShip, int speed) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.spaceShip = spaceShip;
        this.move = speed;

        gameTime = System.currentTimeMillis();
        start = System.currentTimeMillis();
    }

    int getGameTime() {
        elapsedTimeMillis = System.currentTimeMillis() - gameTime;
        elapsedTimeSec = elapsedTimeMillis / 1000F;
        return (int) elapsedTimeSec;
    }

    public int getNumberOfShots() {
        return numberOfShots;
    }

    public void setTimeWithoutShoot(double timeWithoutShoot) {
        this.timeWithoutShoot = timeWithoutShoot;
    }

    public double getTimeWithoutShoot() {
        return timeWithoutShoot;
    }

    public void setMove(int move) {
        this.move = move;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            spaceShip.setX(spaceShip.getX() - move);

        }
        if (key == KeyEvent.VK_RIGHT) {
            spaceShip.setX(spaceShip.getX() + move);

        }

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

        int key = keyEvent.getKeyCode();
        elapsedTimeMillis = System.currentTimeMillis() - start;
        elapsedTimeSec = elapsedTimeMillis / 1000F;
        if (key == KeyEvent.VK_LEFT && spaceShip.getX() - move > 0) {
            spaceShip.setX(spaceShip.getX() - move);

        }
        if (key == KeyEvent.VK_RIGHT && spaceShip.getX() + move < 950) {
            spaceShip.setX(spaceShip.getX() + move);

        }
        if (key == KeyEvent.VK_DOWN && spaceShip.getY() + move < 710) {
            spaceShip.setY(spaceShip.getY() + move);

        }
        if (key == KeyEvent.VK_UP && spaceShip.getY() - move > 150) {
            spaceShip.setY(spaceShip.getY() - move);
        }

        if (key == KeyEvent.VK_SPACE && elapsedTimeSec >= timeWithoutShoot) {
            start = System.currentTimeMillis();
            ++numberOfShots;
            audio.play();
            spaceShip.shooting();
        }

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
