package Core.GameSystem;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioManager {
    private static Clip clip;

    public static void play(String filePath) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(filePath));
            clip = AudioSystem.getClip();

            clip.open(audioIn);
            clip.setMicrosecondPosition(1000);

            clip.start();

//            clip.setLoopPoints(0, clip.getFrameLength() - 1);
//            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void stop() {
        clip.stop();
    }
}
