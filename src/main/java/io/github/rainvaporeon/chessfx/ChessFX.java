package io.github.rainvaporeon.chessfx;

import io.github.rainvaporeon.chessfx.compatibility.FishHook;
import io.github.rainvaporeon.chessfx.events.BaseEventRegistrar;
import io.github.rainvaporeon.chessfx.handlers.EventHandler;
import io.github.rainvaporeon.chessfx.utils.Board;
import io.github.rainvaporeon.chessfx.utils.GridPanes;
import io.github.rainvaporeon.chessfx.utils.SharedElements;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ChessFX extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        SharedElements.setStage(stage);
        init0();

        StackPane pane = new StackPane(GridPanes.getChessBoard());

        Scene scene = new Scene(pane);
        stage.setTitle("ChessFX");
        stage.setScene(scene);
        stage.setResizable(false);

        Board.update();

        stage.show();
    }

    // name clash with Application#init()V;
    private static void init0() {
        BaseEventRegistrar.INSTANCE.initialize(SharedElements.getStage());
        EventHandler.init();
        FishHook.INSTANCE.boardInitialize();
    }

    public static void main(String[] args) {
        launch();
    }
}