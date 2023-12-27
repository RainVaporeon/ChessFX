package io.github.rainvaporeon.chessfx.game.helper;

import com.spiritlight.fishutils.math.Numbers;

public class GridHelper {
    // NOTE: Left-Right = x- to x+
    //       Top-Down = y- to y+

    public static int getX(double axis) {
        return Numbers.clamp((int) axis / 64, 0, 7);
    }

    public static int getY(double axis) {
        return Numbers.clamp((int) (511 - axis) / 64, 0, 7);
    }
}
