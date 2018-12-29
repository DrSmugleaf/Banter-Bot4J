package com.github.drsmugleaf.tak.bot.minimax;

import com.github.drsmugleaf.tak.board.Board;
import com.github.drsmugleaf.tak.board.Coordinates;
import com.github.drsmugleaf.tak.board.Row;
import com.github.drsmugleaf.tak.board.Square;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Piece;
import com.github.drsmugleaf.tak.pieces.Type;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrSmugleaf on 26/12/2018
 */
public class Node {

    @NotNull
    private final Board BOARD;
    private final int SCORE;

    @NotNull
    private final Color NEXT_COLOR;

    @NotNull
    private final List<Node> CHILDREN = new ArrayList<>();

    @Nullable
    private Node BEST_CHILD;

    @Nullable
    private Coordinates COORDINATES;

    protected Node(@NotNull Board board, @NotNull Color nextColor) {
        BOARD = board;
        SCORE = computeScore();
        NEXT_COLOR = nextColor;
    }

    @Nullable
    public Node getBestChild() {
        return BEST_CHILD;
    }

    @Nullable
    public Coordinates getCoordinates() {
        return COORDINATES;
    }

    public int getScore(@NotNull Color color) {
        switch (color) {
            case BLACK:
                return SCORE;
            case WHITE:
                return -SCORE;
            default:
                throw new IllegalArgumentException("Unrecognized color: " + color);
        }
    }

    @NotNull
    public List<Node> getChildren() {
        return CHILDREN;
    }

    @NotNull
    private List<Node> getAllPossible() {
        List<Node> allBoards = new ArrayList<>();

        Row[] rows = BOARD.getRows();
        for (int i = 0; i < rows.length; i++) {
            Row row = rows[i];
            for (int j = 0; j < row.getSquares().length; j++) {
                Board copy = BOARD.copy();

                Piece piece = new Piece(NEXT_COLOR, Type.FLAT_STONE);
                if (!copy.canPlace(piece.getType(), i, j)) {
                    continue;
                }

                copy.place(piece, i, j);

                Node node = new Node(copy, NEXT_COLOR.getOpposite());
                node.COORDINATES = new Coordinates(i, j);
                allBoards.add(node);
            }
        }

        return allBoards;
    }

    private int computeScore() {
        Color winner = BOARD.hasRoad();
        if (winner != null) {
            switch (winner) {
                case BLACK:
                    return Integer.MAX_VALUE;
                case WHITE:
                    return Integer.MIN_VALUE;
                default:
                    throw new IllegalArgumentException("Unrecognized color: " + winner);
            }
        }

        int score = 0;

        for (Row row : BOARD.getRows()) {
            for (Square square : row.getSquares()) {
                Piece topPiece = square.getTopPiece();
                if (topPiece == null) {
                    continue;
                }

                Color topColor = topPiece.getColor();
                switch (topColor) {
                    case BLACK:
                        score++;
                        break;
                    case WHITE:
                        score--;
                        break;
                    default:
                        throw new IllegalArgumentException("Unrecognized piece color: " + topColor);
                }
            }
        }

        score += BOARD.countAdjacent(Color.BLACK);
        score -= BOARD.countAdjacent(Color.WHITE);

        return score;
    }

    protected void computeToDepth(int depth, @NotNull Color color) {
        if (depth < 1) {
            return;
        }

        CHILDREN.addAll(getAllPossible());

        for (Node child : CHILDREN) {
            child.computeToDepth(depth - 1, color);

            if (BEST_CHILD == null || BEST_CHILD.getScore(color) < child.getScore(color)) {
                BEST_CHILD = child;
            }
        }
    }

}
