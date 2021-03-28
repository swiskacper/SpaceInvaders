import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class ActionsKeyboard implements KeyListener {
    SpaceShip spaceShip;
    long start;
    float elapsedTimeSec;
    long elapsedTimeMillis;
    int speed=10;
    long gameTime;
    int numberOfShots;
    double timeWithoutShoot=1;
    ShortAudio audio=new ShortAudio("D:\\SpaceShipInvader\\images&sounds\\AnyConv.com__shoot.au");



    public ActionsKeyboard(SpaceShip spaceShip,int speed) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.spaceShip=spaceShip;
        this.speed=speed;

        gameTime=System.currentTimeMillis();
        start=System.currentTimeMillis();
    }

    int getGameTime() {
        elapsedTimeMillis = System.currentTimeMillis()- gameTime;
        elapsedTimeSec = elapsedTimeMillis / 1000F;
        return (int) elapsedTimeSec;
    }

    public int getNumberOfShots() {
        return numberOfShots;
    }

    public void setTimeWithoutShoot(double timeWithoutShoot) {
        this.timeWithoutShoot = timeWithoutShoot;
    }

    public double getTimeWithoutShoot() {
        return timeWithoutShoot;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        int key=keyEvent.getKeyCode();
        if(key==KeyEvent.VK_LEFT ){
            spaceShip.x=spaceShip.x-speed;

        }if(key==KeyEvent.VK_RIGHT ) {
            spaceShip.x=spaceShip.x+speed;

        }

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

        int key=keyEvent.getKeyCode();
        elapsedTimeMillis = System.currentTimeMillis()- start;
        elapsedTimeSec = elapsedTimeMillis / 1000F;
        if(key==KeyEvent.VK_LEFT && spaceShip.x-speed>0){
            spaceShip.x=spaceShip.x-speed;

        }if(key==KeyEvent.VK_RIGHT && spaceShip.x+speed<950) {
            spaceShip.x=spaceShip.x+speed;

        }
        if(key==KeyEvent.VK_DOWN && spaceShip.y+speed<710){
            spaceShip.y=spaceShip.y+speed;
        } if(key==KeyEvent.VK_UP && spaceShip.y-speed>150){
            spaceShip.y=spaceShip.y-speed;
        }

        if(key==KeyEvent.VK_SPACE && elapsedTimeSec>=timeWithoutShoot){
            start=System.currentTimeMillis();
            ++numberOfShots;
            audio.play();
            spaceShip.shooting();
        }

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
