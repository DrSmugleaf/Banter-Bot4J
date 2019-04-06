package com.github.drsmugleaf.article13;

import org.jetbrains.annotations.NotNull;

public class Votes {

    private int finalVote;
    private int proceduralVote;
    private int pledge2019;

    public Votes() {}

    public int getFinalVote() {
        return finalVote;
    }

    @NotNull
    public Votes addFinalVote() {
        finalVote++;
        return this;
    }

    public int getProceduralVote() {
        return proceduralVote;
    }

    @NotNull
    public Votes addProceduralVote() {
        proceduralVote++;
        return this;
    }

    public int getPledge2019() {
        return pledge2019;
    }

    @NotNull
    public Votes addPledge2019() {
        pledge2019++;
        return this;
    }

    @Override
    public String toString() {
        return finalVote + " final, " + proceduralVote + " procedural, " + pledge2019 + " pledge2019.";
    }

}
