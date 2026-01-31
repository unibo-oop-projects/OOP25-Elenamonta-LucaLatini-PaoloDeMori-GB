package it.unibo.geometrybash.view.gamepanel;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import it.unibo.geometrybash.commons.assets.AssetStore;
import it.unibo.geometrybash.commons.dtos.GameStateDto;
import it.unibo.geometrybash.view.core.RenderContext;
import it.unibo.geometrybash.view.utilities.GameResolution;
import it.unibo.geometrybash.view.utilities.TerminalColor;

/**
 * The builder for a JFrame of this project.
 * 
 * <p>
 * Implemented with Builder pattern.
 * </p>
 * 
 * @see PanelWithEntities
 * @see JFrame
 * @see GameResolution
 */
public class GameFrameBuilder {

    private String title = "Default-Game-Title";
    private GameResolution gameResolution = GameResolution.MEDIUM;
    private Runnable runnable;
    private PanelWithEntities mainPanel;
    private Color backGroundColor = TerminalColor.BACKGROUND;
    private boolean isResizable;

    /**
     * The void constructore of this class.
     */
    public GameFrameBuilder() {
        // Default Constructor.
    }

    /**
     * Adds a new customizable action to execute when closing the frame.
     * 
     * @param frame       the frame to close.
     * @param newRunnable the action to execute.
     */
    private void addCustomizableOnCLoseOperation(final JFrame frame, final Runnable newRunnable) {
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        frame.addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(final WindowEvent e) {
                        newRunnable.run();
                    }
                });
    }

    /**
     * Sets a new game title.
     * 
     * @param newTitle The new title.
     * @return the instance of the builder executing this action.
     */
    public GameFrameBuilder setGameTitle(final String newTitle) {
        this.title = newTitle;
        return this;
    }

    /**
     * Sets the gameResolution.
     * 
     * @param newGameResolution the game resolution
     * @return the instance of the builder executing this action.
     */
    public GameFrameBuilder setResolution(final GameResolution newGameResolution) {
        this.gameResolution = newGameResolution;
        return this;
    }

    /**
     * Sets the custom on close action.
     * 
     * @param newRunnable the action to execute.
     * @return the instance of the builder executing this action.
     */
    public GameFrameBuilder setOnCloseAction(final Runnable newRunnable) {
        this.runnable = newRunnable;
        return this;
    }

    /**
     * Sets the first shown Jpanel.
     * 
     * @param newMainPanelFactory the panel to set.
     * @param renderContext       the information useful to render this frame.
     * @param assetStore          the object that retrieves resources.
     * @return the instance of the builder executing this action.
     */
    public GameFrameBuilder setMainPanel(final PanelsFactory newMainPanelFactory, final RenderContext renderContext,
            final AssetStore assetStore) {
        final PanelWithEntities panelWithEntities = newMainPanelFactory.createPanelWithEntities(renderContext,
                assetStore);
        this.mainPanel = panelWithEntities;
        return this;
    }

    /**
     * Sets the background color .
     * 
     * @param color the color to set.
     * @return the instance of the builder executing this action.
     */
    public GameFrameBuilder setBackGroundColor(final Color color) {
        this.backGroundColor = color;
        return this;
    }

    /**
     * Sets if the windows is resible or not .
     * 
     * @param newIsResizable true if it's resiable fals otherwise.
     * @return the instance of the builder executing this action.
     */
    public GameFrameBuilder setResizable(final boolean newIsResizable) {
        this.isResizable = newIsResizable;
        return this;
    }

    /**
     * Creates the {@link JFrame} using the set values.
     * 
     * @return the created JFrame.
     */
    public GameFrame<GameStateDto> build() {
        final GameFrame<GameStateDto> mainFrame = new GameFrame<>(this.title);
        mainFrame.setSize(new Dimension(gameResolution.getViewPortWidth(), gameResolution.getViewPortHeight()));
        mainFrame.setBackground(backGroundColor);
        if (runnable == null) {
            mainFrame.setDefaultCloseOperation(
                    WindowConstants.EXIT_ON_CLOSE);
        } else {
            addCustomizableOnCLoseOperation(mainFrame, runnable);
        }

        mainFrame.setResizable(isResizable);
        mainFrame.setLayout(new BorderLayout());
        if (mainPanel != null) {
            mainFrame.add(mainPanel, BorderLayout.CENTER);
            mainFrame.setUpdatablePanel(mainPanel);
        }

        mainFrame.setLocationRelativeTo(null);

        return mainFrame;

    }
}
