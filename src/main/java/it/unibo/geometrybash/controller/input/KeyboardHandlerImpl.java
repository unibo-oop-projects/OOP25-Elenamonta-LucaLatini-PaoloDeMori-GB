package it.unibo.geometrybash.controller.input;

import java.awt.event.KeyEvent;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.geometrybash.controller.OnInputEventAction;

/**
 * Implementation of {@link KeyboardHandler} for handling keyboard input.
 *
 *  <p>
 * Default key bindings are: main key is SPACE or UP arrow; menu key is ESCAPE.
 */

public final class KeyboardHandlerImpl implements KeyboardHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeyboardHandlerImpl.class);

    private static final int DEFAULT_MAIN_KEY = KeyEvent.VK_SPACE;

    private static final int ALT_MAIN_KEY = KeyEvent.VK_UP;

    private static final int DEFAULT_MENU_KEY = KeyEvent.VK_ESCAPE;

    private OnInputEventAction mainKeyAction;
    private OnInputEventAction menuKeyAction;

    @Override
    public void handleKeyPressed(final KeyEvent event) {
        Objects.requireNonNull(event, "KeyEvent cannot be null");
        final int keyCode = event.getKeyCode();

        if (keyCode == DEFAULT_MAIN_KEY || keyCode == ALT_MAIN_KEY) {
            executeAction(mainKeyAction, "main");
        } else if (keyCode == DEFAULT_MENU_KEY) {
            executeAction(menuKeyAction, "menu");
        }
    }

    @Override
    public void setMainKeyAction(final OnInputEventAction action) {
        this.mainKeyAction = Objects.requireNonNull(action, "Main action cannot be null");
    }

    @Override
    public void setMenuKeyAction(final OnInputEventAction action) {
        this.menuKeyAction = Objects.requireNonNull(action, "Menu action cannot be null");
    }

    private void executeAction(final OnInputEventAction action, final String actionType) {
        if (action != null) {
            action.executeAction();
        } else {
            LOGGER.warn("{} key action not configured", actionType);
        }
    }

}
