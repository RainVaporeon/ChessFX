package io.github.rainvaporeon.chessfx.handlers;

import io.github.rainvaporeon.chess.fish.game.Piece;
import com.spiritlight.fishutils.utils.eventbus.events.EventBusSubscriber;
import io.github.rainvaporeon.chessfx.compatibility.FishHook;
import io.github.rainvaporeon.chessfx.events.SelectionChangedEvent;
import io.github.rainvaporeon.chessfx.events.bus.FXEventBus;
import io.github.rainvaporeon.chessfx.utils.SharedElements;

public class ClickHandler {
    private int previousX = -1;
    private int previousY = -1;

    static {
        FXEventBus.INSTANCE.subscribe(new ClickHandler());
    }

    public static void init() {}

    @EventBusSubscriber
    private void handleClick(SelectionChangedEvent event) {
        int x = event.getX();
        int y = event.getY();
        int prevX = previousX;
        int prevY = previousY;
        previousX = x;
        previousY = y;
        if(prevX == -1 || prevY == -1) return;

        int sourcePosition = prevX + 8 * prevY;
        int targetPosition = x + 8 * y;

        if(Piece.is(FishHook.INSTANCE.getPieceAt(sourcePosition), Piece.NONE)) return;

        int[] targets = FishHook.INSTANCE.getPossibleMoves(sourcePosition);

        for(int value : targets) {
            if(targetPosition == value) {
                boolean success = FishHook.INSTANCE.boardPlayMove(sourcePosition, targetPosition);
                if(!success) resetClicks();
            }
        }
    }

    public static void resetClicks() {
        SharedElements.selectedX = -1;
        SharedElements.selectedY = -1;
        SelectionChangedEvent event = new SelectionChangedEvent(-1, -1);
        FXEventBus.INSTANCE.fire(event);

    }
}
