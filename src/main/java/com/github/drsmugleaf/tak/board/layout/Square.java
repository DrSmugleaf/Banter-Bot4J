package com.github.drsmugleaf.tak.board.layout;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.board.action.IMove;
import com.github.drsmugleaf.tak.pieces.IColor;
import com.github.drsmugleaf.tak.pieces.IPiece;
import com.github.drsmugleaf.tak.pieces.IType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by DrSmugleaf on 01/12/2018
 */
public class Square implements ISquare {

    private final List<IPiece> PIECES = new ArrayList<>();
    private final int ROW;
    private final int COLUMN;

    public Square(int row, int column) {
        ROW = row;
        COLUMN = column;
    }

    private Square(ISquare square) {
        this(square.getRow(), square.getColumn());

        for (IPiece piece : square.getPieces()) {
            getPieces().add(piece.copy());
        }
    }

    public static ISquare createCustom(int row, int column, IPiece... pieces) {
        ISquare square = new Square(row, column);
        Collections.addAll(square.getPieces(), pieces);
        return square;
    }

    @Override
    public ISquare copy() {
        return new Square(this);
    }

    @Override
    public int getRow() {
        return ROW;
    }

    @Override
    public int getColumn() {
        return COLUMN;
    }

    @Override
    public List<IPiece> getPieces() {
        return PIECES;
    }

    @Nullable
    @Override
    public IColor getColor() {
        if (getTopPiece() == null) {
            return null;
        }

        return getTopPiece().getColor();
    }

    @Nullable
    @Override
    public IType getType() {
        if (getTopPiece() == null) {
            return null;
        }

        return getTopPiece().getType();
    }

    @Nullable
    @Override
    public IPiece getTopPiece() {
        List<IPiece> pieces = getPieces();
        if (pieces.isEmpty()) {
            return null;
        }

        return pieces.get(pieces.size() - 1);
    }

    @Override
    public boolean canMove(IMove move, ISquare destination) {
        int amount = move.getAmount();
        if (amount <= 0 || amount > getPieces().size()) {
            return false;
        }

        IPiece thisTopPiece = getTopPiece();
        if (thisTopPiece == null) {
            return false;
        }

        IPiece otherTopPiece = destination.getTopPiece();
        if (otherTopPiece == null) {
            return true;
        }

        if (amount == 1) {
            return thisTopPiece.getType().canMoveTo(otherTopPiece);
        } else {
            return !otherTopPiece.getType().blocks();
        }
    }

    @Override
    public ISquare move(IMove move, ISquare destination, boolean silent) {
        List<IPiece> pieces = getPieces();
        ListIterator<IPiece> iterator = pieces.listIterator(pieces.size());
        int amount = move.getAmount();
        while (iterator.hasPrevious() && amount > 0) {
            IPiece piece = iterator.previous();

            piece.getType().move(destination, amount);
            iterator.remove();
            destination.getPieces().add(piece);

            amount--;
        }

        if (!silent) {
            onUpdate();
            destination.onUpdate();
        }

        return this;
    }

    @Override
    public boolean canPlace() {
        return getTopPiece() == null;
    }

    @Override
    public ISquare place(IPiece piece, boolean silent) {
        getPieces().add(piece);

        if (!silent) {
            onUpdate();
        }

        return this;
    }

    @Override
    public ISquare remove(IPiece piece, boolean silent) {
        getPieces().remove(piece);

        if (!silent) {
            onUpdate();
        }

        return this;
    }

    @Override
    public boolean connectsTo(@Nullable IPiece piece) {
        IPiece topPiece = getTopPiece();
        if (piece == null || topPiece == null) {
            return false;
        }

        return getColor() == piece.getColor() && topPiece.getType().formsRoad() && piece.getType().formsRoad();
    }

    @Override
    public boolean connectsTo(@Nullable ISquare square) {
        if (square == null) {
            return false;
        }

        return connectsTo(square.getTopPiece());
    }

    @Override
    public void onUpdate() {}

    @Override
    public void reset() {
        getPieces().clear();
    }

    @Override
    public double[] toDoubleArray(int totalPieces) {
        double[] array = new double[totalPieces];
        for (int i = 0; i < getPieces().size(); i++) {
            array[i] = getPieces().get(i).toDouble();
        }

        return array;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        List<IPiece> pieces = getPieces();

        for (int i = 0; i < pieces.size(); i++) {
            IPiece piece = pieces.get(i);
            builder.append(piece);

            if (i < pieces.size() - 1) {
                builder.append(" ");
            }
        }

        return builder.toString();
    }

}
