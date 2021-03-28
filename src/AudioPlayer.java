import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {
    Clip clip;
    AudioInputStream audioInputStream;
    String path;
    public AudioPlayer(String path) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        audioInputStream =
                AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void play()
    {
        clip.start();

    }
    public void pause(){
        clip.stop();
    }
}

