package io.github.rainvaporeon.chessfx.events;

import com.spiritlight.fishutils.utils.eventbus.events.Event;
import io.github.rainvaporeon.chessfx.game.ClickContext;

public class ClickEvent extends Event {
    private final ClickContext context;
    private final Type type;
    public ClickEvent(ClickContext context, Type type) {
        this.context = context;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public ClickContext getContext() {
        return context;
    }

    @Override
    public String toString() {
        return type + "@" + context.toString();
    }

    public enum Type {
        CLICK,
        RELEASE,
        HOLD
    }
}
