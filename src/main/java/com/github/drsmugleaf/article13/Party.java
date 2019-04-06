package com.github.drsmugleaf.article13;

import org.jetbrains.annotations.NotNull;

public class Party {

    @NotNull
    private final String NAME;

    @NotNull
    public final Votes FOR;

    @NotNull
    public final Votes AGAINST;

    @NotNull
    public final Votes ABSTAINED;

    @NotNull
    public final Votes ABSENT;

    public Party(@NotNull String name) {
        NAME = name;
        FOR = new Votes();
        AGAINST = new Votes();
        ABSTAINED = new Votes();
        ABSENT = new Votes();
    }

    @NotNull
    public String getName() {
        return NAME;
    }

    @NotNull
    public Party parseFinalVote(@NotNull String vote) {
        if (vote.contains("FOR")) {
            FOR.addFinalVote();
        } else if (vote.contains("AGAINST")) {
            AGAINST.addFinalVote();
        } else if (vote.contains("ABSTAINED")) {
            ABSTAINED.addFinalVote();
        } else if (vote.contains("NOT PRESENT")) {
            ABSENT.addFinalVote();
        } else {
            throw new IllegalArgumentException("Invalid vote value: " + vote);
        }

        return this;
    }

    @NotNull
    public Party parseProceduralVote(@NotNull String vote) {
        if (vote.contains("FOR")) {
            FOR.addProceduralVote();
        } else if (vote.contains("AGAINST")) {
            AGAINST.addProceduralVote();
        } else if (vote.contains("ABSTAINED")) {
            ABSTAINED.addProceduralVote();
        } else if (vote.contains("NOT PRESENT")) {
            ABSENT.addProceduralVote();
        } else {
            throw new IllegalArgumentException("Invalid vote value: " + vote);
        }

        return this;
    }

    @NotNull
    public Party parsePledge2019(@NotNull String vote) {
        if (vote.contains("YES")) {
            FOR.addPledge2019();
        } else if (vote.contains("-")) {
            AGAINST.addPledge2019();
        } else {
            throw new IllegalArgumentException("Invalid vote value: " + vote);
        }

        return this;
    }

    @NotNull
    @Override
    public String toString() {
        return "Party: " +
                getName() +
                "\n" +
                "For: " +
                FOR +
                "\n" +
                "Against: " +
                AGAINST +
                "\n" +
                "Abstained: " +
                ABSTAINED +
                "\n" +
                "Absent: " +
                ABSENT +
                "\n";
    }

    @NotNull
    public String toSimpleString() {
        return "**Party: " +
                getName() +
                "**\n" +
                "Implement Article 13: " +
                FOR.getFinalVote() +
                "\nRepeal Article 13: " +
                AGAINST.getFinalVote() +
                "\nAbstained: " +
                ABSTAINED.getFinalVote() +
                "\nNot present: " +
                ABSENT.getFinalVote();
    }

}
