 import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MyJPanel extends JPanel implements ActionListener {
    SpaceShip spaceShip;
    int speed=10;
    int speed2=10;
    Ducks ducks;
    BufferedImage image;
    String [] coords2;
    Bonuses bonus;
    long start;
    long start2;
    int numberOfBonus;
    String [] duckCoords;
    Death death;
    ArrayList <Bonuses> bonuses=new ArrayList<>();
    ArrayList <Shots>shots=new ArrayList<>();
    ArrayList <String> ducksShots=new ArrayList<>();
    String coords;
    int number;
    boolean bossStarted=false;
    Boss boss;
    ActionsKeyboard actionsKeyboard;
    boolean end=false;
    Window window;
    public MyJPanel(Window window,SpaceShip spaceShip,Ducks ducks, Death death,ArrayList <Shots>shots,ActionsKeyboard actionsKeyboard, Boss boss ) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.spaceShip=spaceShip;
        this.window=window;
        this.actionsKeyboard=actionsKeyboard;
        this.ducks=ducks;
        this.death=death;
        this.shots=shots;
        this.boss=boss;
        ducks.checkLevel();
        ducks.makeDucks();
        start=System.currentTimeMillis();
        addKeyListener(actionsKeyboard);


    }

    public void paintComponent(Graphics g)  {

        super.paintComponent(g);
      if(boss.health==0){
          boss.setActivated(false);
      }
        drawBackground(g);
        shots=spaceShip.getShots();
        ducksActivities();
        deathAcitivites();
        bonusesActivities();
        number=ducks.shootingDucks();
        if(number!=1000 && ducks.ducksCoordinates.size()>0){
            ducksShots.add(ducks.ducksCoordinates.get(number));
        }
        makeBonuses(g);
        if(ducksShots.size()>0 && !boss.activated)
            ducks.movingDucksShots(g, this);

        if(spaceShip.alive)
            DrawImage(g,"images&sounds\\statekKos22.png",spaceShip.x,spaceShip.y);

        for(int i=0;i<shots.size();i++){
            shotting(g, i);

        }
        for (int i = 0; i < ducks.ducksCoordinates.size(); i++) {
            moveDucks(g, i);

        }
        checkingSpaceShipLives(g);
        if(boss.activated){
            bossActivatedActivities(g);

        }



    }

    private void checkingSpaceShipLives(Graphics g) {
        if(spaceShip.lives>0){
            for(int i=0;i<spaceShip.lives;i++) {
                if(i<=6)
                    DrawImage(g,"images&sounds\\serce.png",950 - (i * 50),700);

            }

        }else if(!end){
            window.dispose();
            end=true;
            makingResultWindow();

        }
    }

    private void makeBonuses(Graphics g) {
        if(checkTime()){
            try {
                bonuses.add(new Bonuses ((int)(Math.random()*4),(int)(Math.random()*700),0));
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }
        if(bonuses.size()>0){
            for(int i=0;i<bonuses.size();i++){
                if(bonuses.get(i).getY()>=700){
                    bonuses.remove(i);
                }if(bonuses.size()>0) {
                    DrawImage(g,bonuses.get(i).getPath(),bonuses.get(i).getX(),bonuses.get(i).getY());
                    bonuses.get(i).setY(bonuses.get(i).getY() + 5);
                }
            }
        }
    }

    private void drawBackground(Graphics g) {
        DrawImage(g,"images&sounds\\kosmos4.jpg",0,0);

        Font font = new Font("Verdana", Font.BOLD, 20);
        setFont(font);
        g.setColor(Color.WHITE);
        g.drawString("Time: "+ actionsKeyboard.getGameTime(),800,20);
        g.drawString("Good Shoots: "+ death.getGodShots(),800,40);
        g.drawString("Shoots: "+actionsKeyboard.getNumberOfShots(),800,60);
        g.drawString("Lost lives: "+death.getLostLifes(),800,80);
    }

    private void moveDucks(Graphics g, int i) {
        duckCoords = ducks.ducksCoordinates.get(i).split(",");
        DrawImage(g,"images&sounds\\kaczkaW2.png",Integer.parseInt(duckCoords[0]),Integer.parseInt(duckCoords[1]));
    }

    private void shotting(Graphics g, int i) {
        DrawImage(g,"images&sounds\\laser.png",shots.get(i).getX(),shots.get(i).getY()-50);
        shots.get(i).setY(shots.get(i).getY()-speed2);
    }

    void bossActivatedActivities(Graphics g) {
        Font font;
//
        boss.playAudio();
        font = new Font("Verdana", Font.BOLD, 25);
        setFont(font);
        g.drawString("BOSS HEALTH: ", 80,40);
        g.setColor(Color.red);
        g.fillRect(300,20,boss.health*3,25);
        DrawImage(g,"images&sounds\\KaczkaBoss.png",boss.x,boss.y);
        boss.setMoveX(speed);
        boss.setMoveY(speed);
        boss.moveBoss();
        death.dieingBoss();

        boss.shottingBoss();
        String [] coordss;
        ducksShots=boss.getShots();
        for(int i=0;i<ducksShots.size();i++){
            coordss=ducksShots.get(i).split(",");
            DrawImage(g,"images&sounds\\laser.png",(Integer.parseInt(coordss[0])),(Integer.parseInt(coordss[1])));

            boss.moveBossShot(coordss, i, this);


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
        numberOfBonus=spaceShip.eatBonuses();
    }

    private void ducksActivities() {
        ducks.checkLevel();
        ducks.checkLeveling();
        ducks.duckSegregation();
        ducks.changeDirection();
        ducks.moveDucks();
    }

    private void deathAcitivites() {
        death.dieingSpaceShipV2();
        death.setShots(shots);
        death.dieingSpaceShip();
        death.dieingDucks();
        death.dieingSpaceShipV3();
        death.setDucksShots(ducksShots);
    }

    void DrawImage(Graphics g, String path, int x, int y) {
        try {
            Image img = ImageIO.read(new File(path));
            g.drawImage(img, x, y, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void makingResultWindow() {
        JFrame window2=new JFrame("Results");

        int points=spaceShip.countPoints(actionsKeyboard.getGameTime(),actionsKeyboard.getNumberOfShots()-death.getLostLifes(),death.getGodShots(),death.getLostLifes());
        ImageIcon icon=new ImageIcon("images&sounds\\kosmos.jpg");

        JLabel label=new JLabel();
        label.setIcon(icon);
        label.setBounds(0,0,200,30);


        JLabel label2=new JLabel("GAME OVER YOUR SCORE " + points );
        label2.setBounds(0,0,200,30);





        JLabel labelLogin = createLoginLabel(icon);
        labelLogin.setForeground(Color.BLACK);




        JTextField loginTextField=new JTextField();
        loginTextField.setOpaque(true);
        loginTextField.setBounds(150,50,150,20);
        JButton button1=new JButton();
        JPanel2 panel2 = createPanel2(labelLogin, loginTextField, button1);
        panel2.setLayout(null);

        panel2.add(label2);
        panel2.add(label);


        button1.setHorizontalTextPosition(JButton.CENTER);

        createButton(panel2, points, icon, loginTextField, button1);

        window2.add(panel2);
        window2.setBounds(0,0,500,500);
        window2.setVisible(true);
        window2.setBackground(Color.white);
        window2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private JPanel2 createPanel2(JLabel labelLogin, JTextField loginTextField, JButton button1) {
        JPanel2 panel2=new JPanel2();

        panel2.setLayout(null);
        panel2.add(loginTextField);
        panel2.add(labelLogin);
        panel2.add(button1);
        return panel2;
    }

    private void createButton(JPanel2 panel2, int points, ImageIcon icon, JTextField loginTextField, JButton button1) {
        button1.setForeground(Color.white);
        button1.setIcon(icon);
        button1.setHorizontalTextPosition(JButton.CENTER);
        button1.setText("Add");
        button1.setBounds(170,80,100,20);
        button1.addActionListener(new Highscore(panel2,loginTextField,points));
    }

    private JLabel createLoginLabel(ImageIcon icon) {
        JLabel labelLogin= new JLabel("Login: ");
        labelLogin.setOpaque(true);
        labelLogin.setBounds(100,50,60,20);
        labelLogin.setIcon(icon);
        labelLogin.setForeground(Color.white);
        return labelLogin;
    }

    private void createLabel1(int points, ImageIcon icon) {
        JLabel label=new JLabel("GAME OVER YOUR SCORE " + points);
        label.setForeground(Color.white);
        label.setBackground(Color.WHITE);
        label.setOpaque(true);
        label.setBounds(0,0,1600,300);

    }

    void cancelBonuses(){
        float elapsedTimeSec;
        long elapsedTimeMillis;
        elapsedTimeMillis = System.currentTimeMillis()- start2;
        elapsedTimeSec = elapsedTimeMillis / 1000F;

        if(elapsedTimeSec>=10){
            speed=10;
            speed2=10;
            ducks.setSpeed(5);
            actionsKeyboard.setTimeWithoutShoot(1);
            actionsKeyboard.setSpeed(10);
        }
    }

    void setBonuses() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        ShortAudio audio=new ShortAudio("images&sounds\\dp_superpac_wakka.au");
        if(numberOfBonus==0){
            audio.play();
            if(spaceShip.lives<=7)
            ++spaceShip.lives;
        }if(numberOfBonus==1){
            audio.play();
            start2=System.currentTimeMillis();
            setSpeed(10);
            speed2=5;
            ducks.setSpeed(10);
            actionsKeyboard.setSpeed(5);
            actionsKeyboard.setTimeWithoutShoot(2);
        }
        if(numberOfBonus==2){
            audio.play();
            start2=System.currentTimeMillis();
            setSpeed(10);
            ducks.setSpeed(5);
            actionsKeyboard.setSpeed(15);
            speed2=20;
            actionsKeyboard.setTimeWithoutShoot(0.7);
        }if(numberOfBonus==3){
            audio.play();
            spaceShip.lives=spaceShip.lives-1;
            death.setLostLifes(death.getLostLifes()+1);
        }
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    boolean checkTime(){
       float elapsedTimeSec;
       long elapsedTimeMillis;
        elapsedTimeMillis = System.currentTimeMillis()- start;
        elapsedTimeSec = elapsedTimeMillis / 1000F;
        if(elapsedTimeSec>15){
            start=System.currentTimeMillis();
            return true;
        }
        return false;
    }
    void drawAImage(Graphics g, String s, int i2, int i3, int i4) {
       image(s);
        Image newImage = image.getScaledInstance(i2, i2, Image.SCALE_DEFAULT);
        g.drawImage(newImage, i3, i4, this);
    }



   void image(String path) {
        BufferedImage img = null;

        File file = new File(path);
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    public ArrayList<Shots> getShots() {
        return shots;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1000,750);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
