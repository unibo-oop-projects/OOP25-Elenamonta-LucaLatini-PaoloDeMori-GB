package it.unibo.geometrybash.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.jbox2d.dynamics.Body;
import org.junit.jupiter.api.Test;

import it.unibo.geometrybash.commons.assets.ResourceLoader;
import it.unibo.geometrybash.commons.assets.ResourceLoaderImpl;
import it.unibo.geometrybash.model.exceptions.InvalidModelMethodInvocationException;
import it.unibo.geometrybash.model.physicsengine.PhysicsEngineFactory;
import it.unibo.geometrybash.model.physicsengine.exception.ModelExecutionException;
import it.unibo.geometrybash.model.physicsengine.impl.jbox2d.JBox2DPhysicsEngineFactory;

class GameModelImplTest {
    private static final String ERROR_STARTING_MESSAGE = "Error while starting the gamemodel";
    private final ResourceLoader rL = new ResourceLoaderImpl();
    private final PhysicsEngineFactory<Body> pH = new JBox2DPhysicsEngineFactory();

    @Test
    void testStartPauseAndResume() {
        final GameModelImpl gM = new GameModelImpl(rL, pH);

        try {
            gM.start(MenuModel.LEVELS_NAME_LIST.get(0));
        } catch (InvalidModelMethodInvocationException | ModelExecutionException e) {
            fail("Error while starting the gamemodel");
        }

        assertThrows(InvalidModelMethodInvocationException.class, gM::resume);

        assertDoesNotThrow(gM::pause);
        assertThrows(InvalidModelMethodInvocationException.class, () -> gM.start(MenuModel.LEVELS_NAME_LIST.get(0)));
        assertEquals(Status.ONPAUSE, gM.getStatus());
        assertThrows(InvalidModelMethodInvocationException.class, gM::pause);
        assertDoesNotThrow(gM::resume);
        assertEquals(Status.PLAYING, gM.getStatus());

    }

    @Test
    void testRestart() {
        final GameModelImpl gM = new GameModelImpl(rL, pH);

        try {
            gM.start(MenuModel.LEVELS_NAME_LIST.get(0));
        } catch (InvalidModelMethodInvocationException | ModelExecutionException e) {
            fail(ERROR_STARTING_MESSAGE);
        }

        assertDoesNotThrow(gM::pause);
        assertEquals(Status.ONPAUSE, gM.getStatus());
        assertDoesNotThrow(gM::restart);
        assertEquals(Status.PLAYING, gM.getStatus());

    }

    @Test
    void testGetPlayer() {
        final GameModelImpl gM = new GameModelImpl(rL, pH);
        assertThrows(ModelExecutionException.class, gM::getPlayer);

        try {
            gM.start(MenuModel.LEVELS_NAME_LIST.get(0));
        } catch (InvalidModelMethodInvocationException | ModelExecutionException e) {
            fail(ERROR_STARTING_MESSAGE);
        }
        assertEquals(Status.PLAYING, gM.getStatus());

        assertDoesNotThrow(gM::getPlayer);
    }

    @Test
    void testRespawnPlayer() throws ModelExecutionException {
        final float delta = 1 / 60f;
        final GameModelImpl gM = new GameModelImpl(rL, pH);
        assertThrows(ModelExecutionException.class, gM::getPlayer);

        try {
            gM.start(MenuModel.LEVELS_NAME_LIST.get(0));
        } catch (InvalidModelMethodInvocationException | ModelExecutionException e) {
            fail(ERROR_STARTING_MESSAGE);
        }
        assertEquals(Status.PLAYING, gM.getStatus());

        assertDoesNotThrow(gM::getPlayer);

        gM.update(delta);
        gM.update(delta);

        gM.respawnPlayer();
        assertTrue(Math.abs(gM.getPlayer().getPosition().x() - gM.getLevel().getPlayerStartPosition().x()) < 1);

    }

    @Test
    void testGetSetInnerColor() {
        final int innerNewValue = 3;
        final int outerNewValue = 4;

        final GameModelImpl gM = new GameModelImpl(rL, pH);
        assertThrows(ModelExecutionException.class, gM::getPlayer);

        try {
            gM.start(MenuModel.LEVELS_NAME_LIST.get(0));
        } catch (InvalidModelMethodInvocationException | ModelExecutionException e) {
            fail(ERROR_STARTING_MESSAGE);
        }
        assertNotEquals(innerNewValue, gM.getSetInnerColor());
        assertNotEquals(outerNewValue, gM.getSetOuterColor());

        gM.setPlayerInnerColor(innerNewValue);
        gM.setPlayerOuterColor(outerNewValue);

        assertEquals(innerNewValue, gM.getSetInnerColor());
        assertEquals(outerNewValue, gM.getSetOuterColor());

    }
}
