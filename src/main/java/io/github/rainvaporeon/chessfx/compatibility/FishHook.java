package io.github.rainvaporeon.chessfx.compatibility;

import java.lang.reflect.Field;

/**
 * Interface for interacting with the Fish implementation
 */
public interface FishHook {
    FishHook INSTANCE = null;

    String getCompatiblePieceName(int piece);

    boolean boardPlayMove(String notation);

    boolean boardPlayMove(int x, int y);

    static void registerHook(FishHook hook) {
        try {
            Field f = FishHook.class.getDeclaredField("INSTANCE");
            f.setAccessible(true);
            f.set(null, hook);
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException("Unable to hook: ", ex);
        }
    }
}
