package it.unibo.geometrybash.view.gamepanel;

import it.unibo.geometrybash.commons.assets.AssetStore;
import it.unibo.geometrybash.view.core.RenderContext;

/**
 * A factory for panels.
 */
@FunctionalInterface
public interface PanelsFactory {

    /**
     * Creates a new instance of {@link PanelWithEntities} a panel that aim to draw
     * the entities of the level.
     * 
     * @param renderContext the information useful to draw the entities in the panel.
     * @param assetStore the object that retrieves resources.
     * @return the new PanelWithEntitie's instance.
     */
    PanelWithEntities createPanelWithEntities(RenderContext renderContext, AssetStore assetStore);

}
