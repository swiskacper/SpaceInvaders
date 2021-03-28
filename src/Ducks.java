import java.awt.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class Ducks {
    ArrayList<String> ducksCoordinates = new ArrayList<>();
    ArrayList<Integer> boolDucks = new ArrayList<>();
    int X = 0;
    int Y = 0;
    int numberofDucks;
    int moveDirection;
    int changePosition;
    boolean positionDirection;
    long start;
    float elapsedTimeSec;
    long elapsedTimeMillis;
    String[] coord;
    int down=20;
    int count;
    int number1;
    int speed=5;
    int level=1;
    boolean leveling=false;
    boolean makingDucks=false;
    int difficultyLevel=1;
    Boss boss;
    public Ducks(ArrayList<String> ducksCoordinates, ArrayList<Integer> boolDucks, Boss boss, int difficultyLevel) {
        this.difficultyLevel=difficultyLevel;
        this.ducksCoordinates=ducksCoordinates;
        this.boolDucks=boolDucks;
        this.boss=boss;
        start=System.currentTimeMillis();
        moveDirection=0;
    }
    void setDifficultyLevel(int difficultyLevel2){
        this.difficultyLevel=difficultyLevel2;

    }
    void checkLevel(){
        settingLevelofDifficulty(1, 30, 60);
        settingLevelofDifficulty(2, 45, 90);
    }

    private void settingLevelofDifficulty(int i, int i2, int i3) {
        if (difficultyLevel == i) {
            if (level == 1) {
                numberofDucks = i2;

            }
            if (level == 2 && !makingDucks) {
                makingDucks = true;
                setX(-150);
                setY(0);
                numberofDucks = i3;
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

    void checkLeveling(){
        if(ducksCoordinates.size()==0 && !leveling && level<=3){
            level=level+1;
            leveling=true;
        }
    }
    void duckSegregation(){
        Set<String> set=new TreeSet<>();
        set.addAll(ducksCoordinates);
        ducksCoordinates.removeAll(ducksCoordinates);
        ducksCoordinates.addAll(set);

    }



    void makeDucks() {
        int a = X;
        int b = Y;
        String coords;
        int c = 0;
        for (int i = 0; i < numberofDucks; i++) {
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


    void changeDirection(){
        if(ducksCoordinates.size()>0) {
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

    void moveDucks() {
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
    int  shootingDucks(){
        number1=1000;
        elapsedTimeMillis = System.currentTimeMillis()- start;
        elapsedTimeSec = elapsedTimeMillis / 1000F;
        if(ducksCoordinates.size()>=70  && elapsedTimeSec>=0.8){
            setShoot();
        }
        if(ducksCoordinates.size()<70 && ducksCoordinates.size()>=50  && elapsedTimeSec>=0.95){
            setShoot();
        }
        if(ducksCoordinates.size()>=40 && ducksCoordinates.size()<50 && elapsedTimeSec>=1){
            setShoot();
        }
        if(ducksCoordinates.size()>=30 && ducksCoordinates.size()<40 && elapsedTimeSec>=2){
            setShoot();
        }
        if(ducksCoordinates.size()<30 && elapsedTimeSec>=2) {
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

    void setNumberofDucks(int a) {
        this.numberofDucks = a;
    }
    void setStart(){
        start=System.currentTimeMillis();
    }
    int  drawDuck(){
        return (int)(Math.random()*ducksCoordinates.size());
    }

    void movingDucksShots(Graphics g, MyJPanel myJPanel) {
          for (int i = 0; i < myJPanel.ducksShots.size(); i++) {
              myJPanel.coords2 = myJPanel.ducksShots.get(i).split(",");
              myJPanel.DrawImage(g,"images&sounds\\laser.png",Integer.parseInt(myJPanel.coords2[0]),Integer.parseInt(myJPanel.coords2[1]));

              int a = Integer.parseInt(myJPanel.coords2[1]) + myJPanel.speed;
              myJPanel.coords2[1] = String.valueOf(a);
              myJPanel.coords = myJPanel.coords2[0] + "," + myJPanel.coords2[1];
              myJPanel.ducksShots.set(i, myJPanel.coords);
          }
      }
}
