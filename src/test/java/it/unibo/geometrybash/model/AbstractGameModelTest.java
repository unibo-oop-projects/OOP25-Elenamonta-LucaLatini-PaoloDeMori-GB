package it.unibo.geometrybash.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import it.unibo.geometrybash.commons.UpdateInfoDto;
import it.unibo.geometrybash.commons.pattern.observerpattern.Observer;
import it.unibo.geometrybash.commons.pattern.observerpattern.modelobserver.ModelEvent;
import it.unibo.geometrybash.model.core.Updatable;
import it.unibo.geometrybash.model.exceptions.InvalidModelMethodInvocationException;
import it.unibo.geometrybash.model.exceptions.RunTimeModelInitializationException;
import it.unibo.geometrybash.model.level.Level;
import it.unibo.geometrybash.model.player.Player;

// CHECKSTYLE: AbstractClassName OFF
/*Check style signals this name as invalid because it starts with Abstract without being an
  abstract class, but it's clear that it is a test for the abstract class*/
class AbstractGameModelTest {
    // CHECKSTYLE: AbstractClassName ON

    private static final String NOT_NECESSARY_METHOD = "this method is not tested here";
    private TestModel aGM;

    @Test
    void testConstructor() {
        assertThrows(RunTimeModelInitializationException.class, () -> new TestModel(null));
        assertDoesNotThrow(() -> new TestModel());
    }

    @Test
    void testUpdateAndAfterGameObjectsUpdate() {
        final List<Updatable> list = new LinkedList<>();
        list.add(new TestUpdatable());
        aGM = new TestModel(list);
        assertDoesNotThrow(() -> aGM.update(0));
        // check if after the update the update
        assertTrue(aGM.isAfterGameObjectsUpdateActionExecuted());
    }

    @Test
    void testIsUpdatable() {
        final List<Updatable> list = new LinkedList<>();
        list.add(new TestUpdatable());
        aGM = new TestModel(list);
        aGM.toggleIsUpdatable();
        assertDoesNotThrow(() -> aGM.update(0));
        assertFalse(aGM.isAfterGameObjectsUpdateActionExecuted());
        aGM.toggleIsUpdatable();
        assertDoesNotThrow(() -> aGM.update(0));
        assertTrue(aGM.isAfterGameObjectsUpdateActionExecuted());
    }

    @Test
    void testGetUpdatables() {
        final List<Updatable> list = new LinkedList<>();
        final Updatable upd = new TestUpdatable();
        list.add(upd);
        aGM = new TestModel(list);
        assertEquals(aGM.getUpdatables().size(), 1);
        assertEquals(aGM.getUpdatables().get(0), upd);
    }

    @Test
    void testAddUpdatable() {
        final List<Updatable> list = new LinkedList<>();
        final Updatable upd = new TestUpdatable();
        aGM = new TestModel(list);
        aGM.addUpdatableGameObjects(upd);
        assertEquals(aGM.getUpdatables().size(), 0);
        aGM.update(0);
        assertEquals(aGM.getUpdatables().get(0), upd);
    }

    @Test
    void testRemoveUpdatable() {
        final List<Updatable> list = new LinkedList<>();
        final Updatable upd = new TestUpdatable();
        list.add(upd);
        aGM = new TestModel(list);
        aGM.removeUpdatableGameObjects(upd);
        assertEquals(aGM.getUpdatables().get(0), upd);
        aGM.update(0);
        assertEquals(aGM.getUpdatables().size(), 0);
    }

    @Test
    void testCleanUpdatables() {
        final List<Updatable> list = new LinkedList<>();
        final Updatable upd = new TestUpdatable();
        final Updatable upd2 = new TestUpdatable();
        list.add(upd);
        list.add(upd2);
        aGM = new TestModel(list);
        assertEquals(aGM.getUpdatables().size(), 2);
        aGM.clearUpdatableList();
        assertEquals(aGM.getUpdatables().size(), 0);
    }

    @Test
    void testClearToList() {
        final List<Updatable> list = new LinkedList<>();
        final Updatable upd = new TestUpdatable();
        final Updatable upd2 = new TestUpdatable();
        list.add(upd);
        aGM = new TestModel(list);
        aGM.addUpdatableGameObjects(upd2);
        aGM.removeUpdatableGameObjects(upd);
        aGM.clearToLists();
        aGM.update(0);
        assertEquals(aGM.getUpdatables().size(), 1);
        assertEquals(aGM.getUpdatables().get(0), upd);
    }

    class TestUpdatable implements Updatable {

        @Override
        public void update(final float deltaTime) {
            aGM.addUpdatableGameObjects(new Updatable() {

                @Override
                public void update(final float deltaTime) {
                }

            });
        }

    }

    class TestModel extends AbstractGameModel {

        private boolean isAfterGameObjectsUpdateActionExecuted;
        private boolean isUpdatable = true;

        TestModel(final List<Updatable> updatables) {
            super(updatables);
        }

        TestModel() {
            super();
        }

        @Override
        public void pause() {
            throw new UnsupportedOperationException(NOT_NECESSARY_METHOD);
        }

        @Override
        public void resume() {
            throw new UnsupportedOperationException(NOT_NECESSARY_METHOD);
        }

        @Override
        public void restart() {
            throw new UnsupportedOperationException(NOT_NECESSARY_METHOD);
        }

        @Override
        public void jumpSignal() {
            throw new UnsupportedOperationException(NOT_NECESSARY_METHOD);
        }

        @Override
        public Player<?> getPlayer() {
            throw new UnsupportedOperationException(NOT_NECESSARY_METHOD);
        }

        @Override
        public Level getLevel() {
            throw new UnsupportedOperationException(NOT_NECESSARY_METHOD);
        }

        @Override
        public Status getStatus() {
            throw new UnsupportedOperationException(NOT_NECESSARY_METHOD);
        }

        @Override
        public void addObserver(final Observer<? super ModelEvent> obs) {
            throw new UnsupportedOperationException(NOT_NECESSARY_METHOD);
        }

        @Override
        public void notifyObservers(final ModelEvent event) {
            throw new UnsupportedOperationException(NOT_NECESSARY_METHOD);
        }

        @Override
        protected void afterGameObjectsUpdate(final float deltaTime) {
            isAfterGameObjectsUpdateActionExecuted = true;
        }

        protected boolean isAfterGameObjectsUpdateActionExecuted() {
            return this.isAfterGameObjectsUpdateActionExecuted;
        }

        protected void toggleIsUpdatable() {
            this.isUpdatable = !this.isUpdatable;
        }

        @Override
        protected boolean isUpdatable() {
            return this.isUpdatable;
        }

        @Override
        public void start(final String levelName) throws InvalidModelMethodInvocationException {
            throw new UnsupportedOperationException(NOT_NECESSARY_METHOD);
        }

        @Override
        public UpdateInfoDto tDto() {
            throw new UnsupportedOperationException(NOT_NECESSARY_METHOD);
        }
    }
}
