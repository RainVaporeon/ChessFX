package io.github.rainvaporeon.chessfx.game.helper;

import com.spiritlight.fishutils.math.Numbers;

/**
 * A collection of methods to assist interactions from/to conventional
 * 8x8 grid to/from a 512x512 display grid
 */
public class GridHelper {
    // NOTE: Left-Right = x- to x+
    //       Top-Down = y- to y+

    public static int getX(double axis) {
        return Numbers.clamp((int) axis / 64, 0, 7);
    }

    public static int getY(double axis) {
        return Numbers.clamp((int) (511 - axis) / 64, 0, 7);
    }

    public static int getGridX(int value) {
        return value * 64;
    }

    public static int getGridY(int value) {
        return (8 - value) * 64;
    }

    public static boolean isDarkSquare(int x, int y) {
        return (x + y) % 2 == 0; // Only if the x + y is divisible by two
    }
}
