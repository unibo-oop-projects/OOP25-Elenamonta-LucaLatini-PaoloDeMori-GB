package it.unibo.geometrybash.view.gamepanel;

import it.unibo.geometrybash.commons.assets.AssetStore;
import it.unibo.geometrybash.commons.dtos.GameStateDto;
import it.unibo.geometrybash.view.core.RenderContext;
import it.unibo.geometrybash.view.utilities.GameResolution;

/**
 * The class that represents the graphic representation of the game while
 * playing.
 */
public interface GamePanel {

    /**
     * Method to correctly initialized the Game Panel.
     * 
     * @param renderContext  the object that exposes methods to give encessary
     *                       informations to render view entities.
     * @param assetStore     the object responsible of retrieving resources.
     * @param gameTitle      the title of the gui window.
     * @param gameResolution the size resolution of the game window.
     */
    void init(RenderContext renderContext, AssetStore assetStore, String gameTitle,
            GameResolution gameResolution);

    /**
     * Method to show the game window.
     */
    void show();

    /**
     * Method to hide the game window.
     */
    void hide();

    /**
     * Method to update the game window.
     * 
     * @param gameStateDto the representation of the model state.
     */
    void update(GameStateDto gameStateDto);

    /**
     * Method to dispose the game window.
     */
    void dispose();

}
