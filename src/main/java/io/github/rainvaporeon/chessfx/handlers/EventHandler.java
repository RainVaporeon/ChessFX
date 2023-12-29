package io.github.rainvaporeon.chessfx.handlers;

import com.spiritlight.fishutils.utils.eventbus.events.EventBusSubscriber;
import io.github.rainvaporeon.chessfx.compatibility.FishHook;
import io.github.rainvaporeon.chessfx.events.ClickEvent;
import io.github.rainvaporeon.chessfx.events.SelectionChangedEvent;
import io.github.rainvaporeon.chessfx.events.bus.FXEventBus;
import io.github.rainvaporeon.chessfx.game.helper.GridHelper;
import io.github.rainvaporeon.chessfx.utils.Board;
import io.github.rainvaporeon.chessfx.utils.ChessFXLogger;
import io.github.rainvaporeon.chessfx.utils.SharedElements;

public class EventHandler {
    static {
        FXEventBus.INSTANCE.subscribe(EventHandler.class);
    }

    public static void init() { /* Loads this class if not already loaded */ }

    @EventBusSubscriber
    public static void onClickAction(ClickEvent event) {
        ChessFXLogger.getLogger().debug(event.toString());
        ChessFXLogger.getLogger().debug(STR."Determined click position=\{parseLocation(GridHelper.getX(event.getContext().x()) + 8 * GridHelper.getY(event.getContext().y()))}");
        ChessFXLogger.getLogger().debug(STR."Click position refers to \{FishHook.INSTANCE.getPieceName(FishHook.INSTANCE.getPieceAt(GridHelper.getX(event.getContext().x()), GridHelper.getY(event.getContext().y())))}");

        SharedElements.selectedX = GridHelper.getX((int) event.getContext().x());
        SharedElements.selectedY = GridHelper.getY((int) event.getContext().y());

        FXEventBus.INSTANCE.fire(new SelectionChangedEvent(SharedElements.selectedX, SharedElements.selectedY));

        ChessFXLogger.getLogger().debug(STR."Selection: \{SharedElements.selectedX}, \{SharedElements.selectedY}");
        Board.update();
    }

    public static String parseLocation(int src) {
        return STR."\{(char) ((src % 8) + 'a')}\{(char) ((src / 8) + '1')}";
    }

}
