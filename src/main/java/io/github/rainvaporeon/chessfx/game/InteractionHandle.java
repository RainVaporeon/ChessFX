package io.github.rainvaporeon.chessfx.game;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Represents a handle that accepts a specific type of event
 * and returns the handler for such event.
 * This class also provides some static handles for generic uses.
 * @param <T> event type
 */
public interface InteractionHandle<T extends Event> {

    EventHandler<T> handle(T event);

    InteractionHandle<MouseEvent> CLICK = event -> {
        return null; // TODO: Impl
    };
}
