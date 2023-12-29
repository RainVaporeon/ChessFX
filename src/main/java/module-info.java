module ChessFX {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires FishUtilities;
    requires Fish;

    opens io.github.rainvaporeon.chessfx to javafx.fxml;
    opens io.github.rainvaporeon.chessfx.events to FishUtilities;
    opens io.github.rainvaporeon.chessfx.handlers to FishUtilities;
    opens io.github.rainvaporeon.chessfx.compatibility to Fish;
    exports io.github.rainvaporeon.chessfx;
}