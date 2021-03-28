import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;

public class Boss {
    int x=0;
    int y=0;
    int health;
    int moveX=5;
    int moveY=5;
    int destinationX;
    int destinationY;
    long start;
    float elapsedTimeSec;
    long elapsedTimeMillis;
    boolean activated;
    AudioPlayer audio=new AudioPlayer("D:\\SpaceShipInvader\\images&sounds\\13_Boss.au");

    ArrayList<String> shots=new ArrayList<>();
    public Boss(int x, int y, int health, boolean activated) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.x=x;
        this.y=y;
        this.health=health;
        this.activated=activated;
        start=System.currentTimeMillis();

        drawDestinationX();
       drawDestinationY();
    }
    void playAudio(){
        audio.play();
    }
    void stopAudio(){
        audio.pause();
    }


    void shottingBoss(){
        elapsedTimeMillis = System.currentTimeMillis()- start;
        elapsedTimeSec = elapsedTimeMillis / 1000F;
        checkingShots();
        if(elapsedTimeSec>=3.5) {
            shots.add(((x - 100)+","+ y + ","+"0"));
            shots.add(((x - 100)+","+ (y - 100)+ ","+"1"));
            shots.add(((x + 100)+ ","+y+  ","+"2"));
            shots.add(((x + 100)+","+( y + 100)+ ","+"3"));
            shots.add((x+ ","+(y - 100)+ ","+ "4"));
            shots.add(((x + 100)+","+ (y - 100)+  ","+"5"));
            shots.add(((x - 100) +","+ (y + 100)+ ","+"6"));
            shots.add((x+","+(y + 100)+  ","+ "7"));
            start=System.currentTimeMillis();
        }
    }
    void checkingShots(){
        String [] coords;
        for(int i=0;i<shots.size();i++){
            coords=shots.get(i).split(",");
            if(Integer.parseInt(coords[0])<=0 || Integer.parseInt(coords[1])<=0 ||Integer.parseInt(coords[0])>1000 || Integer.parseInt(coords[1])>=750){
                shots.remove(i);
            }
        }
    }

    void moveBoss(){
        if(destinationX!=x && destinationY!=y) {
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
        }else{
            if(destinationX>x){
                x += moveX;
            }
            if(destinationX<x){
                x -= moveX;
            }
            if(destinationY>y){
                y += moveY;
            }
            if(destinationY<y){
                y -= moveY;
            }
        }
        if(destinationX==x && destinationY==y){
            drawDestinationX();
            drawDestinationY();
        }

    }
    ArrayList getShots(){
        return shots;
    }
    void drawDestinationX(){
        destinationX=(int)(Math.random()*900);
        if(destinationX%10!=0)
            drawDestinationX();
    }
    void drawDestinationY(){
        destinationY=(int)(Math.random()*650);
        if(destinationY%10!=0)
            drawDestinationY();
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


    public boolean getActivated(){
        return activated;
    }

    void moveBossShot(String[] coordss, int i, MyJPanel myJPanel) {
        if (coordss[2].equals("0")) {
            myJPanel.ducksShots.set(i, (Integer.parseInt(coordss[0]) - myJPanel.speed) + "," + coordss[1] + "," + coordss[2]);
        }
        if (coordss[2].equals("1")) {
            myJPanel.ducksShots.set(i, (Integer.parseInt(coordss[0]) - myJPanel.speed) + "," + (Integer.parseInt(coordss[1]) - myJPanel.speed) + "," + coordss[2]);

        }
        if (coordss[2].equals("2")) {
            myJPanel.ducksShots.set(i, (Integer.parseInt(coordss[0]) + myJPanel.speed) + "," + coordss[1] + "," + coordss[2]);
        }
        if (coordss[2].equals("3")) {
            myJPanel.ducksShots.set(i, (Integer.parseInt(coordss[0]) + myJPanel.speed) + "," + (Integer.parseInt(coordss[1]) + myJPanel.speed) + "," + coordss[2]);

        }
        if (coordss[2].equals("4")) {
            myJPanel.ducksShots.set(i, (coordss[0]) + "," + (Integer.parseInt(coordss[1]) - myJPanel.speed) + "," + coordss[2]);

        }
        if (coordss[2].equals("5")) {
            myJPanel.ducksShots.set(i, (Integer.parseInt(coordss[0]) + myJPanel.speed) + "," + (Integer.parseInt(coordss[1]) - myJPanel.speed) + "," + coordss[2]);
        }
        if (coordss[2].equals("6")) {
            myJPanel.ducksShots.set(i, (Integer.parseInt(coordss[0]) - myJPanel.speed) + "," + (Integer.parseInt(coordss[1]) + myJPanel.speed) + "," + coordss[2]);


        }
        if (coordss[2].equals("7")) {
            myJPanel.ducksShots.set(i, (coordss[0]) + "," + (Integer.parseInt(coordss[1]) + myJPanel.speed) + "," + coordss[2]);

        }
    }
}
