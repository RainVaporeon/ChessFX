package io.github.rainvaporeon.chessfx.utils;

import io.github.rainvaporeon.chess.fish.game.utils.board.BoardMap;
import io.github.rainvaporeon.chessfx.compatibility.FishHook;
import io.github.rainvaporeon.chessfx.compatibility.LocalRegistry;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;

import static javafx.scene.input.KeyCode.*;

public record KeyInputOp(KeyCode key, Runnable runnable) {
    public static final KeyInputOp RESET_BOARD = create(R, FishHook.INSTANCE::boardInitialize);
    public static final KeyInputOp UNDO_BOARD = create(LEFT, () -> {
        if(FishHook.INSTANCE.supportsMoveUnmaking()) FishHook.INSTANCE.unmakeMove();
    });
    public static final KeyInputOp REDO_BOARD = create(RIGHT, () -> {
        if(FishHook.INSTANCE.supportsMoveUnmaking()) {
            BoardMap map = LocalRegistry.getRedoQueue().poll();
            if(map == null) return;
            FishHook.INSTANCE.boardLoadFEN(map.toFENString());
        }
    });
    public static final KeyInputOp LOAD_BOARD = create(L, () -> {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Load from FEN");
        dialog.setContentText("Paste the FEN string below:");
        String fen = dialog.showAndWait().orElse(null);
        boolean valid = FishHook.INSTANCE.validateFENString(fen);
        if(!valid) {
            Alert alert = new Alert(Alert.AlertType.WARNING, STR."Invalid FEN String entered: \{fen}");
            alert.setTitle("Operation failed");
            alert.showAndWait();
        } else {
            FishHook.INSTANCE.boardLoadFEN(fen);
        }
    });
    public static final KeyInputOp SAVE_BOARD = create(S, () -> {
        TextInputDialog dialog = new TextInputDialog(LocalRegistry.getCurrentMap().toFENString());
        dialog.setContentText("Copy the FEN string below:");
        dialog.setTitle("Save FEN");
        dialog.showAndWait();
    });

    public static void hook(Scene node, KeyInputOp... ops) {
        node.setOnKeyPressed(key -> {
            for(KeyInputOp op : ops) {
                if(key.getCode() == op.key()) op.runnable.run();
            }
            Board.update();
        });
    }

    private static KeyInputOp create(KeyCode code, Runnable runnable) {
        return new KeyInputOp(code, runnable);
    }
}
