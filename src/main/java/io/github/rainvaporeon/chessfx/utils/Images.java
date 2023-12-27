package io.github.rainvaporeon.chessfx.utils;

import javafx.scene.image.ImageView;

public class Images {
    public static final ImageView BLACK_PAWN, WHITE_PAWN,
                                  BLACK_KNIGHT, WHITE_KNIGHT,
                                  BLACK_BISHOP, WHITE_BISHOP,
                                  BLACK_ROOK, WHITE_ROOK,
                                  BLACK_QUEEN, WHITE_QUEEN,
                                  BLACK_KING, WHITE_KING;

    static {
        class Resource {
            ImageView get(String image) {
                return new ImageView(Images.class.getResource("pieces/" + image + ".png").toExternalForm());
            }
        }
        Resource helper = new Resource();

        BLACK_PAWN = helper.get("black-pawn");
        BLACK_KNIGHT = helper.get("black-knight");
        BLACK_BISHOP = helper.get("black-bishop");
        BLACK_ROOK = helper.get("black-rook");
        BLACK_QUEEN = helper.get("black-queen");
        BLACK_KING = helper.get("black-king");
        WHITE_PAWN = helper.get("white-pawn");
        WHITE_KNIGHT = helper.get("white-knight");
        WHITE_BISHOP = helper.get("white-bishop");
        WHITE_ROOK = helper.get("white-rook");
        WHITE_QUEEN = helper.get("white-queen");
        WHITE_KING = helper.get("white-king");
    }
}
