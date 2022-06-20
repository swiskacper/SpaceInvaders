package Game;

import Config.AppConfiguration;
import Audio.ShortAudio;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;

public class Death {
    private SpaceShip spaceShip;
    private Ducks duck;
    private String[] coord;
    private String[] coords2;
    private long start;
    private float elapsedTimeSec;
    private int godShots = 0;
    private int lostLives;
    private long elapsedTimeMillis;
    private ArrayList<Shots> shots;
    private ArrayList<String> ducksShots;
    private Boss boss;
    private AppConfiguration appConfiguration = new AppConfiguration();
    private ShortAudio audioDuckDeath = new ShortAudio(appConfiguration.getProperties().getProperty("duckSound"));
    private ShortAudio audioSpaceShipDeath = new ShortAudio(appConfiguration.getProperties().getProperty("dinosaurSound"));
    private ShortAudio audioBossDeath = new ShortAudio(appConfiguration.getProperties().getProperty("bossDeathSound"));

    public Death(SpaceShip spaceShip, Ducks duck, ArrayList<Shots> shots, ArrayList<String> ducksShots, Boss boss) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.spaceShip = spaceShip;
        this.duck = duck;
        this.shots = shots;
        this.boss = boss;
        this.ducksShots = ducksShots;
        start = System.currentTimeMillis();
    }

    public void checkDuckShot() {
        checkElapsedTime();
        if (elapsedTimeSec > 3)
            for (int i = 0; i < ducksShots.size(); i++) {
                coords2 = ducksShots.get(i).split(",");
                if (Integer.parseInt(coords2[0]) - 50 < spaceShip.getX() && Integer.parseInt(coords2[0]) + 50 > spaceShip.getX()) {

                    if (Integer.parseInt(coords2[1]) - 50 < spaceShip.getY() && Integer.parseInt(coords2[1]) + 50 > spaceShip.getY()) {
                        audioSpaceShipDeath.play();
                        start = System.currentTimeMillis();
                        ++lostLives;
                        spaceShip.setLives(spaceShip.getLives()-1);
                        ducksShots.remove(i);
                    }
                }

            }
    }

    private void checkElapsedTime() {
        if (start != 0) {
            elapsedTimeMillis = System.currentTimeMillis() - start;
            elapsedTimeSec = elapsedTimeMillis / 1000F;
        }
    }

    public void checkDuckCoordinates() {
        checkElapsedTime();
        if (elapsedTimeSec > 3)
            for (int i = 0; i < duck.getDucksCoordinates().size(); i++) {
                coord = duck.getDucksCoordinates().get(i).split(",");
                if (spaceShip.getX() >= Integer.parseInt(coord[0]) && spaceShip.getX() <= Integer.parseInt(coord[0]) + 20 && (spaceShip.getY() <= Integer.parseInt(coord[1]) + 50 && spaceShip.getY() >= Integer.parseInt(coord[1]))) {
                    audioSpaceShipDeath.play();
                    start = System.currentTimeMillis();
                    ++lostLives;
                    spaceShip.setLives(spaceShip.getLives()-1);
                }
            }
    }

    public void checkBossCoordinates() {
        checkElapsedTime();
        if (elapsedTimeSec > 3)
            if (spaceShip.getX() >= boss.getX() && spaceShip.getX() <= boss.getX() + 100 && (spaceShip.getY() <= boss.getY() + 100 && spaceShip.getY() >= boss.getY())) {
                audioSpaceShipDeath.play();
                start = System.currentTimeMillis();
                ++lostLives;
                spaceShip.setLives(spaceShip.getLives()-1);
            }

    }

    public void checkDucksHealth() {
        boolean bool;
        for (int i = 0; i < duck.getDucksCoordinates().size(); i++) {
            bool = false;
            coord = duck.getDucksCoordinates().get(i).split(",");
            for (int k = 0; k < shots.size(); k++) {
                if (shots.get(k).getY() <= 0 && !bool) {
                    shots.remove(k);
                    bool = true;
                    continue;
                }
                if (shots.get(k).getX() <= Integer.parseInt(coord[0]) + 20 && shots.get(k).getX() >= Integer.parseInt(coord[0]) - 20 && !bool) {
                    if (shots.get(k).getY() <= Integer.parseInt(coord[1]) + 65 && shots.get(k).getY() >= Integer.parseInt(coord[1]) - 65) {
                        shots.remove(k);
                        ++godShots;
                        audioDuckDeath.play();
                        duck.getDucksCoordinates().remove(i);
                    }
                }

            }
        }
    }

    public void checkBossHealth() {
        boolean bool;

        for (int i = 0; i < 2; i++) {
            bool = false;
            for (int k = 0; k < shots.size(); k++) {
                if (shots.get(k).getY() <= 0 && !bool) {
                    shots.remove(k);
                    bool = true;
                    continue;
                }
                if (shots.get(k).getX() <= boss.getX() + 100 && shots.get(k).getX() >= boss.getX() - 100 && !bool) {
                    if (shots.get(k).getY() <= boss.getY() + 120 && shots.get(k).getY() >= boss.getY() - 100) {
                        audioBossDeath.play();
                        shots.remove(k);
                        ++godShots;
                        boss.setHealth(boss.getHealth() - 5);
                        System.out.println(boss.getHealth());
                    }
                }

            }
        }
    }

    public void setLostLifes(int lostLifes) {
        this.lostLives = lostLifes;
    }

    public int getLostLifes() {
        return lostLives;
    }

    public int getGodShots() {
        return godShots;
    }

    public void setDucksShots(ArrayList<String> ducksShots2) {
        this.ducksShots = ducksShots2;
    }

    public void setShots(ArrayList<Shots> shotss) {
        this.shots = shotss;
    }
}
