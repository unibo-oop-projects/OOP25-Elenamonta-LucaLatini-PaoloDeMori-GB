package it.unibo.geometrybash.view.userinteraction;

import java.awt.event.KeyEvent;
import it.unibo.geometrybash.commons.input.StandardViewEventType;
import it.unibo.geometrybash.commons.input.ViewEventTypeFactory;
import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewEvent;
import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewEventType;
import it.unibo.geometrybash.view.KeyProcessor;

/**
 * Implementation of {@link InputListenerStrategy} that maps Swing key codes
 * to view events and notifies a {@link KeyProcessor}.
 */
public final class SwingStrategyWithObservable implements InputListenerStrategy {

    private final KeyProcessor kp;
    private StandardViewEventType eventType;

    /**
     * Creates a new strategy that will notify the given processor.
     *
     * @param obs the processor to notify of key events
     */
    public SwingStrategyWithObservable(final KeyProcessor obs) {
        this.kp = obs;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * Maps SPACE/UP to JUMP and ESCAPE to MENU. Other keys are ignored.
     */
    @Override
    public void handleInput(final int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_UP:
                eventType = StandardViewEventType.JUMP;
                break;
            case KeyEvent.VK_ESCAPE:
                eventType = StandardViewEventType.MENU;
                break;
            default:
                return;
        }
        final ViewEventType viewEventType = ViewEventTypeFactory.standard(eventType);
        final ViewEvent viewEvent = ViewEvent.createEvent(viewEventType);
        kp.keyPressionReceived(viewEvent);
    }
}
