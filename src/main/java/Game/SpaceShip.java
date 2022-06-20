package Game;

import java.util.ArrayList;

public class SpaceShip {
    private int x;
    private int y;
    private boolean alive;
    private ArrayList<Shots> shoots = new ArrayList<>();
    private int lives;
    private int number;
    private ArrayList<Bonuses> bonuses;

    public SpaceShip(int x, int y, boolean alive, int lives, ArrayList<Bonuses> bonuses) {
        this.x = x;
        this.y = y;
        this.alive = alive;
        this.lives = lives;
        this.bonuses = bonuses;
    }


    public void setBonuses(ArrayList<Bonuses> bonuses) {
        this.bonuses = bonuses;
    }

    public int eatBonuses() {
        for (int i = 0; i < bonuses.size(); i++) {
            if (bonuses.get(i).getX() - 50 < x && bonuses.get(i).getX() + 50 > x) {
                if (bonuses.get(i).getY() - 50 < y && bonuses.get(i).getY() + 50 > y) {
                    number = bonuses.get(i).getNumberOfBonus();
                    bonuses.remove(i);
                    return number;
                }
            }

        }
        return -5;
    }

    public int countPoints(int Time, int missedShoots, int goodShots, int lostLives) {
        int points = 0;
        if (Time <= 200) {
            points = points + 10000;
        }
        if (Time <= 400 && points > 200) {
            points = points + 8000;
        }
        if (Time <= 600 && points > 400) {
            points = points + 6500;
        }
        if (points > 600) {
            points = points + 5000;
        }
        points = points + (100 * goodShots);
        points = points - (100 * missedShoots);
        points = points - (500 * lostLives);
        return points;

    }

    public void shooting() {
        shoots.add(new Shots(x, y, false, 0));
    }

    public ArrayList getShots() {
        return shoots;
    }


    public ArrayList<Bonuses> getBonuses() {
        return bonuses;
    }

    public ArrayList<Shots> getShoots() {
        return shoots;
    }

    public int getLives() {
        return lives;
    }

    public int getNumber() {
        return number;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setShoots(ArrayList<Shots> shoots) {
        this.shoots = shoots;
    }

}
