import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class Death {
    SpaceShip spaceShip;
    Ducks duck;
    String[] coord;
    String [] coords2;
    long start;
    float elapsedTimeSec;
    int godShots=0;
    int lostLifes;
    long elapsedTimeMillis;
    ArrayList<Shots> shots = new ArrayList<>();
    ArrayList <String> ducksShots=new ArrayList<>();
    Boss boss;
    ShortAudio audioDuckDeath=new ShortAudio("D:\\SpaceShipInvader\\images&sounds\\ringt-nature-duck1.au");
    ShortAudio audioSpaceShipDeath=new ShortAudio("D:\\SpaceShipInvader\\images&sounds\\Dinosaur-Roar-SoundBible.com-605392672.au");
    ShortAudio audioBossDeath=new ShortAudio("D:\\SpaceShipInvader\\images&sounds\\Horse-Angry-Neigh-And-Breathing-www.fesliyanstudios.com.au");

    public Death(SpaceShip spaceShip, Ducks duck, ArrayList<Shots> shots, ArrayList <String> ducksShots, Boss boss) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.spaceShip = spaceShip;
        this.duck = duck;
        this.shots = shots;
        this.boss=boss;
        this.ducksShots=ducksShots;
        start=System.currentTimeMillis();


    }

    void dieingSpaceShipV2(){
        if(start!=0){
            elapsedTimeMillis = System.currentTimeMillis()- start;
            elapsedTimeSec = elapsedTimeMillis / 1000F;
        }if(elapsedTimeSec>3)
        for (int i = 0; i < ducksShots.size(); i++) {
            coords2 = ducksShots.get(i).split(",");
            if(Integer.parseInt(coords2[0])-50<spaceShip.x && Integer.parseInt(coords2[0])+50>spaceShip.x){

                if(Integer.parseInt(coords2[1])-50<spaceShip.y && Integer.parseInt(coords2[1])+50>spaceShip.y){
                    audioSpaceShipDeath.play();
                    start=System.currentTimeMillis();
                    ++lostLifes;
                    spaceShip.lives = spaceShip.lives - 1;
                    ducksShots.remove(i);
                }
            }

        }
    }

    void dieingSpaceShip() {
        if(start!=0){
            elapsedTimeMillis = System.currentTimeMillis()- start;
            elapsedTimeSec = elapsedTimeMillis / 1000F;
        }if(elapsedTimeSec>3)
        for (int i = 0; i < duck.getDucksCoordinates().size(); i++) {
            coord = duck.getDucksCoordinates().get(i).split(",");
            if (spaceShip.x >=Integer.parseInt(coord[0]) && spaceShip.x <=Integer.parseInt(coord[0])+20 && (spaceShip.y <= Integer.parseInt(coord[1]) + 50 && spaceShip.y >= Integer.parseInt(coord[1]))) {
                audioSpaceShipDeath.play();
                start=System.currentTimeMillis();
                ++lostLifes;
                spaceShip.lives = spaceShip.lives - 1;
            }
        }
    }
    void dieingSpaceShipV3() {
        if(start!=0){
            elapsedTimeMillis = System.currentTimeMillis()- start;
            elapsedTimeSec = elapsedTimeMillis / 1000F;
        }if(elapsedTimeSec>3)
                if (spaceShip.x >=boss.getX() && spaceShip.x <=boss.getX()+100 && (spaceShip.y <= boss.getY() + 100 && spaceShip.y >=boss.getY())) {
                    audioSpaceShipDeath.play();
                    start=System.currentTimeMillis();
                    ++lostLifes;
                    spaceShip.lives = spaceShip.lives - 1;
                }

    }

    void dieingDucks() {
        boolean bool;
        for (int i = 0; i < duck.getDucksCoordinates().size(); i++) {
            bool=false;
            coord = duck.getDucksCoordinates().get(i).split(",");
            for(int k=0;k<shots.size();k++) {
                if(shots.get(k).getY()<=0 && !bool){
                    shots.remove(k);
                    bool=true;
                    continue;
                }
                if (shots.get(k).getX() <= Integer.parseInt(coord[0])+20 && shots.get(k).getX() >= Integer.parseInt(coord[0])-20  && !bool) {
                    if(shots.get(k).getY()<=Integer.parseInt(coord[1])+65 && shots.get(k).getY()>=Integer.parseInt(coord[1])-65 ){
                        shots.remove(k);
                        ++godShots;
                        audioDuckDeath.play();
                        duck.getDucksCoordinates().remove(i);
                    }
                }

            }
        }
    }
    void dieingBoss(){
        boolean bool;

        for (int i = 0; i < 2; i++) {
            bool=false;
            for(int k=0;k<shots.size();k++) {
                if(shots.get(k).getY()<=0 && !bool){
                    shots.remove(k);
                    bool=true;
                    continue;
                }
                if (shots.get(k).getX() <= boss.getX()+100 && shots.get(k).getX() >= boss.getX()-100  && !bool) {
                    if(shots.get(k).getY()<=boss.getY()+120 && shots.get(k).getY()>=boss.getY()-100 ){
                        audioBossDeath.play();
                        shots.remove(k);
                        ++godShots;
                        boss.setHealth(boss.getHealth()-5);
                        System.out.println(boss.health);
                    }
                }

            }
        }
    }

    public void setLostLifes(int lostLifes) {
        this.lostLifes = lostLifes;
    }

    public int getLostLifes() {
        return lostLifes;
    }

    public int getGodShots() {
        return godShots;
    }

    void setDucksShots(ArrayList <String> ducksShots2){
        this.ducksShots=ducksShots2;
    }
    void setShots(ArrayList <Shots> shotss){
        this.shots=shotss;
    }
}
