import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class JPanel2 extends JPanel {

    ArrayList <SumptousDuck> ducks=new ArrayList<>();
    public JPanel2(){
    makeDucks();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image img = null;

        try {
            img = ImageIO.read(new File("images&sounds\\kosmos.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(img, 0, 0, null);
        for(int i=0;i<ducks.size();i++){


            try {

                img = ImageIO.read(new File("images&sounds\\kaczkaW2.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            g.drawImage(img, ducks.get(i).x, ducks.get(i).y, null);
            ducks.get(i).moveduck();
        }



    repaint();
    }
    void makeDucks(){
        ducks.add(new SumptousDuck(0,0,150,620));
        ducks.add(new SumptousDuck(200,200,250,110));
        ducks.add(new SumptousDuck(625,700,170,220));
        ducks.add(new SumptousDuck(900,10,655,825));
        ducks.add(new SumptousDuck(255,500,35,350));
        ducks.add(new SumptousDuck( 5,25,335,380));
        ducks.add(new SumptousDuck(250,250,250,110));
        ducks.add(new SumptousDuck(865,745,990,660));
        ducks.add(new SumptousDuck(10,900,655,550));
        ducks.add(new SumptousDuck(500,255,300,350));
    }



}
