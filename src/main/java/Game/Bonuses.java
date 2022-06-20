package Game;

import Config.AppConfiguration;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Bonuses {
    private int numberOfBonus;
    private int x;
    private String path;
    private int y;
    private AppConfiguration appConfiguration = new AppConfiguration();


    public Bonuses(int numberOfBonus, int x,int y) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.numberOfBonus=numberOfBonus;
        this.x=x;
        this.y=y;
    }

    public String drawBonus(){
        if(numberOfBonus==0){
            path=appConfiguration.getProperties().getProperty("heartImg");
        }
        if(numberOfBonus==1){
            path=appConfiguration.getProperties().getProperty("speedImg");
        }
        if(numberOfBonus==2){
            path=appConfiguration.getProperties().getProperty("golfImg");
        }
        if(numberOfBonus==3){
            path=appConfiguration.getProperties().getProperty("rockImg");
        }
        return path;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getNumberOfBonus() {
        return numberOfBonus;
    }

    public void setNumberOfBonus(int numberOfBonus) {
        this.numberOfBonus = numberOfBonus;
    }

}
