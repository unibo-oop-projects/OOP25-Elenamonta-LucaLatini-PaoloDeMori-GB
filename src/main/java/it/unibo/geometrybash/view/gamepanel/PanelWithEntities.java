package it.unibo.geometrybash.view.gamepanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

import it.unibo.geometrybash.commons.assets.AssetStore;
import it.unibo.geometrybash.commons.assets.ResourceLoader;
import it.unibo.geometrybash.commons.dtos.ObstacleDto;
import it.unibo.geometrybash.commons.dtos.PlayerDto;
import it.unibo.geometrybash.commons.dtos.PowerUpDto;
import it.unibo.geometrybash.view.core.RenderContext;
import it.unibo.geometrybash.view.core.SpriteRegistry;
import it.unibo.geometrybash.view.renderer.ObstacleView;
import it.unibo.geometrybash.view.renderer.PowerUpView;
import it.unibo.geometrybash.view.utilities.TerminalColor;
import it.unibo.geometrybash.view.renderer.PlayerView;

public class PanelWithEntities extends JPanel {
    private ObstacleView obstacleView;
    private PowerUpView powerUpView;
    private PlayerView playerView;
    private RenderContext renderContext;

    private List<ObstacleDto> obstaclesDto;
    private List<PowerUpDto> powerUpDto;
    private PlayerDto playerDto;

    private AssetStore assetStore;
    private SpriteRegistry spriteRegistry;

    public PanelWithEntities(ResourceLoader resourceLoader, SpriteRegistry spriteRegistry, AssetStore assetStore,
            RenderContext renderContext) {
        this.assetStore = new AssetStore(resourceLoader);
        this.spriteRegistry = new SpriteRegistry(assetStore);
        this.obstacleView = new ObstacleView(spriteRegistry);
        this.powerUpView = new PowerUpView(spriteRegistry);
        this.playerView = new PlayerView(spriteRegistry);
        this.renderContext = renderContext;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphic = (Graphics2D) g;
        graphic.setBackground(TerminalColor.BACKGROUND);

        if (this.obstaclesDto != null && this.powerUpDto != null && this.playerDto != null) {
            this.obstacleView.draw(graphic, this.renderContext, this.obstaclesDto);
            this.powerUpView.draw(graphic, this.renderContext, this.powerUpDto);
            this.playerView.draw(graphic, this.renderContext, this.playerDto);
        }

    }

    protected void update(List<ObstacleDto> obstacles, List<PowerUpDto> powerUps, PlayerDto playerDto) {
        this.obstaclesDto = obstacles;
        this.powerUpDto = powerUps;
        this.playerDto = playerDto;
        this.repaint();
    }

}
