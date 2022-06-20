package Game;

public class MenuDucks {
    private int x;
    private int y;
    private int destinationX;
    private int destinationY;
    private int moveX = 5;
    private int moveY = 5;

    public MenuDucks(int x, int y, int destinationX, int destinationY) {
        this.x = x;
        this.y = y;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
    }

    public void moveDuck() {
        if (destinationX != x && destinationY != y) {
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
        } else {
            if (destinationX > x) {
                x += moveX;
            }
            if (destinationX < x) {
                x -= moveX;
            }
            if (destinationY > y) {
                y += moveY;
            }
            if (destinationY < y) {
                y -= moveY;
            }
        }
        if (destinationX == x && destinationY == y) {
            drawDestinationX();
            drawDestinationY();
        }

    }

    void drawDestinationX() {
        destinationX = (int) (Math.random() * 1100);
        if (destinationX % 5 != 0)
            drawDestinationX();
    }

    void drawDestinationY() {
        destinationY = (int) (Math.random() * 750);
        if (destinationY % 5 != 0)
            drawDestinationY();
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
