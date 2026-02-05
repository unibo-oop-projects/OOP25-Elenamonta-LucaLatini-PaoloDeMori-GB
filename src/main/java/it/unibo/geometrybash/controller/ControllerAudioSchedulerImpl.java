package it.unibo.geometrybash.controller;

import it.unibo.geometrybash.commons.assets.AudioManager;
import it.unibo.geometrybash.commons.assets.AudioStore;
import it.unibo.geometrybash.commons.assets.ResourceLoader;

/**
 * Class to handle the music in the controller using the {@link AudioManager}
 * class.
 */
class ControllerAudioSchedulerImpl implements ControllerAudioScheduler {

    private static final String MENU_MUSIC = "it/unibo/geometrybash/audio/menu.wav";
    private static final String LEVEL_MUSIC = "it/unibo/geometrybash/audio/level1.wav";
    private static final String ON_FILE_LOADING_ERROR = "Error while loading the audio file";
    private final AudioManager audioManager;
    private boolean isInit;

    ControllerAudioSchedulerImpl(final ResourceLoader rL) {
        final AudioStore audioStore = new AudioStore(rL);
        audioManager = new AudioManager(audioStore);
    }

    private void checkInit() throws ImpossibleToReproduceMusicException {
        if (!isInit) {
            throw new ImpossibleToReproduceMusicException("Never called the first start method.");
        }
    }

    // CHECKSTYLE: IllegalCatch OFF
    // Catching generic exception to enhance the resilience of the audio file
    // loading
    // doing this if the error is only in the resource loading or audio reproducing
    // it wont affect
    // the whole program.
    /**
     * {@inheritDoc}
     */
    @Override
    public void fromMenuToGame() throws ImpossibleToReproduceMusicException {
        checkInit();
        try {
            audioManager.stop(MENU_MUSIC);
            audioManager.loop(LEVEL_MUSIC);
        } catch (final Exception e) { // NOPMD
            // Catching generic exception to enhance the resilience of the audio file
            // loading
            // doing this if the error is only in the resource loading or audio reproducing
            // it wont affect
            // the whole program.
            throw new ImpossibleToReproduceMusicException(ON_FILE_LOADING_ERROR, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fromGameToMenu() throws ImpossibleToReproduceMusicException {
        checkInit();
        try {
            audioManager.stop(LEVEL_MUSIC);
            audioManager.loop(MENU_MUSIC);
        } catch (final Exception e) { // NOPMD
            // Catching generic exception to enhance the resilience of the audio file
            // loading
            // doing this if the error is only in the resource loading or audio reproducing
            // it wont affect
            // the whole program.
            throw new ImpossibleToReproduceMusicException(ON_FILE_LOADING_ERROR, e);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void firstStart() throws ImpossibleToReproduceMusicException {
        if (isInit) {
            throw new ImpossibleToReproduceMusicException("The audio file has been initialized yet.");

        }
        try {
            audioManager.loop(MENU_MUSIC);
            this.isInit = true;
        } catch (final Exception e) { // NOPMD
            // Catching generic exception to enhance the resilience of the audio file
            // loading
            // doing this if the error is only in the resource loading or audio reproducing
            // it wont affect
            // the whole program.
            throw new ImpossibleToReproduceMusicException(ON_FILE_LOADING_ERROR, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void restartLevelMusic() throws ImpossibleToReproduceMusicException {
        checkInit();
        try {
            audioManager.stop(LEVEL_MUSIC);
            audioManager.loop(LEVEL_MUSIC);
        } catch (final Exception e) { // NOPMD
            // Catching generic exception to enhance the resilience of the audio file
            // loading
            // doing this if the error is only in the resource loading or audio reproducing
            // it wont affect
            // the whole program.
            throw new ImpossibleToReproduceMusicException(ON_FILE_LOADING_ERROR, e);
        }
    }
    // CHECKSTYLE: IllegalCatch ON

}
