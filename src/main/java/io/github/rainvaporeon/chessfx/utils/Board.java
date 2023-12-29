package io.github.rainvaporeon.chessfx.utils;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class Board {

    public static void update() {
        StackPane pane = (StackPane) SharedElements.getStage().getScene().getRoot();
        // Clear everything aside from the main pane itself
        pane.getChildren().clear();
        pane.getChildren().add(GridPanes.getChessBoard());
        GridPane selectOverlay = GridPanes.getSelectionOverlayPane();
        if(selectOverlay != null) {
            pane.getChildren().add(selectOverlay);
        }
        pane.getChildren().add(GridPanes.getPieceOverlay());
    }

}
