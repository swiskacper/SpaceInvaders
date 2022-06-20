package Swing;

import Config.AppConfiguration;
import Game.MenuDucks;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MenuJPanel extends JPanel {

    private ArrayList<MenuDucks> ducks = new ArrayList<>();
    private AppConfiguration appConfiguration = new AppConfiguration();

    public MenuJPanel() {
        makeDucks();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image img = null;

        try {
            img = ImageIO.read(new File(appConfiguration.getProperties().getProperty("cosmosJPG")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(img, 0, 0, null);
        for (int i = 0; i < ducks.size(); i++) {

            try {

                img = ImageIO.read(new File(appConfiguration.getProperties().getProperty("duck")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            g.drawImage(img, ducks.get(i).getX(), ducks.get(i).getY(), null);
            ducks.get(i).moveDuck();
        }


        repaint();
    }

    void makeDucks() {
        ducks.add(new MenuDucks(0, 0, 150, 620));
        ducks.add(new MenuDucks(200, 200, 250, 110));
        ducks.add(new MenuDucks(625, 700, 170, 220));
        ducks.add(new MenuDucks(900, 10, 655, 825));
        ducks.add(new MenuDucks(255, 500, 35, 350));
        ducks.add(new MenuDucks(5, 25, 335, 380));
        ducks.add(new MenuDucks(250, 250, 250, 110));
        ducks.add(new MenuDucks(865, 745, 990, 660));
        ducks.add(new MenuDucks(10, 900, 655, 550));
        ducks.add(new MenuDucks(500, 255, 300, 350));
    }


}
