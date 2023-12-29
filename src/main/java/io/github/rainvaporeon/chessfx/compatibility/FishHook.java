package io.github.rainvaporeon.chessfx.compatibility;

import java.lang.reflect.Field;

/**
 * Interface for interacting with the Fish implementation
 */
public interface FishHook {
    FishHook INSTANCE = LocalRegistry.registerFish();

    // Gets the name compatible for local resources for a given piece
    String getCompatiblePieceName(int piece);

    // Plays this notation in the current board
    boolean boardPlayMove(String notation);

    // Plays this location in the current board
    boolean boardPlayMove(int sourceX, int sourceY, int targetX, int targetY);

    // Loads this FEN into the active board
    void boardLoadFEN(String fen);

    // Initializes the default board
    void boardInitialize();

    String getPieceName(int piece);

    int[] getPossibleMoves(int location);

    // Gets a piece at location with the board
    int getPieceAt(int x, int y);

    int getPieceAt(int location);

    int PROGRESS  = 0;
    int CHECKMATE = 1;
    int STALEMATE = 2;
    int MOVE_50   = 4;
    int getBoardState();

}
