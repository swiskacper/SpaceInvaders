import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PasserbyClass implements ActionListener {
    JFrame window2;
    AudioPlayer player;
    boolean bool;
    Starter starter;
    JButton button;
    public PasserbyClass(JFrame window2, AudioPlayer player, boolean bool, Starter starter, JButton button){
        this.bool=bool;
        this.player=player;
        this.starter=starter;
        this.window2=window2;
        this.button=button;
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if(button.getText().equals("Lvl: Easy")){
            bool=true;
        }else{
            bool=false;
        }

        ActionList actionList=new ActionList(window2, player, bool);
        actionList.actionPerformed(actionEvent);

    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }
}
