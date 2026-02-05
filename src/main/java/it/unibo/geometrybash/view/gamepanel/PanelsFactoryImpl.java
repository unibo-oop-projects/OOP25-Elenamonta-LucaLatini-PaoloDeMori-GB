package it.unibo.geometrybash.view.gamepanel;

import it.unibo.geometrybash.commons.assets.AssetStore;
import it.unibo.geometrybash.view.core.RenderContext;

/**
 * A factory for a game panel for geometry bash with java swing.
 */
public class PanelsFactoryImpl implements PanelsFactory {

    /**
     * Default constructor.
     */
    public PanelsFactoryImpl() {
        // default constructor.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PanelWithEntities createPanelWithEntities(final RenderContext renderContext, final AssetStore assetStore) {
        return new PanelWithEntities(renderContext, assetStore);
    }

}
