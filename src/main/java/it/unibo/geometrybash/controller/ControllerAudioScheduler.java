package it.unibo.geometrybash.controller;

/**
 * A class for scheduling the audioduring controller execution.
 */
interface ControllerAudioScheduler {
    /**
     * Switches from menu music to gamemusic.
     * 
     * @throws ImpossibleToReproduceMusicException if an error occured while loading
     *                                             the music or the firstStart
     *                                             method was never called.
     */
    void fromMenuToGame() throws ImpossibleToReproduceMusicException;

    /**
     * Switches from game music to menu music.
     * 
     * @throws ImpossibleToReproduceMusicException if an error occured while loading
     *                                             the music or the firstStart
     *                                             method was never called.
     */
    void fromGameToMenu() throws ImpossibleToReproduceMusicException;

    /**
     * Start the first music to reproduce.
     * 
     * @throws ImpossibleToReproduceMusicException if an error occured while loading
     *                                             the music.
     */
    void firstStart() throws ImpossibleToReproduceMusicException;

    /**
     * Restarts the level music.
     * 
     * @throws ImpossibleToReproduceMusicException if an error occured while loading
     *                                             the music or the firstStart
     *                                             method was never called.
     */
    void restartLevelMusic() throws ImpossibleToReproduceMusicException;

}
