package io.github.rainvaporeon.chessfx.utils;

import io.github.rainvaporeon.chessfx.compatibility.FishHook;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.util.Objects;

public class Images {
    public static Image getImage(int piece) {
        InputStream is = Objects.requireNonNull(Image.class.getResourceAsStream(STR."pieces/\{FishHook.INSTANCE.getCompatiblePieceName(piece)}.png"));
        return new Image(is);
    }

    public static ImageView getResource(String image) {
        InputStream stream = Objects.requireNonNull(Images.class.getResourceAsStream(STR."pieces/\{image}.png"));
        Image img = new Image(stream);
        return new ImageView(img);
    }
}
