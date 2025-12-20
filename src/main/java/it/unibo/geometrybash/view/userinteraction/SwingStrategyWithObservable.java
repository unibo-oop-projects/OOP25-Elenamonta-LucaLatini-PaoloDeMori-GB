package it.unibo.geometrybash.view.userinteraction;

import java.awt.event.KeyEvent;
import it.unibo.geometrybash.commons.input.StandardViewEventType;
import it.unibo.geometrybash.commons.input.ViewEventTypeFactory;
import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewEvent;
import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewEventType;
import it.unibo.geometrybash.view.KeyProcessor;

public class SwingStrategyWithObservable implements InputListenerStrategy {

    private KeyProcessor kp;
    private StandardViewEventType eventType = null;

    SwingStrategyWithObservable(final KeyProcessor obs){
        this.kp = obs;
    }

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
        kp.keyPressionRecived(viewEvent);
    }
}
