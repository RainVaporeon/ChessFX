package io.github.rainvaporeon.chessfx.utils;

import com.spiritlight.chess.fish.game.Piece;
import io.github.rainvaporeon.chessfx.compatibility.FishHook;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GridPanes {
    public static final GridPane CHESS_LAYOUT;

    static {
        GridPane pane = new GridPane();

        // Create 64 rectangles and add to pane
        int count = 0;
        double s = 64; // side of rectangle
        for (int i = 0; i < 8; i++) {
            count++;
            for (int j = 0; j < 8; j++) {
                Rectangle r = new Rectangle(s, s, s, s);
                // per Fish specification, the top rows are back-most.
                int piece = FishHook.INSTANCE.getPieceAt(j, 7 - i);
                ImageView view = Images.getImageView(FishHook.INSTANCE.getCompatiblePieceName(piece));
                if (count % 2 == 0) {
                    r.setFill(Color.DARKOLIVEGREEN);
                } else {
                    r.setFill(Color.ANTIQUEWHITE);
                }
                pane.add(view == null ? r : view, j, i);
                count++;
            }
        }

        CHESS_LAYOUT = pane;
    }
}
