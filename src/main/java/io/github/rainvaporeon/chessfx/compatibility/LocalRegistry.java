package io.github.rainvaporeon.chessfx.compatibility;

import com.spiritlight.chess.fish.game.FEN;
import com.spiritlight.chess.fish.game.Piece;
import com.spiritlight.chess.fish.game.utils.MoveGenerator;
import com.spiritlight.chess.fish.game.utils.board.BoardMap;
import com.spiritlight.chess.fish.game.utils.game.Move;
import com.spiritlight.fishutils.collections.IntList;

import java.util.ArrayDeque;
import java.util.Locale;
import java.util.Queue;
import java.util.Stack;

import static com.spiritlight.chess.fish.game.Piece.*;

public class LocalRegistry {
    private static BoardMap currentMap;
    private static final Stack<Move> undoStack = new Stack<>();
    private static final Queue<Move> redoQueue = new ArrayDeque<>();

    public static BoardMap getCurrentMap() {
        return currentMap;
    }

    public static Stack<Move> getUndoStack() {
        return undoStack;
    }

    public static Queue<Move> getRedoQueue() {
        return redoQueue;
    }

    static FishHook registerFish() {
        return new FishHook() {
            @Override
            public String getCompatiblePieceName(int piece) {
                if((piece & ~(PIECE_MASK | COLOR_MASK)) != 0) return "blank";
                return Piece.asString(piece).toLowerCase(Locale.ROOT).replace(' ', '-');
            }

            @Override
            public boolean boardPlayMove(String notation) {
                Move move = Move.of(notation);
                boolean success = currentMap.update(move).validate();
                if(success) {
                    undoStack.push(move);
                    return true;
                }
                return false;
            }

            @Override
            public boolean boardPlayMove(int from, int to) {
                Move move = Move.of(from, to);
                boolean success = currentMap.update(move).validate();
                if(success) {
                    undoStack.push(move);
                    return true;
                }
                return false;
            }

            @Override
            public boolean boardPlayMove(int sourceX, int sourceY, int targetX, int targetY) {
                Move move = Move.of(sourceX + 8 * sourceY, targetX + 8 * targetY);
                boolean success = currentMap.update(move).validate();
                if(success) {
                    undoStack.push(move);
                    return true;
                }
                return false;
            }

            @Override
            public void boardLoadFEN(String fen) {
                resetBoard();
                currentMap = BoardMap.fromFENString(fen);
            }

            @Override
            public void boardInitialize() {
                resetBoard();
                currentMap = BoardMap.initialize();
            }

            @Override
            public int getPieceAt(int x, int y) {
                return currentMap.getPieceAt(x + 8 * y);
            }

            @Override
            public int getPieceAt(int location) {
                return currentMap.getPieceAt(location);
            }

            @Override
            public int getKingPosition(int side) {
                BoardMap.BoardItr itr = side == WHITE ? currentMap.itr() : currentMap.getEnemyBoard().itr();
                long layout = 0;
                while(itr.cursorPiece() != KING) {
                    layout = itr.nextLong();
                }
                int ret = Long.numberOfTrailingZeros(layout);
                return ret == 64 ? -1 : ret;
            }

            @Override
            public int getCheckedSquare() {
                if(currentMap.inCheck()) {
                    return getKingPosition(WHITE);
                }
                if(currentMap.getEnemyBoard().inCheck()) {
                    return getKingPosition(BLACK);
                }
                return -1;
            }

            @Override
            public boolean anySideInCheck() {
                return currentMap.inCheck() || currentMap.getEnemyBoard().inCheck();
            }

            @Override
            public int getBoardState() {
                return switch (currentMap.getGameState().getValue()) {
                    case IN_PROGRESS -> FishHook.PROGRESS;
                    case BLACK_WIN_CHECKMATE, WHITE_WIN_CHECKMATE -> FishHook.CHECKMATE;
                    case DRAW_STALEMATE -> FishHook.STALEMATE;
                    case DRAW_50_MOVE -> FishHook.MOVE_50;
                    default -> throw new IllegalArgumentException(STR."unsupported enum: \{currentMap.getGameState().getValue()}");
                };
            }

            @Override
            public boolean validateFENString(String fen) {
                try {
                    FEN.load(fen);
                    return true;
                } catch (Exception ex) {
                    return false;
                }
            }

            @Override
            public boolean supportsMoveUnmaking() {
                return true;
            }

            @Override
            public void unmakeMove() {
                if(undoStack.isEmpty()) return;
                Move move = undoStack.pop();
                currentMap.unmake(move);
                redoQueue.offer(move);
            }

            @Override
            public String getPieceName(int piece) {
                return Piece.asString(piece);
            }

            private static final boolean BIT_MODE = false;
            @Override
            public int[] getPossibleMoves(int location) {
                int color = Piece.color(this.getPieceAt(location));
                if(BIT_MODE) {
                    return MoveGenerator.create(color == WHITE ? currentMap : currentMap.getEnemyBoard())
                            .getValidMovesFor(location)
                            .stream().mapToInt(Move::to)
                            .toArray();
                } else {
                    IntList list = new IntList(8);
                    for(int i = 0; i < 64; i++) {
                        if((color == WHITE ? currentMap : currentMap.getEnemyBoard()).canMove(location, i, true)) list.add(i);
                    }
                    return list.toIntArray();
                }
            }

            private void resetBoard() {
                undoStack.clear();
            }
        };
    }
}
