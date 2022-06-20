package Game;

public class Shots {
    private int x;
    private int y;

    public Shots(int x, int y, boolean boom, int direction) {
        this.x = x;
        this.y = y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setBoom(boolean boom) {
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
