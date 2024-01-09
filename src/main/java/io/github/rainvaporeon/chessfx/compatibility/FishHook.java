package io.github.rainvaporeon.chessfx.compatibility;

import java.lang.reflect.Field;
import java.util.function.Consumer;

/**
 * Interface for interacting with the Fish implementation
 */
public interface FishHook {
    /**
     * The hook instance, all communications with Fish should be
     * done via this interface instance.
     */
    FishHook INSTANCE = LocalRegistry.registerFish();

    // Gets the name compatible for local resources for a given piece

    /**
     * Fetches the compatible piece name to be loaded from the resources
     * @param piece the piece
     * @return the name for corresponding asset, or "blank" if a piece
     * does not exist (either none or not found)
     */
    String getCompatiblePieceName(int piece);

    /**
     * Plays this move on the board
     * @param notation the notation
     * @return whether the move was made
     */
    // Plays this notation in the current board
    boolean boardPlayMove(String notation);

    /**
     * Plays this move on the board
     * @param from from position
     * @param to to position
     * @return whether the move was made
     */
    boolean boardPlayMove(int from, int to);

    /**
     * Plays this move on the board
     * @param sourceX the source X
     * @param sourceY the source Y
     * @param targetX the target X
     * @param targetY the target Y
     * @return whether the move was made
     * @apiNote this is equal to calling
     * {@code boardPlayMove(sourceX + 8 * sourceY, targetX + 8 * targetY)}.
     */
    boolean boardPlayMove(int sourceX, int sourceY, int targetX, int targetY);

    /**
     * Loads the FEN string into the active board
     * @param fen the FEN string
     * @throws IllegalArgumentException if the FEN string
     * is not correct
     */
    // Loads this FEN into the active board
    void boardLoadFEN(String fen);

    /**
     * Initializes this board, loading the default
     * state
     */
    // Initializes the default board
    void boardInitialize();

    /**
     * Gets the string representation of a piece
     * @param piece the piece
     * @return the string representation of a piece
     */
    String getPieceName(int piece);

    /**
     * Gets all possible moves from this location
     * @param location the piece to check
     * @return an array of int denoting the locations
     * this location can move to
     */
    int[] getPossibleMoves(int location);

    /**
     * Gets the piece on this coordinate
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the piece, or {@link io.github.rainvaporeon.chess.fish.game.Piece#NONE} if none
     */
    // Gets a piece at location with the board
    int getPieceAt(int x, int y);

    /**
     * Gets the piece on this location
     * @param location the location
     * @return the piece, or {@link io.github.rainvaporeon.chess.fish.game.Piece#NONE} if none
     */
    int getPieceAt(int location);

    /**
     * Gets the king position from the specified side
     * @param side the side
     * @return the king position, -1 if not found or invalid side
     */
    int getKingPosition(int side);

    /**
     * Gets the checked square
     * @return the checked square, or -1 if none
     */
    int getCheckedSquare();

    /**
     * Checks whether any side is in check
     * @return true if any side is in check
     */
    boolean anySideInCheck();

    /**
     * Validates this FEN string
     * @param fen the fen string
     * @return true if {@link FishHook#boardLoadFEN(String)} will
     * not throw an exception with this string as parameter.
     */
    boolean validateFENString(String fen);

    /**
     * Checks whether {@link FishHook#unmakeMove()} is supported
     * @return {@code true} if {@link FishHook#unmakeMove()} throws
     * an exception.
     */
    boolean supportsMoveUnmaking();

    /**
     * Unmakes the latest valid move on the board
     */
    void unmakeMove();

    // All possible outcomes
    int PROGRESS  = 0;
    int CHECKMATE = 1;
    int STALEMATE = 2;
    int MOVE_50   = 4;

    /**
     * Gets the board state
     * @return the board state, see {@link FishHook} for possible outcomes.
     */
    int getBoardState();

    boolean supportsConsoleCommand();

    void appendConsoleCommand(String key, String description, Consumer<String> consumer);

}
