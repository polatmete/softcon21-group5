package ch.uzh.softcon.one;

import ch.uzh.softcon.one.abstraction.GameHandling;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) { // Setup and initialize game
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        GameHandling.initialize(stage);
    }
}