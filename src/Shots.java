import java.util.ArrayList;

public class Shots {
    int x;
    int y;
    boolean boom;
    int direction;
    public Shots(int x, int y, boolean boom, int direction){
        this.x=x;
        this.y=y;
        this.boom=boom;
        this.direction=direction;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setBoom(boolean boom) {
        this.boom = boom;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
