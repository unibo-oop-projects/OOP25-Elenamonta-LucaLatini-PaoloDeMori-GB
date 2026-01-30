package it.unibo.geometrybash.controller;

import it.unibo.geometrybash.commons.UpdateInfoDto;
import it.unibo.geometrybash.commons.assets.ResourceLoaderImpl;
import it.unibo.geometrybash.commons.pattern.observerpattern.Observer;
import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewEvent;
import it.unibo.geometrybash.controller.gameloop.GameLoopFixedExecutionTimeFactory;
import it.unibo.geometrybash.controller.input.InputHandlerFactoryImpl;
import it.unibo.geometrybash.model.GameModel;
import it.unibo.geometrybash.model.GameModelImpl;
import it.unibo.geometrybash.model.exceptions.InvalidModelMethodInvocationException;
import it.unibo.geometrybash.model.physicsengine.exception.ModelExecutionException;
import it.unibo.geometrybash.model.physicsengine.impl.jbox2d.JBox2dPhysicsEngineFactory;
import it.unibo.geometrybash.view.View;
import it.unibo.geometrybash.view.ViewScene;
import it.unibo.geometrybash.view.exceptions.ExecutionWithIllegalThreadException;
import it.unibo.geometrybash.view.exceptions.NotStartedViewException;

/**
 * An AbstractControllerImpl implementation that uses a static delta time.
 */
public class StaticDeltaTimeControllerImpl extends AbstractControllerImpl {
    /**
     * The static delta time.
     */
    private static final float DELTA_TIME = 1f / 60f;

    /**
     * The constructor of this class.
     *
     * @param gameModel del to use as the "brain" of this
     *
     * @param view      omponent to show the gui of this
     *
     */
    public StaticDeltaTimeControllerImpl(final GameModel gameModel, final View view) {
        super(gameModel, view, new GameLoopFixedExecutionTimeFactory(), new InputHandlerFactoryImpl());
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * This implementation returns a static delta time.
     * </p>
     */
    @Override
    protected float evaluateDeltaTime() {
        return DELTA_TIME;
    }

    public static void main(String[] args) throws InterruptedException, InvalidModelMethodInvocationException {
        View view = new View() {

            @Override
            public void notifyObservers(ViewEvent event) {
            }

            @Override
            public void init() {
            }

            @Override
            public void show() throws NotStartedViewException {
            }

            @Override
            public void stop() throws NotStartedViewException {
            }

            @Override
            public void update(UpdateInfoDto dto) throws NotStartedViewException, ExecutionWithIllegalThreadException {
            }

            @Override
            public void changeView(ViewScene scene) {
            }

            @Override
            public void disposeView() {
                System.exit(0);
            }

            @Override
            public void addObserver(Observer<? super ViewEvent> obs) {
            }

        };

        try {
            GameModel gameModel = new GameModelImpl(new ResourceLoaderImpl(), new JBox2dPhysicsEngineFactory());
            Controller controller = new StaticDeltaTimeControllerImpl(gameModel, view);
            //gameModel.start("tempLevel");
            controller.start();
            Thread.sleep(200);
            System.out.println("x: " + controller.getModel().toDto().getStateDto().player().x()+ " y: " + controller.getModel().toDto().getStateDto().player().y());
            controller.getModel().toDto().getStateDto().obstacles().forEach(i -> System.out.println("Ostatocolo di tipo " + i.type()+ " alla posizione " + i.x() + " y " + i.y()));
            Thread.sleep(2000);
            System.out.println("x: " + controller.getModel().toDto().getStateDto().player().x()+ " y: " + controller.getModel().toDto().getStateDto().player().y());
            controller.getModel().toDto().getStateDto().obstacles().forEach(i -> System.out.println("Ostatocolo di tipo " + i.type()+ " alla posizione " + i.x() + " y " + i.y()));
            Thread.sleep(2000);
            System.out.println("x: " + controller.getModel().toDto().getStateDto().player().x()+ " y: " + controller.getModel().toDto().getStateDto().player().y());
            controller.getModel().toDto().getStateDto().obstacles().forEach(i -> System.out.println("Ostatocolo di tipo " + i.type()+ " alla posizione " + i.x() + " y " + i.y() ));
        } catch (ModelExecutionException e) {
            System.out.println("ERRORE");
        }
    }

}
