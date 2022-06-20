import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;

public class SpaceShip {
    int x;
    int y;
    boolean alive;
    ArrayList <Shots> shoots=new ArrayList<>();
    int lives;
    int number;
    ArrayList <Bonuses> bonuses=new ArrayList<>();
    public SpaceShip(int x, int y, boolean alive, int lives, ArrayList <Bonuses> bonuses){
        this.x=x;
        this.y=y;
        this.alive=alive;
        this.lives=lives;
        this.bonuses=bonuses;
    }


    public void setBonuses(ArrayList<Bonuses> bonuses) {
        this.bonuses = bonuses;
    }
    int eatBonuses(){
        for (int i = 0; i < bonuses.size(); i++) {
            if(bonuses.get(i).getX()-50<x && bonuses.get(i).getX()+50>x){
                if(bonuses.get(i).getY()-50<y && bonuses.get(i).getY()+50>y){
                    number=bonuses.get(i).getNumberOfBonus();
                    bonuses.remove(i);
                    return number;
                }
            }

        }
        return -5;
    }
    int countPoints(int Time, int missedShoots, int goodShots, int lostLives){
        int points=0;
        if(Time<=200){
            points=points+10000;
        }if(Time<=400 && points>200){
            points=points+8000;
        }
        if(Time<=600 && points>400){
            points=points+6500;
        }
        if(points>600){
            points=points+5000;
        }
        points=points+(100*goodShots);
        points=points-(100*missedShoots);
        points=points-(500*lostLives);
        return points;

    }

    void shooting(){
        shoots.add(new Shots(x,y,false,0));
    }
    ArrayList getShots(){
        return shoots;
    }



}
