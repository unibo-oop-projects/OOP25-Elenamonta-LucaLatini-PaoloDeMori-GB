package it.unibo.geometrybash.view;

import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewEvent;

public interface KeyProcessor {
    public void keyPressionRecived(ViewEvent event);
}
