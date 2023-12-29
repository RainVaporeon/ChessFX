package io.github.rainvaporeon.chessfx.utils;

import com.spiritlight.fishutils.logging.Logger;

public class ChessFXLogger {
    private static final Logger logger;

    static {
        logger = new Logger("ChessFX/Game");
        logger.configured();
    }

    public static Logger getLogger() {
        return logger;
    }
}
