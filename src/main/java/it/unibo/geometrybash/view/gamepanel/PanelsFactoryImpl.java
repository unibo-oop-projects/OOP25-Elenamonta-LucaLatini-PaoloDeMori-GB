package it.unibo.geometrybash.view.gamepanel;

import it.unibo.geometrybash.commons.assets.AssetStore;
import it.unibo.geometrybash.view.core.RenderContext;

/**
 * {@inheritDoc}.
 */
public class PanelsFactoryImpl implements PanelsFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public PanelWithEntities createPanelWithEntities(final RenderContext renderContext, final AssetStore assetStore) {
        return new PanelWithEntities(renderContext, assetStore);
    }

}
