package com.github.drsmugleaf.tak.board.history;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.board.IBoard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by DrSmugleaf on 16/01/2020
 */
public class BoardHistory implements IBoardHistory {

    private int id;
    private final List<IBoardState> STATES;

    public BoardHistory(IBoard board) {
        id = 0;
        STATES = new ArrayList<>();
        IBoardState state = new BoardState(id, board);
        STATES.add(state);
        id = STATES.size() - 1;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public int addState(IBoard board) {
        id++;
        IBoardState state = new BoardState(id, board);
        STATES.add(id, state);
        return id - 1;
    }

    @Override
    public List<IBoardState> getStates() {
        return Collections.unmodifiableList(STATES);
    }

    @Override
    public IBoardState getState(int id) {
        return STATES.get(id);
    }

    @Override
    public IBoardState getState() {
        return getState(getID());
    }

    @Override
    public IBoardState getPrevious() {
        if (STATES.size() == 1) {
            return STATES.get(0);
        }

        return STATES.get(id - 2);
    }

    @Override
    public IBoardState toState(int id) {
        if (id < 0) {
            id = 0;
        }

        IBoardState state = STATES.get(id);
        STATES.removeIf(oldState -> oldState.getID() > state.getID());
        this.id = state.getID();
        return state;
    }

    @Override
    public IBoardState toPrevious() {
        return toPrevious(getState());
    }

    @Override
    public IBoardState toPrevious(IBoardState state) {
        return toState(state.getID() - 1);
    }

    @Nullable
    @Override
    public IBoardState getNext(IBoardState state) {
        int id = state.getID();
        if (id >= STATES.size()) {
            return null;
        }

        return STATES.get(id);
    }

    @Override
    public void reset(IBoard board) {
        id = 0;
        STATES.clear();
        IBoardState state = new BoardState(id, board);
        STATES.add(state);
        id = STATES.size() - 1;
    }

}
