package io.github.rainvaporeon.chessfx;

import io.github.rainvaporeon.chess.fish.internal.InternLogger;
import com.spiritlight.fishutils.utils.noop.io.NoOpPrintStream;
import io.github.rainvaporeon.chessfx.async.AsyncTaskThread;
import io.github.rainvaporeon.chessfx.compatibility.FishHook;
import io.github.rainvaporeon.chessfx.compatibility.LocalRegistry;
import io.github.rainvaporeon.chessfx.events.BaseEventRegistrar;
import io.github.rainvaporeon.chessfx.handlers.ClickHandler;
import io.github.rainvaporeon.chessfx.handlers.EventHandler;
import io.github.rainvaporeon.chessfx.utils.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventTarget;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class ChessFX extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        hookShutdownOp(stage);
        AsyncTaskThread.init();
        AsyncTaskThread.submitTask(() -> System.out.println("Async task thread polled"));

        SharedElements.setStage(stage);
        init0();

        StackPane pane = new StackPane(GridPanes.getChessBoard());

        pane.getChildren().add(ChessFX.loadMenuItems());

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

    // phase 2: register tabs
    private static MenuBar loadMenuItems() {
        MenuItem restart = new MenuItem("Restart game");
        MenuItem loadFEN = new MenuItem("Load from FEN...");
        MenuItem saveFEN = new MenuItem("Save as FEN...");

        Menu menu = new Menu("Board");

        restart.setOnAction(_ -> {
            FishHook.INSTANCE.boardInitialize();
            Board.update();
        });

        loadFEN.setOnAction(_ -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Load from FEN");
            dialog.setContentText("Paste the FEN string below:");
            String fen = dialog.showAndWait().orElse(null);
            boolean valid = FishHook.INSTANCE.validateFENString(fen);
            if(!valid) {
                Alert alert = new Alert(Alert.AlertType.WARNING, STR."Invalid FEN String entered: \{fen}");
                alert.show();
            } else {
                FishHook.INSTANCE.boardLoadFEN(fen);
            }
        });

        saveFEN.setOnAction(_ -> {
            TextInputDialog dialog = new TextInputDialog(LocalRegistry.getCurrentMap().toFENString());
            dialog.setContentText("Copy the FEN string below:");
            dialog.setTitle("Save FEN");
            dialog.show();
        });

        menu.getItems().addAll(restart, loadFEN, saveFEN);

        MenuBar menubar = new MenuBar(menu);

        menubar.setUseSystemMenuBar(true);

        return menubar;
    }

    private static void addKeyInputOp(Scene target) {
        KeyInputOp.hook(target, KeyInputOp.RESET_BOARD, KeyInputOp.UNDO_BOARD, KeyInputOp.REDO_BOARD);
    }

    private static void hookShutdownOp(Stage stage) {
        stage.setOnCloseRequest(_ -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        InternLogger.setEnabled(false);
        launch();
    }
}