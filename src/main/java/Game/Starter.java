package Game;

import Config.AppConfiguration;
import Swing.ButtonCreator;
import Swing.MenuJPanel;
import Audio.AudioPlayer;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Starter {
    private final AppConfiguration configuration = new AppConfiguration();
    private final ButtonCreator buttonConfig = new ButtonCreator(this);

    public Starter() {
    }

    public void makeBoardAndStartGame() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioPlayer player = new AudioPlayer(configuration.getProperties().getProperty("musicMenu"));
        JFrame window = new JFrame("Space");
        MenuJPanel panel = new MenuJPanel();
        panel.setLayout(null);
        window.add(panel);
        Font font = new Font("Verdana", Font.BOLD, 25);
        ImageIcon icon = new ImageIcon(configuration.getProperties().getProperty("cosmosTape"));
        buttonConfig.setButtonsWindowAndPanel(player, window, panel, font, icon);
    }
}

