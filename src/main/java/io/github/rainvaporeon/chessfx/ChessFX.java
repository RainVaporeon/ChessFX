package io.github.rainvaporeon.chessfx;

import com.spiritlight.fishutils.utils.noop.io.NoOpPrintStream;
import io.github.rainvaporeon.chess.fish.internal.InternLogger;
import io.github.rainvaporeon.chessfx.async.AsyncTaskThread;
import io.github.rainvaporeon.chessfx.compatibility.FishHook;
import io.github.rainvaporeon.chessfx.compatibility.LocalRegistry;
import io.github.rainvaporeon.chessfx.events.BaseEventRegistrar;
import io.github.rainvaporeon.chessfx.handlers.ClickHandler;
import io.github.rainvaporeon.chessfx.handlers.EventHandler;
import io.github.rainvaporeon.chessfx.utils.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ChessFX extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        hookShutdownOp(stage);
        AsyncTaskThread.init();
        AsyncTaskThread.submitTask(() -> System.out.println("Async task thread polled"));

        SharedElements.setStage(stage);
        init0();

        StackPane pane = new StackPane(GridPanes.getChessBoard());

        Scene scene = new Scene(pane);
        stage.setTitle("ChessFX");
        stage.setScene(scene);
        stage.setResizable(false);

        addKeyInputOp(scene);

        Board.update();

        stage.show();
    }

    // name clash with Application#init()V;
    private static void init0() {
        BaseEventRegistrar.INSTANCE.initialize(SharedElements.getStage());
        EventHandler.init();
        ClickHandler.init();
        FishHook.INSTANCE.boardInitialize();
    }

    private static void addKeyInputOp(Scene target) {
        KeyInputOp.hook(target,
                KeyInputOp.RESET_BOARD,
                KeyInputOp.UNDO_BOARD,
                KeyInputOp.REDO_BOARD,
                KeyInputOp.SAVE_BOARD,
                KeyInputOp.LOAD_BOARD);
    }

    private static void hookShutdownOp(Stage stage) {
        stage.setOnCloseRequest(_ -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        InternLogger.setEnabled(false);
        ChessFXLogger.getLogger().setAllOutputStream(new NoOpPrintStream());
        launch();
    }
}