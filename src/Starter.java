import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Starter {
    boolean bool;
    ActionList actionList;
    public Starter(){

    }
    void start() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioPlayer player=new AudioPlayer("images&sounds\\01_Title-Screen.au");

        JFrame window=new JFrame("Space");

        JPanel2 panel=new JPanel2();
        panel.setLayout(null);

        window.add(panel);
        Font font= new Font("Verdana", Font.BOLD, 25);

        ImageIcon icon=new ImageIcon("images&sounds\\kosmos2.jpg");

        JButton difficultyButton=getDifficultyButton(window,font,icon);
        JButton playButton = getPlayButton(player, window, font, icon,difficultyButton);


        JButton exitButton = getExitButton(window, font, icon);

        JButton higscoreButton = getHigscoreButton(panel,font,icon);


        settingWindowAndPanel(window, panel, difficultyButton, playButton, exitButton, higscoreButton);


    }

    private void settingWindowAndPanel(JFrame window, JPanel2 panel, JButton difficultyButton, JButton playButton, JButton exitButton, JButton higscoreButton) {
        panel.add(difficultyButton);
        panel.add(playButton);
        panel.add(exitButton);
        panel.add(higscoreButton);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1000,1000);
        window.setVisible(true);
    }

    ActionList makeActionList(JFrame window, AudioPlayer player, boolean bool){
        ActionList actionList=new ActionList((JFrame) window, player, bool);
        return actionList;
    }
    private  JButton getDifficultyButton(JFrame window, Font font, ImageIcon icon) {
        JButton difficultyButton=new JButton();
        difficultyButton.setIcon(icon);
        difficultyButton.setForeground(Color.white);
        difficultyButton.setBounds(420,400,200,50);
        difficultyButton.setText("Lvl: Easy");
        difficultyButton.setHorizontalTextPosition(JButton.CENTER);
        difficultyButton.setFont(font);
        difficultyButton.addActionListener((new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                if(e.getSource()==difficultyButton)
                {
                    if(difficultyButton.getText().equals("Lvl: Easy")) {
                        difficultyButton.setText("Lvl: Hard");
                    }
                    else {
                        difficultyButton.setText("Lvl: Easy");

                    }
                }
            }
        }));
        return difficultyButton;
    }
    private JButton getHigscoreButton(JPanel2 panel, Font font, ImageIcon icon) {
        JButton higscoreButton=new JButton();
        higscoreButton.setIcon(icon);
        higscoreButton.setForeground(Color.white);
        higscoreButton.setBounds(420,450,200,50);
        higscoreButton.setText("Highscore");
        higscoreButton.setHorizontalTextPosition(JButton.CENTER);
        higscoreButton.setFont(font);
        higscoreButton.addActionListener(new Highscore(panel,new JTextField(" "),-5));
        return higscoreButton;
    }

    private  JButton getExitButton(JFrame window, Font font, ImageIcon icon) {
        JButton exitButton=new JButton();
        exitButton.setIcon(icon);
        exitButton.setForeground(Color.white);
        exitButton.setBounds(420,500,200,50);
        exitButton.setText("Exit");
        exitButton.setHorizontalTextPosition(JButton.CENTER);
        exitButton.setFont(font);
        exitButton.addActionListener((new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                window.dispose();
            }
        }));
        return exitButton;
    }

    private  JButton getPlayButton(AudioPlayer player, JFrame window, Font font, ImageIcon icon, JButton button) {
        JButton playButton=new JButton();
        playButton.setIcon(icon);
        playButton.setForeground(Color.white);
        playButton.setBounds(420,350,200,50);
        playButton.setText("Play");
        playButton.setHorizontalTextPosition(JButton.CENTER);
        playButton.setFont(font);

        PasserbyClass classs=new PasserbyClass(window,player,bool,this,button);
        if(button.getText().equals("Lvl: Easy")) {

            classs.setBool(false);
            playButton.addActionListener(classs);

        }
        else {

            classs.setBool(true);
            playButton.addActionListener(classs);
        }
        playButton.setVisible(true);
        return playButton;
    }
    }

