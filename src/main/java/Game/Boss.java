package Game;

import Config.AppConfiguration;
import Swing.MyJPanel;
import Audio.AudioPlayer;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;

public class Boss {
    private int x;
    private int y;
    private int health;
    private int moveX = 5;
    private int moveY = 5;
    private int destinationX;
    private int destinationY;
    private long start;
    private float elapsedTimeSec;
    private long elapsedTimeMillis;
    private boolean activated;
    private AppConfiguration appConfiguration = new AppConfiguration();
    AudioPlayer audio = new AudioPlayer(appConfiguration.getProperties().getProperty("boosSound"));

    ArrayList<String> shots = new ArrayList<>();

    public Boss(int x, int y, int health, boolean activated) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.x = x;
        this.y = y;
        this.health = health;
        this.activated = activated;
        start = System.currentTimeMillis();
        drawDestinationX();
        drawDestinationY();
    }

    public void playAudio() {
        audio.play();
    }

    void stopAudio() {
        audio.pause();
    }


    public void shotingBoss() {
        elapsedTimeMillis = System.currentTimeMillis() - start;
        elapsedTimeSec = elapsedTimeMillis / 1000F;
        checkingShots();
        if (elapsedTimeSec >= 3.5) {
            shots.add(((x - 100) + "," + y + "," + "0"));
            shots.add(((x - 100) + "," + (y - 100) + "," + "1"));
            shots.add(((x + 100) + "," + y + "," + "2"));
            shots.add(((x + 100) + "," + (y + 100) + "," + "3"));
            shots.add((x + "," + (y - 100) + "," + "4"));
            shots.add(((x + 100) + "," + (y - 100) + "," + "5"));
            shots.add(((x - 100) + "," + (y + 100) + "," + "6"));
            shots.add((x + "," + (y + 100) + "," + "7"));
            start = System.currentTimeMillis();
        }
    }

    void checkingShots() {
        String[] coords;
        for (int i = 0; i < shots.size(); i++) {
            coords = shots.get(i).split(",");
            if (Integer.parseInt(coords[0]) <= 0 || Integer.parseInt(coords[1]) <= 0 || Integer.parseInt(coords[0]) > 1000 || Integer.parseInt(coords[1]) >= 750) {
                shots.remove(i);
            }
        }
    }

    public void moveBoss() {
        if (destinationX != x && destinationY != y) {
            if (destinationX > x && destinationY > y) {
                x += moveX;
                y += moveY;
            }
            if (destinationX < x && destinationY > y) {
                x -= moveX;
                y += moveY;
            }
            if (destinationX < x && destinationY < y) {
                x -= moveX;
                y -= moveY;
            }
            if (destinationX > x && destinationY < y) {
                x += moveX;
                y -= moveY;
            }
        } else {
            if (destinationX > x) {
                x += moveX;
            }
            if (destinationX < x) {
                x -= moveX;
            }
            if (destinationY > y) {
                y += moveY;
            }
            if (destinationY < y) {
                y -= moveY;
            }
        }
        if (destinationX == x && destinationY == y) {
            drawDestinationX();
            drawDestinationY();
        }

    }

    public ArrayList getShots() {
        return shots;
    }

    void drawDestinationX() {
        destinationX = (int) (Math.random() * 900);
        if (destinationX % 10 != 0)
            drawDestinationX();
    }

    void drawDestinationY() {
        destinationY = (int) (Math.random() * 650);
        if (destinationY % 10 != 0)
            drawDestinationY();
    }

    public void moveBossShot(String[] coordss, int i, MyJPanel myJPanel) {
        if (coordss[2].equals("0")) {
            myJPanel.getDucksShots().set(i, (Integer.parseInt(coordss[0]) - myJPanel.getSpeed()) + "," + coordss[1] + "," + coordss[2]);
        }
        if (coordss[2].equals("1")) {
            myJPanel.getDucksShots().set(i, (Integer.parseInt(coordss[0]) - myJPanel.getSpeed()) + "," + (Integer.parseInt(coordss[1]) - myJPanel.getSpeed()) + "," + coordss[2]);

        }
        if (coordss[2].equals("2")) {
            myJPanel.getDucksShots().set(i, (Integer.parseInt(coordss[0]) + myJPanel.getSpeed()) + "," + coordss[1] + "," + coordss[2]);
        }
        if (coordss[2].equals("3")) {
            myJPanel.getDucksShots().set(i, (Integer.parseInt(coordss[0]) + myJPanel.getSpeed()) + "," + (Integer.parseInt(coordss[1]) + myJPanel.getSpeed()) + "," + coordss[2]);

        }
        if (coordss[2].equals("4")) {
            myJPanel.getDucksShots().set(i, (coordss[0]) + "," + (Integer.parseInt(coordss[1]) - myJPanel.getSpeed()) + "," + coordss[2]);

        }
        if (coordss[2].equals("5")) {
            myJPanel.getDucksShots().set(i, (Integer.parseInt(coordss[0]) + myJPanel.getSpeed()) + "," + (Integer.parseInt(coordss[1]) - myJPanel.getSpeed()) + "," + coordss[2]);
        }
        if (coordss[2].equals("6")) {
            myJPanel.getDucksShots().set(i, (Integer.parseInt(coordss[0]) - myJPanel.getSpeed()) + "," + (Integer.parseInt(coordss[1]) + myJPanel.getSpeed()) + "," + coordss[2]);


        }
        if (coordss[2].equals("7")) {
            myJPanel.getDucksShots().set(i, (coordss[0]) + "," + (Integer.parseInt(coordss[1]) + myJPanel.getSpeed()) + "," + coordss[2]);

        }
    }

    public void setMoveX(int moveX) {
        this.moveX = moveX;
    }

    public void setMoveY(int moveY) {
        this.moveY = moveY;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }


    public boolean getActivated() {
        return activated;
    }
}
