package com.github.drsmugleaf.tak.board.history;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.tak.board.IBoard;

import java.util.List;

/**
 * Created by DrSmugleaf on 16/01/2020
 */
public interface IBoardHistory {

    int getID();
    int addState(IBoard state);
    List<IBoardState> getStates();
    IBoardState getState(int id);
    IBoardState getState();
    IBoardState getPrevious();
    IBoardState toState(int id);
    IBoardState toPrevious();
    IBoardState toPrevious(IBoardState state);
    @Nullable
    IBoardState getNext(IBoardState state);
    void reset(IBoard board);

}
