package io.github.rainvaporeon.chessfx.events.bus;

import com.spiritlight.fishutils.utils.eventbus.EventBus;
import com.spiritlight.fishutils.utils.eventbus.events.Event;
import io.github.rainvaporeon.chessfx.events.ClickEvent;

/**
 * Event bus for JavaFX-related events, separate from the
 * main instance bus, but listens to the main bus anyway.
 * This implementation provides some potentially useful
 * features to grant extra control over (specifically) FX-related
 * events.
 */
public class FXEventBus extends EventBus {
    public static final FXEventBus INSTANCE = new FXEventBus();

    /**
     * The delay before HOLD events is fired, in milliseconds.
     */
    private static long holdDelay = 100;
    private long timeSinceLastClick = 0;

    private FXEventBus() {
        this.hook(EventBus.INSTANCE);
    }

    public static void setHoldDelay(long holdDelay) {
        FXEventBus.holdDelay = holdDelay;
    }

    @Override
    public void fire(Event event) {
        if(event instanceof ClickEvent ce) {
            if(ce.getType() == ClickEvent.Type.CLICK) timeSinceLastClick = System.currentTimeMillis();
            if(ce.getType() == ClickEvent.Type.HOLD) {
                // Drop all HOLD type if a hold is too close to the last CLICK.
                if(System.currentTimeMillis() - timeSinceLastClick < holdDelay) return;
            }
        }
        super.fire(event);
    }
}
