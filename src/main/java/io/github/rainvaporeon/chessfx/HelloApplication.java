package io.github.rainvaporeon.chessfx;

import io.github.rainvaporeon.chessfx.events.BaseEventRegistrar;
import io.github.rainvaporeon.chessfx.handlers.EventHandler;
import io.github.rainvaporeon.chessfx.utils.GridPanes;
import javafx.application.Application;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        HelloApplication.stage = stage;
        init0();

        Scene scene = new Scene(GridPanes.CHESS_LAYOUT);
        stage.setTitle("ChessFX");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    // name clash with Application#init()V;
    private static void init0() {
        BaseEventRegistrar.INSTANCE.initialize(stage);
        EventHandler.init();
    }

    public static void main(String[] args) {
        launch();
    }
}