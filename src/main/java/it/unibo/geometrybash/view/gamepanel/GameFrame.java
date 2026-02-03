package it.unibo.geometrybash.view.gamepanel;

import javax.swing.JFrame;

import it.unibo.geometrybash.view.UpdatableWithDto;

/**
 * A JFrame updatable with
 * {@link it.unibo.geometrybash.commons.dtos.GameStateDto}.
 *
 * @param <T> the type of dto that makes possible to update this
 *            frame.
 */

public class GameFrame<T> extends JFrame implements UpdatableWithDto<T> {

    /**
     * Value for serialization.
     */
    private static final long serialVersionUID = 1L;
    /**
     * The game panel to show while using this class.
     */
    private UpdatableWithDto<T> panel;

    /**
     * Constructor with window's title.
     *
     * @param title the title of the window.
     */
    protected GameFrame(final String title) {
        super(title);
    }

    /**
     * Updates the Frame.
     */
    @Override
    public void update(final T gameStateDto) {
        if (panel != null) {
            panel.update(gameStateDto);
        }
    }

    /**
     * Sets the panel to update when update required.
     *
     * @param newPanel the panel to update.
     */
    protected void setUpdatablePanel(final UpdatableWithDto<T> newPanel) {
        this.panel = newPanel;
    }

}
