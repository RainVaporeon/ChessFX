package io.github.rainvaporeon.chessfx.utils;

import io.github.rainvaporeon.chess.fish.game.utils.MoveGenerator;
import io.github.rainvaporeon.chessfx.async.AsyncTaskThread;
import io.github.rainvaporeon.chessfx.compatibility.FishHook;
import io.github.rainvaporeon.chessfx.compatibility.LocalRegistry;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class Board {

    public static void update() {
        StackPane pane = (StackPane) SharedElements.getStage().getScene().getRoot();
        // Clear everything aside from the main pane itself
        pane.getChildren().removeIf(node -> !(node instanceof MenuBar));
        pane.getChildren().add(GridPanes.getChessBoard());
        GridPane selectOverlay = GridPanes.getSelectionOverlayPane();
        if(selectOverlay != null) {
            pane.getChildren().add(selectOverlay);
        }
        pane.getChildren().add(GridPanes.getPieceOverlay());

        if(LocalRegistry.getCurrentMap().inCheck()) {
            ChessFXLogger.getLogger().debug("Check detected: Available moves are as follows:");
            ChessFXLogger.getLogger().debug(MoveGenerator.create(LocalRegistry.getCurrentMap()).getAllValidMoves().toString());
        }

        AsyncTaskThread.submitTask(() -> {
            int result = FishHook.INSTANCE.getBoardState();
            switch (result) {
                case FishHook.CHECKMATE -> {
                    ChessFXLogger.getLogger().debug("Checkmate detected");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Game ended by checkmate");
                    Platform.runLater(alert::showAndWait);
                }
                case FishHook.STALEMATE -> {
                    ChessFXLogger.getLogger().debug("Stalemate detected");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Game drawn by stalemate");
                    Platform.runLater(alert::showAndWait);
                }
            }
        });
    }

}
