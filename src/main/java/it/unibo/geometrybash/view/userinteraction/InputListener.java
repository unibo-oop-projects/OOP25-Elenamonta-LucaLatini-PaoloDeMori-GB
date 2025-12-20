package it.unibo.geometrybash.view.userinteraction;

public interface InputListener {
    void setOnClickStrategy(InputListenerStrategy strategy);

    void setOnReleasedStrategy(InputListenerStrategy strategy);
}
