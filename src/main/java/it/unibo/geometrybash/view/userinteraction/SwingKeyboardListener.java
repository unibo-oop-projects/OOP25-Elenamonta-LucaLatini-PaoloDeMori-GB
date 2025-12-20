package it.unibo.geometrybash.view.userinteraction;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SwingKeyboardListener extends KeyAdapter implements InputListener {

    private InputListenerStrategy onClickStrategy;

    SwingKeyboardListener(final InputListenerStrategy strategy){
        this.setOnClickStrategy(strategy);
    }

    @Override
    public void setOnClickStrategy(final InputListenerStrategy strategy) {
        this.onClickStrategy = strategy;
    }

    @Override
    public void setOnReleasedStrategy(final  InputListenerStrategy strategy) {
        throw new UnsupportedOperationException("In our implemtation released keys aren't evaluated");
    }

    @Override
    public void keyPressed(final KeyEvent e){
        onClickStrategy.handleInput(e.getKeyCode());
    }
}
