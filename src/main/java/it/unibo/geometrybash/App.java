package it.unibo.geometrybash;

import it.unibo.geometrybash.controller.menu.MainMenuController;
import it.unibo.geometrybash.view.menu.MainMenuView;
import it.unibo.geometrybash.view.menu.MainMenuViewImpl;


public final class App {
    
    private App(){}

    public static void main(final String[] args) {
        final MainMenuView view = new MainMenuViewImpl();
        final MainMenuController controller = new MainMenuController(view);
        controller.start();
    }
}
