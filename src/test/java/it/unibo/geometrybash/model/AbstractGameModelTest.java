package it.unibo.geometrybash.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import it.unibo.geometrybash.commons.pattern.observerpattern.Observer;
import it.unibo.geometrybash.commons.pattern.observerpattern.modelobserver.ModelEvent;
import it.unibo.geometrybash.model.core.Updatable;

// CHECKSTYLE: AbstractClassName OFF
class AbstractGameModelTest {
    // CHECKSTYLE: AbstractClassName ON

    private static final String NOT_NECESSARY_METHOD = "this method is not tested here"; 
    private TestModel aGM;

    @Test
    void testUpdateAndAfterGameObjectsUpdate() {
        final List<Updatable> list = new LinkedList<>();
        list.add(new TestUpdatable());
        aGM = new TestModel(list);
        assertDoesNotThrow(() -> aGM.update(0));
        //check if after the update the update 
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
        public Player getPlayer() {
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
        protected void afterGameObjectsUpdate() {
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

    }
}
