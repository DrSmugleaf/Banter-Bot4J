package com.github.drsmugleaf.tak.bot.minimax;

import com.github.drsmugleaf.tak.board.Board;
import com.github.drsmugleaf.tak.board.Row;
import com.github.drsmugleaf.tak.board.Square;
import com.github.drsmugleaf.tak.pieces.Color;
import com.github.drsmugleaf.tak.pieces.Piece;
import com.github.drsmugleaf.tak.pieces.Type;
import org.jetbrains.annotations.NotNull;

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

    protected Node(@NotNull Board board, @NotNull Color nextColor) {
        BOARD = board;
        SCORE = computeScore();

        NEXT_COLOR = nextColor;
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

        for (Row row : BOARD.getRows()) {
            for (Square square : row.getSquares()) {
                Board copy = BOARD.copy();

                Piece piece = new Piece(NEXT_COLOR, Type.FLAT_STONE);
                square.place(piece);

                Node node = new Node(copy, NEXT_COLOR.getOpposite());
                allBoards.add(node);
            }
        }

        return allBoards;
    }

    private int computeScore() {
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

        return score;
    }

    @NotNull
    protected List<Node> computeToDepth(int depth, @NotNull List<Node> nodes) {
        if (depth < 1) {
            nodes.add(this);
            return nodes;
        }

        CHILDREN.addAll(getAllPossible());
        nodes.addAll(CHILDREN);

        for (Node child : CHILDREN) {
            child.computeToDepth(depth - 1, nodes);
        }

        return nodes;
    }

}
