package Game;

import Swing.ActionList;
import Audio.AudioPlayer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PasserbyClass implements ActionListener {
    private final JFrame window2;
    private final AudioPlayer player;
    private boolean isEasy;
    private final JButton button;

    public PasserbyClass(JFrame window2, AudioPlayer player, boolean isEasy, Starter starter, JButton button) {
        this.isEasy = isEasy;
        this.player = player;
        this.window2 = window2;
        this.button = button;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        isEasy = button.getText().equals("Lvl: Easy");
        ActionList actionList = new ActionList(window2, player, isEasy);
        actionList.actionPerformed(actionEvent);

    }

    public void setEasy(boolean easy) {
        this.isEasy = easy;
    }
}
