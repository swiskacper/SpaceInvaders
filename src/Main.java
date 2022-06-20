import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Main {


  public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    Starter start=new Starter();
    start.start();
  }
}