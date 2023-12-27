package io.github.rainvaporeon.chessfx.game;

import javafx.scene.input.MouseButton;

public record ClickContext(MouseButton button, double x, double y, double z) {
}
