package io.github.rainvaporeon.chessfx.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

public class Images {

    public static ImageView getImageView(String image) {
        InputStream stream = Images.class.getResourceAsStream(STR."/pieces/64/\{image}.png");
        if(stream == null) {
            InputStream empty = Images.class.getResourceAsStream("/pieces/64/blank.png");
            Image img = new Image(empty);
            ImageView view = new ImageView(img);
            view.resize(64, 64);
            return view;
        }
        Image img = new Image(stream);
        final ImageView imageView = new ImageView(img);
        imageView.resize(64, 64);
        return imageView;
    }
}
