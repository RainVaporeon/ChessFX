package io.github.rainvaporeon.chessfx;

import io.github.rainvaporeon.chessfx.compatibility.FishHook;
import io.github.rainvaporeon.chessfx.events.BaseEventRegistrar;
import io.github.rainvaporeon.chessfx.handlers.EventHandler;
import io.github.rainvaporeon.chessfx.utils.GridPanes;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChessFX extends Application {
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        ChessFX.stage = stage;
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
        FishHook.INSTANCE.boardInitialize();
    }

    public static Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch();
    }
}