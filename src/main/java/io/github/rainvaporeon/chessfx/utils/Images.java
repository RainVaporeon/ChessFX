package io.github.rainvaporeon.chessfx.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

public class Images {

    public static ImageView getImageView(String image) {
        InputStream stream = Images.class.getResourceAsStream(STR."/pieces/64/\{image}.png");
        if(stream == null) return null;
        Image img = new Image(stream);
        final ImageView imageView = new ImageView(img);
        imageView.resize(64, 64);
        return imageView;
    }
}
