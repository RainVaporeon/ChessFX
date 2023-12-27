package io.github.rainvaporeon.chessfx.handlers;

import com.spiritlight.fishutils.logging.Loggers;
import com.spiritlight.fishutils.utils.eventbus.events.EventBusSubscriber;
import io.github.rainvaporeon.chessfx.events.ClickEvent;
import io.github.rainvaporeon.chessfx.events.bus.FXEventBus;
import io.github.rainvaporeon.chessfx.game.helper.GridHelper;

import static java.lang.StringTemplate.STR;

public class EventHandler {
    static {
        FXEventBus.INSTANCE.subscribe(EventHandler.class);
    }

    public static void init() { /* Loads this class if not already loaded */ }

    @EventBusSubscriber
    public static void onClickAction(ClickEvent event) {
        Loggers.getThreadLogger().debug(event.toString());
        Loggers.getThreadLogger().debug(STR."Determined click position=\{parseLocation(GridHelper.getX(event.getContext().x()) + 8 * GridHelper.getY(event.getContext().y()))}");

    }

    public static String parseLocation(int src) {
        return STR."\{(char) ((src % 8) + 'a')}\{(char) ((src / 8) + '1')}";
    }

}
