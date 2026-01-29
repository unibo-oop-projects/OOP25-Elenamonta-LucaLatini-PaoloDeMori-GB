package it.unibo.geometrybash.view.gamepanel;

import javax.swing.JPanel;

import it.unibo.geometrybash.commons.dtos.GameStateDto;

public abstract class GamePanel extends JPanel {
    public abstract void update(GameStateDto gameStateDto);
}
