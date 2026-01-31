package it.unibo.geometrybash;

import it.unibo.geometrybash.commons.assets.AssetStore;
import it.unibo.geometrybash.commons.assets.ResourceLoader;
import it.unibo.geometrybash.commons.assets.ResourceLoaderImpl;
import it.unibo.geometrybash.controller.StaticDeltaTimeControllerImpl;
import it.unibo.geometrybash.controller.Controller;
import it.unibo.geometrybash.model.GameModel;
import it.unibo.geometrybash.model.GameModelImpl;
import it.unibo.geometrybash.model.physicsengine.impl.jbox2d.JBox2dPhysicsEngineFactory;
import it.unibo.geometrybash.view.ViewImpl;
import it.unibo.geometrybash.view.View;


public class App {
    public static void main(String[] args) {
        ResourceLoader resourceLoader = new ResourceLoaderImpl();
        AssetStore assetStore = new AssetStore(resourceLoader);
        JBox2dPhysicsEngineFactory physicsEngineFactory = new JBox2dPhysicsEngineFactory();
        View view = new ViewImpl(resourceLoader, assetStore);
        GameModel gameModel = new GameModelImpl(resourceLoader,physicsEngineFactory);
        Controller controller = new StaticDeltaTimeControllerImpl(gameModel, view);
        controller.start();
    }
}
