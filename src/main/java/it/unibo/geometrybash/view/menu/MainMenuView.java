package it.unibo.geometrybash.view.menu;

public interface MainMenuView {

    void show();

    void close();

    void clear();

    void setObserver(MainMenuViewObserver observer);

    void showMessage(String message);



}
