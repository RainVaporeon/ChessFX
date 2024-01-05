package io.github.rainvaporeon.chessfx.utils;

import io.github.rainvaporeon.chess.fish.game.Piece;
import io.github.rainvaporeon.chess.fish.game.utils.board.BoardHelper;
import com.spiritlight.fishutils.logging.Loggers;
import io.github.rainvaporeon.chessfx.compatibility.FishHook;
import io.github.rainvaporeon.chessfx.compatibility.LocalRegistry;
import io.github.rainvaporeon.chessfx.game.helper.GridHelper;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;

import static io.github.rainvaporeon.chess.fish.game.Piece.NONE;
import static io.github.rainvaporeon.chess.fish.game.Piece.PAWN;

public class GridPanes {

    public static GridPane getChessBoard() {
        GridPane pane = new GridPane();

        // Create 64 rectangles and add to pane
        int count = 0;
        double s = 64; // side of rectangle
        for (int i = 0; i < 8; i++) {
            count++;
            for (int j = 0; j < 8; j++) {
                Rectangle r = new Rectangle(s, s, s, s);
                if (count % 2 == 0) {
                    r.setFill(Color.DARKOLIVEGREEN);
                } else {
                    r.setFill(Color.ANTIQUEWHITE);
                }
                pane.add(r, j, i);
                count++;
            }
        }
        return pane;
    }

    // Priority: Highest
    public static GridPane getPieceOverlay() {
        GridPane pane = new GridPane();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // per Fish implementation: Invert Y
                int piece = FishHook.INSTANCE.getPieceAt(i, 7 - j);
                ImageView view = Images.getImageView(FishHook.INSTANCE.getCompatiblePieceName(piece));
                pane.add(view, i, j);
            }
        }

        return pane;
    }

    // Priority: 2nd highest
    public static GridPane getSelectionOverlayPane() {
        if(SharedElements.selectedX == -1) return null;
        int piece = FishHook.INSTANCE.getPieceAt(SharedElements.selectedX, SharedElements.selectedY);

        GridPane pane = new GridPane();
        int[] possibleMoves = FishHook.INSTANCE.getPossibleMoves(SharedElements.selectedX + 8 * SharedElements.selectedY);

        int checkedSquare = FishHook.INSTANCE.getCheckedSquare();
        if(checkedSquare != -1) {
            Rectangle rect = new Rectangle(64, 64, 64, 64);
            rect.setFill(GridHelper.isDarkSquare(BoardHelper.getFile(checkedSquare), 7 - BoardHelper.getRank(checkedSquare)) ? Color.ORANGERED : Color.LIGHTCORAL);
            pane.add(rect, BoardHelper.getFile(checkedSquare), 7 - BoardHelper.getRank(checkedSquare));
            ChessFXLogger.getLogger().debug(STR."Placed check sign at \{BoardHelper.getFile(checkedSquare)}, \{BoardHelper.getRank(checkedSquare)}");
        }

        if(!Piece.is(piece, NONE)) {
            Rectangle rect = new Rectangle(64, 64, 64, 64);
            rect.setFill(GridHelper.isDarkSquare(SharedElements.selectedX, 7 - SharedElements.selectedY) ? Color.DARKKHAKI : Color.BURLYWOOD);
            pane.add(rect, SharedElements.selectedX, 7 - SharedElements.selectedY);
            ChessFXLogger.getLogger().debug(STR."Placed colored sign at \{SharedElements.selectedX}, \{SharedElements.selectedY}");
        } else {
            return null;
        }
        ChessFXLogger.getLogger().debug(STR."Found piece \{Piece.asString(piece)}@\{SharedElements.selectedX + 8 * SharedElements.selectedY}");
        ChessFXLogger.getLogger().debug(STR."Can move to \{Arrays.toString(possibleMoves)}");

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                int index = i + (7 - j) * 8;
                for(int value : possibleMoves) {
                    if(index == value) {
                        boolean selectedEnPassant = index == LocalRegistry.getCurrentMap().enPassantSquare();
                        boolean selectedPawn = Piece.is(FishHook.INSTANCE.getPieceAt(SharedElements.getSelectedIndex(false)), PAWN);
                        if(Piece.is(FishHook.INSTANCE.getPieceAt(index), NONE) && !(selectedEnPassant && selectedPawn)) {
                            pane.add(Images.getImageView("dot"), i, j);
                        } else {
                            pane.add(Images.getImageView("capture"), i, j);
                        }
                    }
                }
                pane.add(Images.getImageView("blank"), i, j);
            }
        }
        return pane;
    }

    private static GridPane emptyLayout() {
        GridPane gp = new GridPane();
        for(int i = 0; i < 64; i++) gp.add(Images.getImageView("blank"), i / 8, i % 8);
        return gp;
    }
}
