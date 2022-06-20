package Swing;

import Game.PasserbyClass;
import Game.Starter;
import Audio.AudioPlayer;
import Game.Highscore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonCreator {
    private boolean isEasy;
    private Starter starter;

    public ButtonCreator(Starter starter) {
        this.starter = starter;
    }


    public void setButtonsWindowAndPanel(AudioPlayer player, JFrame window, MenuJPanel panel, Font font, ImageIcon icon) {
        JButton difficultyButton = setDifficultyButton(font, icon);
        JButton playButton = setPlayButton(player, window, font, icon, difficultyButton);
        JButton exitButton = setExitButton(window, font, icon);
        JButton highScoreButton = setHighscoreButton(panel, font, icon);

        settingWindowAndPanel(window, panel, difficultyButton, playButton, exitButton, highScoreButton);
    }

    private void settingWindowAndPanel(JFrame window, MenuJPanel panel, JButton difficultyButton, JButton playButton, JButton exitButton, JButton higscoreButton) {
        panel.add(difficultyButton);
        panel.add(playButton);
        panel.add(exitButton);
        panel.add(higscoreButton);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1000, 1000);
        window.setVisible(true);
    }

    private JButton setDifficultyButton(Font font, ImageIcon icon) {
        JButton difficultyButton = new JButton();
        difficultyButton.setIcon(icon);
        difficultyButton.setForeground(Color.white);
        difficultyButton.setBounds(420, 400, 200, 50);
        difficultyButton.setText("Lvl: Easy");
        difficultyButton.setHorizontalTextPosition(JButton.CENTER);
        difficultyButton.setFont(font);
        difficultyButton.addActionListener((e -> {
            if (e.getSource() == difficultyButton) {
                if (difficultyButton.getText().equals("Lvl: Easy")) {
                    difficultyButton.setText("Lvl: Hard");
                } else {
                    difficultyButton.setText("Lvl: Easy");

                }
            }
        }));
        return difficultyButton;
    }

    private JButton setHighscoreButton(MenuJPanel panel, Font font, ImageIcon icon) {
        JButton higscoreButton = new JButton();
        higscoreButton.setIcon(icon);
        higscoreButton.setForeground(Color.white);
        higscoreButton.setBounds(420, 450, 200, 50);
        higscoreButton.setText("Highscore");
        higscoreButton.setHorizontalTextPosition(JButton.CENTER);
        higscoreButton.setFont(font);
        higscoreButton.addActionListener(new Highscore(panel, new JTextField(" "), -5, null,null));
        return higscoreButton;
    }

    private JButton setExitButton(JFrame window, Font font, ImageIcon icon) {
        JButton exitButton = new JButton();
        exitButton.setIcon(icon);
        exitButton.setForeground(Color.white);
        exitButton.setBounds(420, 500, 200, 50);
        exitButton.setText("Exit");
        exitButton.setHorizontalTextPosition(JButton.CENTER);
        exitButton.setFont(font);
        exitButton.addActionListener((new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.dispose();
            }
        }));
        return exitButton;
    }

    private JButton setPlayButton(AudioPlayer player, JFrame window, Font font, ImageIcon icon, JButton button) {
        JButton playButton = new JButton();
        playButton.setIcon(icon);
        playButton.setForeground(Color.white);
        playButton.setBounds(420, 350, 200, 50);
        playButton.setText("Play");
        playButton.setHorizontalTextPosition(JButton.CENTER);
        playButton.setFont(font);

        PasserbyClass passerbyclass = new PasserbyClass(window, player, isEasy, starter, button);
        if (button.getText().equals("Lvl: Easy")) {
            passerbyclass.setEasy(false);
            playButton.addActionListener(passerbyclass);
        } else {
            passerbyclass.setEasy(true);
            playButton.addActionListener(passerbyclass);
        }
        playButton.setVisible(true);
        return playButton;
    }
}
