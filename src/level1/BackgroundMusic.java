package level1;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class BackgroundMusic implements Runnable {
    private String filePath;
    private Clip clip;
    private boolean playing = true;

    public BackgroundMusic(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void run() {
        playMusic();
    }

    private void playMusic() {
        try {
            while (playing) {
                File audioFile = new File(filePath);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                clip = AudioSystem.getClip();
                clip.open(audioStream);

                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);

                while (!Thread.currentThread().isInterrupted() && clip.isRunning()) {
                    Thread.sleep(1000);
                }
                clip.close();
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }

    public void restartMusic() {
        stopMusic(); 
        run(); 
    }

    public void endMusic() {
        playing = false;
        stopMusic();
    }
}
