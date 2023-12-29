package io.github.rainvaporeon.chessfx.utils;

import com.spiritlight.fishutils.misc.StableField;
import javafx.stage.Stage;

public class SharedElements {
    private static final StableField<Stage> stage = new StableField<>();

    public static int selectedX = -1;
    public static int selectedY = -1;

    public static Stage getStage() {
        return stage.get();
    }

    public static void setStage(Stage stage) {
        SharedElements.stage.set(stage);
    }
}
