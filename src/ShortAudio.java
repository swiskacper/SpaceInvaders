import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class ShortAudio {
    Clip clip;
    AudioInputStream audioInputStream;
    String path;
    public ShortAudio(String path) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        audioInputStream =
                AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
    }
    public void play()
    {
        clip.start();
        clip.setFramePosition(0);

    }
    public void pause(){
        clip.stop();
    }
}

