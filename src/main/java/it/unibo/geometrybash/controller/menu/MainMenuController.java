package it.unibo.geometrybash.controller.menu;

import it.unibo.geometrybash.commons.audio.AudioPlayer;
import it.unibo.geometrybash.commons.audio.AudioPlayerImpl;
import it.unibo.geometrybash.view.menu.MainMenuView;
import it.unibo.geometrybash.view.menu.MainMenuViewObserver;
import it.unibo.geometrybash.commons.io.ResourceLoader;

public class MainMenuController implements MainMenuViewObserver {

    private final MainMenuView view;
    private final AudioPlayer menuMusic;

    public MainMenuController(final MainMenuView view) {
        this.view = view;
        this.view.setObserver(this);
        this.menuMusic = new AudioPlayerImpl("/audio/menu.wav");
        this.menuMusic.playLoop();
    }

    public void start() {
        this.view.show();
        this.mainMessage();
    }

    @Override
    public void onCommand(final String command) {
        // Here you can handle the command received from the view
        final String cmd = command.trim().toLowerCase();
        switch (cmd) {
            case "/start":
                this.view.showMessage("geometrybash@oop24:~# " + cmd);
                this.view.showMessage("Starting the game...");
                // Add logic to start the game
                break;
            case "/options":
                this.view.showMessage("geometrybash@oop24:~# " + cmd);
                this.view.showMessage("Opening options...");
                // Add logic to open options
                break;
            case "/exit":
                this.view.showMessage("geometrybash@oop24:~# " + cmd);
                this.view.showMessage("Exiting the application...");
                delayed(500, () -> {
                    this.menuMusic.stop();
                    this.view.close();
                    System.exit(0);
                });
                
                break;
            case "/help":
                this.view.showMessage("geometrybash@oop24:~# " + cmd);
                this.printCommands();
                break;
            case "/credits":
                this.view.showMessage("geometrybash@oop24:~# " + cmd);
                this.printCredits();
                break;
            case "/clear":
                this.view.showMessage("geometrybash@oop24:~# " + cmd);
                delayed(200, () -> {
                    this.view.clear();
                    this.mainMessage();
                });
                
                break;
            default:
                this.view.showMessage("geometrybash@oop24:~# " + cmd);
                this.view.showMessage("Command \'" + cmd + "\' not found");
        }
    }
    
     private void mainMessage() {
        final String title = ResourceLoader.loadText("/ascii/title.txt");

        this.view.showMessage("");
        this.view.showMessage(title);
        this.view.showMessage("");
        this.view.showMessage("");

        this.printCommands();
    }
        private void printCommands() {
            this.view.showMessage(" ----------------------------------------------------------------");
            this.view.showMessage("#       Available commands:");
            this.view.showMessage("#       /start - Start the game");
            this.view.showMessage("#       /options - Open options");
            this.view.showMessage("#       /exit - Exit the application");
            this.view.showMessage("#       /help - Show help message");
            this.view.showMessage("#       /clear - Clear the console");
            this.view.showMessage("#       /credits - Show credits");
            this.view.showMessage(" ----------------------------------------------------------------");
        }

        private void printCredits() {
            this.view.showMessage("GeometryBash");
            this.view.showMessage("OOP Project - University of Bologna");
            this.view.showMessage("Developed by:");
            this.view.showMessage("- De Mori");
            this.view.showMessage("- Montalti");
            this.view.showMessage("- Latini");
            this.view.showMessage("- Riccio");
            this.view.showMessage("Music by NCS - NoCopyrightSounds");
        }   

    private void delayed(final int millis, final Runnable action) {
        new javax.swing.Timer(millis, e -> {
            action.run();
            ((javax.swing.Timer) e.getSource()).stop();
        }).start();
    }
}
