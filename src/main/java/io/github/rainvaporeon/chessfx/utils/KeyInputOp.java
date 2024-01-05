package io.github.rainvaporeon.chessfx.utils;

import io.github.rainvaporeon.chess.fish.game.utils.board.BoardMap;
import io.github.rainvaporeon.chessfx.compatibility.FishHook;
import io.github.rainvaporeon.chessfx.compatibility.LocalRegistry;
import javafx.scene.Scene;
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
