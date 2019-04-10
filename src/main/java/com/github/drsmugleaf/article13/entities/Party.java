package com.github.drsmugleaf.article13.entities;

import com.github.drsmugleaf.article13.vote.Decision;
import com.github.drsmugleaf.article13.vote.Result;
import com.github.drsmugleaf.article13.vote.Vote;
import com.github.drsmugleaf.article13.vote.Votes;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Created by DrSmugleaf on 08/04/2019
 */
public class Party implements Comparable<Party> {

    @Nonnull
    private final String NAME;

    @Nonnull
    private final ImmutableList<Member> MEMBERS;

    @Nonnull
    private final ImmutableMap<Vote, Votes> VOTES;

    public Party(@Nonnull String name, @Nonnull List<Member> members) {
        NAME = name;

        List<Member> validMembers = new ArrayList<>();
        for (Member member : members) {
            if (member.getNationalPoliticalGroup().equals(name)) {
                validMembers.add(member);
            }
        }

        MEMBERS = ImmutableList.copyOf(validMembers);
        VOTES = parseVotes(MEMBERS);
    }

    @Nonnull
    private static ImmutableMap<Vote, Votes> parseVotes(@Nonnull List<Member> members) {
        Map<Vote, Map<Decision, Integer>> voteMap = new HashMap<>();
        for (Vote vote : Vote.getVotes().values()) {
            voteMap.put(vote, new HashMap<>());
        }

        for (Member member : members) {
            for (Map.Entry<Vote, Decision> entry : member.getVotes().entrySet()) {
                Vote vote = entry.getKey();
                Map<Decision, Integer> result = voteMap.get(vote);
                Decision decision = entry.getValue();
                if (!result.containsKey(decision)) {
                    result.put(decision, 0);
                }

                Integer votesFor = result.get(decision);
                result.put(decision, votesFor + 1);
            }
        }

        Map<Vote, Votes> votes = new HashMap<>();
        for (Map.Entry<Vote, Map<Decision, Integer>> entry : voteMap.entrySet()) {
            Vote vote = entry.getKey();
            Map<Decision, Integer> results = entry.getValue();
            votes.put(vote, new Votes(vote, results));
        }

        return ImmutableMap.copyOf(votes);
    }

    @Nonnull
    public String getName() {
        return NAME;
    }

    @Nonnull
    public ImmutableList<Member> getMembers() {
        return MEMBERS;
    }

    @Nonnull
    public Votes getVotes(@Nonnull Vote vote) {
        return VOTES.get(vote);
    }

    @Override
    public int compareTo(@NotNull Party o) {
        return getName().compareTo(o.getName());
    }

    public int compareVote(@Nonnull Party o, @Nonnull Vote vote, @Nonnull Decision decision) {
        Result thisResult = getVotes(vote).getResult(decision);
        Result otherResult = o.getVotes(vote).getResult(decision);
        return otherResult.compareTo(thisResult);
    }

}
