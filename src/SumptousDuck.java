public class SumptousDuck {
    int x;
    int y;
    int destinationX;
    int destinationY;
    int moveX=5;
    int moveY=5;
    public SumptousDuck(int x, int y, int destinationX, int destinationY){
        this.x=x;
        this.y=y;
        this.destinationX=destinationX;
        this.destinationY=destinationY;
    }
    void moveduck(){
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
    void drawDestinationX(){
        destinationX=(int)(Math.random()*1100);
        if(destinationX%5!=0)
            drawDestinationX();
    }
    void drawDestinationY(){
        destinationY=(int)(Math.random()*750);
        if(destinationY%5!=0)
            drawDestinationY();
    }
}
