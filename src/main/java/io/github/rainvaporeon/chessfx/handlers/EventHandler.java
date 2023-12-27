package io.github.rainvaporeon.chessfx.handlers;

import com.spiritlight.fishutils.logging.Loggers;
import com.spiritlight.fishutils.utils.eventbus.EventBus;
import com.spiritlight.fishutils.utils.eventbus.events.EventBusSubscriber;
import io.github.rainvaporeon.chessfx.events.ClickEvent;

public class EventHandler {
    static {
        EventBus.INSTANCE.subscribe(EventHandler.class);
    }

    public static void init() { /* Loads this class if not already loaded */ }

    @EventBusSubscriber
    public static void onClickAction(ClickEvent event) {
        Loggers.getThreadLogger().debug(event.toString());
    }
}
