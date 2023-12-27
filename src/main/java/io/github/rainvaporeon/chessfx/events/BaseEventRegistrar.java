package io.github.rainvaporeon.chessfx.events;

import com.spiritlight.fishutils.misc.StableField;
import com.spiritlight.fishutils.utils.eventbus.EventBus;
import io.github.rainvaporeon.chessfx.game.ClickContext;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class BaseEventRegistrar {
    public static final BaseEventRegistrar INSTANCE = new BaseEventRegistrar();

    private BaseEventRegistrar() {}

    private final StableField<Boolean> initialized = new StableField<>(false);

    public void initialize(Stage stage) {
        if(initialized.get()) throw new IllegalStateException("Already initialized");
        initialized.set(true);
        stage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            ClickContext ctx = new ClickContext(event.getButton(), event.getX(), event.getY(), event.getZ());
            ClickEvent ev = new ClickEvent(ctx, ClickEvent.Type.CLICK);
            EventBus.INSTANCE.fire(ev);
        });
        stage.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            ClickContext ctx = new ClickContext(event.getButton(), event.getX(), event.getY(), event.getZ());
            ClickEvent ev = new ClickEvent(ctx, ClickEvent.Type.RELEASE);
            EventBus.INSTANCE.fire(ev);
        });
        stage.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            ClickContext ctx = new ClickContext(event.getButton(), event.getX(), event.getY(), event.getZ());
            ClickEvent ev = new ClickEvent(ctx, ClickEvent.Type.HOLD);
            EventBus.INSTANCE.fire(ev);
        });
    }

}
