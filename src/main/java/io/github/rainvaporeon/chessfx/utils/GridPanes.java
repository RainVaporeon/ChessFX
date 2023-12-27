package io.github.rainvaporeon.chessfx.utils;

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
                if (count % 2 == 0) {
                    r.setFill(Color.DARKOLIVEGREEN);
                } else {
                    r.setFill(Color.ANTIQUEWHITE);
                }
                pane.add(r, j, i);
                count++;
            }
        }


        CHESS_LAYOUT = pane;
    }
}
