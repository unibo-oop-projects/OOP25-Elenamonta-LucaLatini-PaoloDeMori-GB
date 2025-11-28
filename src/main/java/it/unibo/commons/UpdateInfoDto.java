package it.unibo.commons;

import java.io.Serializable;

/**
 * The information used to update the view.
 * 
 * <p>This Data Transfer Object should be used by the {@link it.unibo.controller.Controller} to send the view
 * the information to update after a cycle of gameloop.
 * Implements {@link Serializable} to ensure a correct communciation
 * 
 * 
 * @see it.unibo.controller.Controller
 */
public class UpdateInfoDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Void constructor place holder.
     */
    public UpdateInfoDto() {
        //TODO
    }
}
