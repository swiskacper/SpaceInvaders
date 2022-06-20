package Game;

import Config.AppConfiguration;
import Swing.MyJPanel;
import Client.RandomNumberApiClient;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;

public class Ducks {
    private int numberOfDucksHard=0;
    private int numberOfDucksEasy=0;
    private final ArrayList<String> ducksCoordinates;
    private final ArrayList<Integer> boolDucks;
    private int X = 0;
    private int Y = 0;
    private int numberOfDucks;
    private int changePosition;
    private boolean positionDirection;
    private long start;
    private String[] coord;
    private int down = 20;
    private int count;
    private int number1;
    private int speed = 5;
    private int level = 1;
    private boolean leveling = false;
    private boolean makingDucks = false;
    private int difficultyLevel = 1;
    private boolean isDraw= false;
    private final Boss boss;
    private AppConfiguration appConfiguration = new AppConfiguration();
    private RandomNumberApiClient randomNumberApiClient = new RandomNumberApiClient(new RestTemplate());

    public Ducks(ArrayList<String> ducksCoordinates, ArrayList<Integer> boolDucks, Boss boss, int difficultyLevel) throws ExecutionException, InterruptedException {
        this.difficultyLevel = difficultyLevel;
        this.ducksCoordinates = ducksCoordinates;
        this.boolDucks = boolDucks;
        this.boss = boss;
        start = System.currentTimeMillis();
        numberOfDucksEasy = Integer.parseInt(drawNumberOfDucks(45, 55));
        numberOfDucksHard = Integer.parseInt(drawNumberOfDucks(55, 65));
    }

    public void setDifficultyLevel(int difficultyLevel2) {
        this.difficultyLevel = difficultyLevel2;

    }

    public void checkLevel() throws ExecutionException, InterruptedException {
        setDifficultyLevel(1, 0, 0);
        setDifficultyLevel(2, numberOfDucksHard, numberOfDucksEasy+15);

    }

    private String drawNumberOfDucks(int min, int max) throws InterruptedException, ExecutionException {
        return randomNumberApiClient.sendRequest(min, max)
                .get()
                .getBody()
                .replace("[", "")
                .replace("]", "");
    }

    private void setDifficultyLevel(int i, int i2, int i3) {
        if (difficultyLevel == i) {
            if (level == 1) {
                numberOfDucks = i2;

            }
            if (level == 2 && !makingDucks) {
                makingDucks = true;
                setX(-150);
                setY(0);
                numberOfDucks = i3;
                makeDucks();

            }
            if (ducksCoordinates.size() == 0 && level != 1) {
                leveling = !leveling;
            }
            if (level == 3) {
                boss.setActivated(true);
            }
        }
    }

    public void checkLeveling() {
        if (ducksCoordinates.size() == 0 && !leveling && level <= 3) {
            level = level + 1;
            leveling = true;
        }
    }

    public void duckSegregation() {
        Set<String> set = new TreeSet<>();
        set.addAll(ducksCoordinates);
        ducksCoordinates.removeAll(ducksCoordinates);
        ducksCoordinates.addAll(set);

    }


    public void makeDucks() {
        int a = X;
        int b = Y;
        String coords;
        int c = 0;
        for (int i = 0; i < numberOfDucks; i++) {
            coords = a + "," + b;
            a = a + 45;
            c = c + 1;
            if (c == 15 && i != 0) {
                b = b + 45;
                a = X;
                c = 0;
            }
            ducksCoordinates.add(coords);
        }
    }


    public void changeDirection() {
        if (ducksCoordinates.size() > 0) {
            coord = ducksCoordinates.get(0).split(",");
            if (Integer.parseInt(coord[0]) <= 0) {
                changePosition = 2;
                positionDirection = true;
            }
            if (Integer.parseInt(coord[1]) >= 300) {
                down = -20;

            }
            if (Integer.parseInt(coord[1]) <= 5) {
                down = 20;

            }

            coord = ducksCoordinates.get(ducksCoordinates.size() - 1).split(",");
            if (Integer.parseInt(coord[0]) >= 950) {
                changePosition = 1;

            }
        }


    }

    public void moveDucks() {
        String coord2;
        int number;
        if (changePosition == 1) {
            for (int i = 0; i < ducksCoordinates.size(); i++) {
                coord = ducksCoordinates.get(i).split(",");
                number = Integer.parseInt(coord[0]) - speed;
                coord2 = String.valueOf(number) + "," + coord[1];
                ducksCoordinates.set(i, coord2);
            }

        } else {
            for (int i = 0; i < ducksCoordinates.size(); i++) {
                coord = ducksCoordinates.get(i).split(",");
                number = Integer.parseInt(coord[0]) + speed;
                coord2 = String.valueOf(number) + "," + coord[1];
                ducksCoordinates.set(i, coord2);
            }
        }
        if (positionDirection) {
            positionDirection = false;
            for (int i = 0; i < ducksCoordinates.size(); i++) {
                coord = ducksCoordinates.get(i).split(",");
                number = Integer.parseInt(coord[1]) + down;
                coord2 = coord[0] + "," + String.valueOf(number);
                ducksCoordinates.set(i, coord2);
            }
            count = count + 1;
        }
    }

    public int shootingDucks() {
        number1 = 1000;
        long elapsedTimeMillis = System.currentTimeMillis() - start;
        float elapsedTimeSec = elapsedTimeMillis / 1000F;
        if (ducksCoordinates.size() >= 70 && elapsedTimeSec >= 0.8) {
            setShoot();
        }
        if (ducksCoordinates.size() < 70 && ducksCoordinates.size() >= 50 && elapsedTimeSec >= 0.95) {
            setShoot();
        }
        if (ducksCoordinates.size() >= 40 && ducksCoordinates.size() < 50 && elapsedTimeSec >= 1) {
            setShoot();
        }
        if (ducksCoordinates.size() >= 30 && ducksCoordinates.size() < 40 && elapsedTimeSec >= 2) {
            setShoot();
        }
        if (ducksCoordinates.size() < 30 && elapsedTimeSec >= 2) {
            setShoot();
        }

        return number1;
    }

    private void setShoot() {
        number1 = drawDuck();
        setStart();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public ArrayList<String> getDucksCoordinates() {
        return ducksCoordinates;
    }

    public ArrayList<Integer> getBoolDucks() {
        return boolDucks;
    }

    void setX(int x) {
        this.X = x;
    }

    void setY(int y) {
        this.Y = y;
    }

    void setNumberOfDucks(int a) {
        this.numberOfDucks = a;
    }

    void setStart() {
        start = System.currentTimeMillis();
    }

    int drawDuck() {
        return (int) (Math.random() * ducksCoordinates.size());
    }

    public void moveDucksShots(Graphics g, MyJPanel myJPanel) {
        for (int i = 0; i < myJPanel.getDucksShots().size(); i++) {
            myJPanel.duckCoords = myJPanel.getDucksShots().get(i).split(",");
            myJPanel.drawImage(g, appConfiguration.getProperties().getProperty("laser"), Integer.parseInt(myJPanel.duckCoords[0]), Integer.parseInt(myJPanel.duckCoords[1]));

            int a = Integer.parseInt(myJPanel.duckCoords[1]) + myJPanel.getSpeed();
            myJPanel.duckCoords[1] = String.valueOf(a);
            myJPanel.spaceShipCoords = myJPanel.duckCoords[0] + "," + myJPanel.duckCoords[1];
            myJPanel.getDucksShots().set(i, myJPanel.spaceShipCoords);
        }
    }
}
