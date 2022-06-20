package Audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class ShortAudio {
    private final Clip clip;
    private String path;

    public ShortAudio(String path) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
    }

    public void play() {
        clip.start();
        clip.setFramePosition(0);

    }

    public void pause() {
        clip.stop();
    }
}

