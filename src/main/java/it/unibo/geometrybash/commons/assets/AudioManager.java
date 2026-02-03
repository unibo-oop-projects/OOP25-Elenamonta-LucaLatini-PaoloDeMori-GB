package it.unibo.geometrybash.commons.assets;

import javax.sound.sampled.Clip;

/**
 * Manages sound effects and music playback for the application.
 * Audio clips are retrieved through an {@link AudioStore}.
 */
public final class AudioManager {

    private final AudioStore audio;

    /**
     * Creates a new {@code AudioManager} using the given audio store.
     *
     * @param audio the {@link AudioStore} used to retrieve audio clips
     */
    public AudioManager(final AudioStore audio) {
        this.audio = audio;
    }

    /**
     * Plays the specified sound once.
     *
     * @param sound the identifier of the sound to play
     */
    public void playOnce(final String sound) {
        final Clip clip = audio.getClip(sound);
        clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }

    /**
     * Starts continuous playback of the specified music track.
     *
     * @param music the identifier of the music track to loop
     */
    public void loop(final String music) {
        final Clip clip = audio.getClip(music);
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Stops playback of the specified sound.
     *
     * @param sound the identifier of the sound to stop
     */
    public void stop(final String sound) {
        audio.getClip(sound).stop();
    }
}
