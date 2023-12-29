package io.github.rainvaporeon.chessfx.compatibility;

import com.spiritlight.chess.fish.game.Piece;
import com.spiritlight.chess.fish.game.utils.MoveGenerator;
import com.spiritlight.chess.fish.game.utils.board.BoardMap;
import com.spiritlight.chess.fish.game.utils.game.Move;
import com.spiritlight.fishutils.collections.IntList;

import java.util.Locale;

import static com.spiritlight.chess.fish.game.Piece.WHITE;

public class LocalRegistry {
    private static BoardMap currentMap;

    public static BoardMap getCurrentMap() {
        return currentMap;
    }

    static FishHook registerFish() {
        return new FishHook() {
            @Override
            public String getCompatiblePieceName(int piece) {
                return Piece.asString(piece).toLowerCase(Locale.ROOT).replace(' ', '-');
            }

            @Override
            public boolean boardPlayMove(String notation) {
                return currentMap.update(Move.of(notation)).validate();
            }

            @Override
            public boolean boardPlayMove(int sourceX, int sourceY, int targetX, int targetY) {
                return currentMap.update(Move.of(sourceX + 8 * sourceY, targetX + 8 * targetY)).validate();
            }

            @Override
            public void boardLoadFEN(String fen) {
                currentMap = BoardMap.fromFENString(fen);
            }

            @Override
            public void boardInitialize() {
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
        };
    }
}