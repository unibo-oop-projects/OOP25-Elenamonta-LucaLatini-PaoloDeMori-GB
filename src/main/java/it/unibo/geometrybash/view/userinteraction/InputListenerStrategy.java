package it.unibo.geometrybash.view.userinteraction;

@FunctionalInterface
public interface InputListenerStrategy {

    void handleInput(int keyCode);
}
