package Swing;

import Audio.ShortAudio;
import Config.AppConfiguration;
import Game.Bonuses;
import Game.Boss;
import Game.Death;
import Game.Ducks;
import Game.Highscore;
import Game.Shots;
import Game.SpaceShip;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MyJPanel extends JPanel implements ActionListener {
    private final SpaceShip spaceShip;
    private int speed = 10;
    private int bonusSpeed = 10;
    private Ducks ducks;
    public String[] duckCoords;
    private long start;
    private long start2;
    private int numberOfBonus;
    private final Death death;
    private final ArrayList<Bonuses> bonuses = new ArrayList<>();
    private ArrayList<Shots> shots;
    private ArrayList<String> ducksShots = new ArrayList<>();
    public String spaceShipCoords;
    private final Boss boss;
    private final ActionsKeyboard actionsKeyboard;
    private boolean end = false;
    private final Window window;
    private AppConfiguration appConfiguration = new AppConfiguration();

    public MyJPanel(Window window, SpaceShip spaceShip, Ducks ducks, Death death, ArrayList<Shots> shots, ActionsKeyboard actionsKeyboard, Boss boss) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.spaceShip = spaceShip;
        this.window = window;
        this.actionsKeyboard = actionsKeyboard;
        this.ducks = ducks;
        this.death = death;
        this.shots = shots;
        this.boss = boss;
        makeBoard(ducks, actionsKeyboard);
        start = System.currentTimeMillis();

    }

    private void makeBoard(Ducks ducks, ActionsKeyboard actionsKeyboard) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ducks.checkLevel();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread());
                ducks.makeDucks();
                addKeyListener(actionsKeyboard);
            }
        });
        thread.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (boss.getHealth() == 0) {
            boss.setActivated(false);
        }
        drawBackground(g);
        shots = spaceShip.getShots();
        try {
            ducksActivities();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        deathAcitivites();
        bonusesActivities();
        int number = ducks.shootingDucks();
        if (number != 1000 && ducks.getDucksCoordinates().size() > 0) {
            ducksShots.add(ducks.getDucksCoordinates().get(number));
        }
        makeBonuses(g);
        if (ducksShots.size() > 0 && !boss.getActivated())
            ducks.moveDucksShots(g, this);

        if (spaceShip.isAlive())
            drawImage(g, appConfiguration.getProperties().getProperty("spaceShip"), spaceShip.getX(), spaceShip.getY());

        for (int i = 0; i < shots.size(); i++) {
            shoot(g, i);

        }
        for (int i = 0; i < ducks.getDucksCoordinates().size(); i++) {
            moveDuck(g, i);

        }
        checkingSpaceShipLives(g);
        if (boss.getActivated()) {
            bossActivatedActivities(g);

        }


    }

    private void checkingSpaceShipLives(Graphics g) {
        if (spaceShip.getLives() > 0) {
            for (int i = 0; i < spaceShip.getLives(); i++) {
                if (i <= 6)
                    drawImage(g, appConfiguration.getProperties().getProperty("heartImg"), 950 - (i * 50), 700);

            }

        } else if (!end) {
            window.dispose();
            end = true;
            makeResultWindow();

        }
    }

    private void makeBonuses(Graphics g) {
        if (checkTime()) {
            try {
                bonuses.add(new Bonuses((int) (Math.random() * 4), (int) (Math.random() * 700), 0));
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }
        if (bonuses.size() > 0) {
            for (int i = 0; i < bonuses.size(); i++) {
                if (bonuses.get(i).getY() >= 700) {
                    bonuses.remove(i);
                }
                if (bonuses.size() > 0) {
                    drawImage(g, bonuses.get(i).drawBonus(), bonuses.get(i).getX(), bonuses.get(i).getY());
                    bonuses.get(i).setY(bonuses.get(i).getY() + 5);
                }
            }
        }
    }

    private void drawBackground(Graphics g) {
        drawImage(g, appConfiguration.getProperties().getProperty("cosmos"), 0, 0);

        Font font = new Font("Verdana", Font.BOLD, 20);
        setFont(font);
        g.setColor(Color.WHITE);
        g.drawString("Time: " + actionsKeyboard.getGameTime(), 800, 20);
        g.drawString("Perfect Shoots: " + death.getGodShots(), 800, 40);
        g.drawString("Total Shoots: " + actionsKeyboard.getNumberOfShots(), 800, 60);
        g.drawString("Lost lives: " + death.getLostLifes(), 800, 80);
    }

    private void moveDuck(Graphics g, int i) {
        String[] duckCoords = ducks.getDucksCoordinates().get(i).split(",");
        drawImage(g, appConfiguration.getProperties().getProperty("duck"), Integer.parseInt(duckCoords[0]), Integer.parseInt(duckCoords[1]));
    }

    private void shoot(Graphics g, int i) {
        drawImage(g, appConfiguration.getProperties().getProperty("laser"), shots.get(i).getX(), shots.get(i).getY() - 50);
        shots.get(i).setY(shots.get(i).getY() - bonusSpeed);
    }

    void bossActivatedActivities(Graphics g) {
        Font font;
        boss.playAudio();
        font = new Font("Verdana", Font.BOLD, 25);
        setFont(font);
        g.drawString("BOSS HEALTH: ", 80, 40);
        g.setColor(Color.red);
        g.fillRect(300, 20, boss.getHealth() * 3, 25);
        drawImage(g, appConfiguration.getProperties().getProperty("bossImage"), boss.getX(), boss.getY());
        boss.setMoveX(speed);
        boss.setMoveY(speed);
        boss.moveBoss();
        death.checkBossHealth();

        boss.shotingBoss();
        String[] shootCoords;
        ducksShots = boss.getShots();
        for (int i = 0; i < ducksShots.size(); i++) {
            shootCoords = ducksShots.get(i).split(",");
            drawImage(g, appConfiguration.getProperties().getProperty("laser"), (Integer.parseInt(shootCoords[0])), (Integer.parseInt(shootCoords[1])));

            boss.moveBossShot(shootCoords, i, this);

        }
    }

    private void bonusesActivities() {
        try {
            setBonuses();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        cancelBonuses();
        spaceShip.setBonuses(bonuses);
        numberOfBonus = spaceShip.eatBonuses();
    }

    private void ducksActivities() throws ExecutionException, InterruptedException {
        ducks.checkLevel();
        ducks.checkLeveling();
        ducks.duckSegregation();
        ducks.changeDirection();
        ducks.moveDucks();
    }

    private void deathAcitivites() {
        death.checkDuckShot();
        death.setShots(shots);
        death.checkDuckCoordinates();
        death.checkDucksHealth();
        death.checkBossCoordinates();
        death.setDucksShots(ducksShots);
    }

    public void drawImage(Graphics g, String path, int x, int y) {
        try {
            Image img = ImageIO.read(new File(path));
            g.drawImage(img, x, y, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void makeResultWindow() {
        JFrame window2 = new JFrame("Results");

        int points = spaceShip.countPoints(actionsKeyboard.getGameTime(), actionsKeyboard.getNumberOfShots() - death.getLostLifes(), death.getGodShots(), death.getLostLifes());
        ImageIcon icon = new ImageIcon(appConfiguration.getProperties().getProperty("cosmosJPG"));

        JLabel label = new JLabel();
        label.setIcon(icon);
        label.setBounds(0, 0, 200, 30);


        JLabel label2 = new JLabel("GAME OVER YOUR SCORE " + points);
        label2.setBounds(0, 0, 200, 30);


        JLabel labelLogin = createLoginLabel(icon);
        labelLogin.setForeground(Color.BLACK);


        JTextField loginTextField = new JTextField();
        loginTextField.setOpaque(true);
        loginTextField.setBounds(150, 50, 150, 20);
        JButton button1 = new JButton();
        MenuJPanel panel2 = createPanel2(labelLogin, loginTextField, button1);
        panel2.setLayout(null);

        panel2.add(label2);
        panel2.add(label);


        button1.setHorizontalTextPosition(JButton.CENTER);

        createButton(panel2, points, icon, loginTextField, button1, window2);

        window2.add(panel2);
        window2.setBounds(0, 0, 500, 500);
        window2.setVisible(true);
        window2.setBackground(Color.white);
        window2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private MenuJPanel createPanel2(JLabel labelLogin, JTextField loginTextField, JButton button1) {
        MenuJPanel panel = new MenuJPanel();
        panel.setLayout(null);
        panel.add(loginTextField);
        panel.add(labelLogin);
        panel.add(button1);
        return panel;
    }

    private void createButton(MenuJPanel panel2, int points, ImageIcon icon, JTextField loginTextField, JButton button1, JFrame jframe) {
        button1.setForeground(Color.white);
        button1.setIcon(icon);
        button1.setHorizontalTextPosition(JButton.CENTER);
        button1.setText("Add");
        button1.setBounds(170, 80, 100, 20);
        button1.addActionListener(new Highscore(panel2, loginTextField, points, jframe, button1));
    }

    private JLabel createLoginLabel(ImageIcon icon) {
        JLabel labelLogin = new JLabel("Login: ");
        labelLogin.setOpaque(true);
        labelLogin.setBounds(100, 50, 60, 20);
        labelLogin.setIcon(icon);
        labelLogin.setForeground(Color.white);
        return labelLogin;
    }

    void cancelBonuses() {
        float elapsedTimeSec;
        long elapsedTimeMillis;
        elapsedTimeMillis = System.currentTimeMillis() - start2;
        elapsedTimeSec = elapsedTimeMillis / 1000F;

        if (elapsedTimeSec >= 10) {
            speed = 10;
            bonusSpeed = 10;
            ducks.setSpeed(5);
            actionsKeyboard.setTimeWithoutShoot(1);
            actionsKeyboard.setMove(10);
        }
    }

    void setBonuses() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        ShortAudio audio = new ShortAudio(appConfiguration.getProperties().getProperty("wakaWaka"));
        if (numberOfBonus == 0) {
            audio.play();
            if (spaceShip.getLives() <= 7)
                spaceShip.setLives(spaceShip.getLives() + 1);
        }
        if (numberOfBonus == 1) {
            audio.play();
            start2 = System.currentTimeMillis();
            setSpeed(10);
            bonusSpeed = 5;
            ducks.setSpeed(10);
            actionsKeyboard.setMove(5);
            actionsKeyboard.setTimeWithoutShoot(2);
        }
        if (numberOfBonus == 2) {
            audio.play();
            start2 = System.currentTimeMillis();
            setSpeed(10);
            ducks.setSpeed(5);
            actionsKeyboard.setMove(15);
            bonusSpeed = 20;
            actionsKeyboard.setTimeWithoutShoot(0.7);
        }
        if (numberOfBonus == 3) {
            audio.play();
            spaceShip.setLives(spaceShip.getLives() - 1);
            death.setLostLifes(death.getLostLifes() + 1);
        }
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    boolean checkTime() {
        float elapsedTimeSec;
        long elapsedTimeMillis;
        elapsedTimeMillis = System.currentTimeMillis() - start;
        elapsedTimeSec = elapsedTimeMillis / 1000F;
        if (elapsedTimeSec > 15) {
            start = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public ArrayList<String> getDucksShots() {
        return ducksShots;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1000, 750);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
