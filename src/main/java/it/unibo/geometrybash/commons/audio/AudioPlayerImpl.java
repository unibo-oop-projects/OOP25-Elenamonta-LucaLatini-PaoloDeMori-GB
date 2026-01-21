package it.unibo.geometrybash.commons.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.InputStream;


public class AudioPlayerImpl implements AudioPlayer {

    private Clip clip;

    public AudioPlayerImpl(final String audioFilePath) {
        try {
            final InputStream audioSrc = getClass().getResourceAsStream(audioFilePath);
            final AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioSrc);

            this.clip = AudioSystem.getClip();
            this.clip.open(audioStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void playLoop() {
        if (this.clip != null) {
            this.clip.loop(Clip.LOOP_CONTINUOUSLY);
            this.clip.start();
        }
    }

    @Override
    public void stop() {
        if (this.clip != null && this.clip.isRunning()) {
            this.clip.stop();
            this.clip.close();
        }
    }


}
