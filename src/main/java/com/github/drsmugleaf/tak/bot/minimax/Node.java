package com.github.drsmugleaf.tak.bot.minimax;

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
    private final Square[][] BOARD;
    private final int SCORE;

    @NotNull
    private final Color NEXT_COLOR;

    @NotNull
    private final List<Node> CHILDREN = new ArrayList<>();

    protected Node(@NotNull Square[][] board, @NotNull Color nextColor) {
        BOARD = board;
        SCORE = computeScore();

        NEXT_COLOR = nextColor;
    }

    public int getScore() {
        return SCORE;
    }

    @NotNull
    public List<Node> getChildren() {
        return CHILDREN;
    }

    @NotNull
    private List<Node> getAllPossible() {
        List<Node> allBoards = new ArrayList<>();

        for (int row = 0; row < BOARD.length; row++) {
            for (int column = 0; column < BOARD[row].length; column++) {
                Square[][] board = BOARD.clone();
                Square square = board[row][column];

                Piece piece = new Piece(NEXT_COLOR, Type.FLAT_STONE);
                square.place(piece);

                Node node = new Node(board, NEXT_COLOR.getOpposite());
                allBoards.add(node);
            }
        }

        return allBoards;
    }

    private int computeScore() {
        int score = 0;

        for (Square[] row : BOARD) {
            for (Square square : row) {
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

    protected void computeToDepth(int depth) {
        if (depth < 1) {
            return;
        }

        CHILDREN.addAll(getAllPossible());

        for (Node child : CHILDREN) {
            child.computeToDepth(depth - 1);
        }
    }

}
