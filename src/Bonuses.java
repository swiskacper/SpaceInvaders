import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Bonuses {
    int numberOfBonus;
    int x;
    String path;
    long start;
    float elapsedTimeSec;
    long elapsedTimeMillis;
    int y;

    public Bonuses(int numberOfBonus, int x,int y) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.numberOfBonus=numberOfBonus;
        this.x=x;
        this.y=y;
        start=System.currentTimeMillis();
    }



    String getPath(){
        if(numberOfBonus==0){
            path="images&sounds\\serce.png";
        }
        if(numberOfBonus==1){
            path="images&sounds\\speed.png";
        }
        if(numberOfBonus==2){
            path="images&sounds\\predkośćiomiez.png";
        }
        if(numberOfBonus==3){
            path="images&sounds\\skała.png";
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
