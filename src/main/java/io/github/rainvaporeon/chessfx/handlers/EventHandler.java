package io.github.rainvaporeon.chessfx.handlers;

import com.spiritlight.fishutils.logging.Loggers;
import com.spiritlight.fishutils.utils.eventbus.events.EventBusSubscriber;
import io.github.rainvaporeon.chessfx.compatibility.FishHook;
import io.github.rainvaporeon.chessfx.events.ClickEvent;
import io.github.rainvaporeon.chessfx.events.bus.FXEventBus;
import io.github.rainvaporeon.chessfx.game.helper.GridHelper;
import io.github.rainvaporeon.chessfx.utils.SharedElements;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

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
        Loggers.getThreadLogger().debug(STR."Click position refers to \{FishHook.INSTANCE.getPieceAt(GridHelper.getX(event.getContext().x()), GridHelper.getY(event.getContext().y()))}");

        SharedElements.selectedX = GridHelper.getGridX((int) event.getContext().x());
        SharedElements.selectedY = GridHelper.getGridY((int) event.getContext().y());
    }

    public static String parseLocation(int src) {
        return STR."\{(char) ((src % 8) + 'a')}\{(char) ((src / 8) + '1')}";
    }

}
