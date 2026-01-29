package it.unibo.geometrybash.view.gamepanel;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import it.unibo.geometrybash.commons.dtos.GameStateDto;
import it.unibo.geometrybash.view.core.Camera2D;
import it.unibo.geometrybash.view.core.RenderContext;
import it.unibo.geometrybash.view.utilities.GameResolution;
import it.unibo.geometrybash.view.utilities.TerminalColor;

public class GamePanelImpl extends GamePanel {

    private Camera2D camera = new Camera2D();
    private JFrame mainFrame;
    private RenderContext renderContext;

    public GamePanelImpl(GameResolution gameResolution){
        renderContext = new RenderContext(camera, gameResolution.getViewPortX(), gameResolution.getViewPortY());

        createMainFrame(gameResolution);
    }

    private void createMainFrame(GameResolution gameResolution) {
        mainFrame = new JFrame("Geometry-Bash");
        mainFrame.setResizable(false);
        mainFrame.setSize(new Dimension(gameResolution.getViewPortX(), gameResolution.getViewPortY()));
        mainFrame.setBackground(TerminalColor.BACKGROUND);
        mainFrame.setLayout(new BorderLayout());
    }

    @Override
    public void update(GameStateDto gameStateDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

}
