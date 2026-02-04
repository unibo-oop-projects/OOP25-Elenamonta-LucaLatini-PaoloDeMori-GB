package it.unibo.geometrybash.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import it.unibo.geometrybash.commons.assets.AssetStore;
import it.unibo.geometrybash.commons.assets.ResourceLoader;
import it.unibo.geometrybash.commons.assets.ResourceLoaderImpl;
import it.unibo.geometrybash.model.GameModelImpl;
import it.unibo.geometrybash.model.Status;
import it.unibo.geometrybash.model.physicsengine.impl.jbox2d.JBox2DPhysicsEngineFactory;
import it.unibo.geometrybash.view.View;
import it.unibo.geometrybash.view.ViewImpl;

class TestAbstractControllerImpl {

    private final ResourceLoader rL = new ResourceLoaderImpl();

    private final View view = new ViewImpl(rL, new AssetStore(rL));

    @Test
    void evaluateDeltaTimeTest() {
        final float delta = 1 / 60f;

        final GameModelImpl gM = new GameModelImpl(rL, new JBox2DPhysicsEngineFactory());
        final AbstractControllerImpl abs = new StaticDeltaTimeControllerImpl(gM,
                view, rL);
        assertEquals(delta, abs.evaluateDeltaTime());
    }

    @Test
    void controllerInterfacesTest() {
        final GameModelImpl gM = new GameModelImpl(rL, new JBox2DPhysicsEngineFactory());
        final AbstractControllerImpl abs = new StaticDeltaTimeControllerImpl(gM,
                new ViewImpl(rL, new AssetStore(rL)), rL);
        assertEquals(Status.NEVERSTARTED, abs.getModelStatus());
        assertTrue(abs.isTheViewSet());
        assertTrue(abs.isTheModelSet());
    }
}
