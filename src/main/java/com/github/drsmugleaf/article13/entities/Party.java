package com.github.drsmugleaf.article13.entities;

import com.github.drsmugleaf.article13.vote.Decision;
import com.github.drsmugleaf.article13.vote.Result;
import com.github.drsmugleaf.article13.vote.Vote;
import com.github.drsmugleaf.article13.vote.Votes;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DrSmugleaf on 08/04/2019
 */
public class Party implements Comparable<Party> {

    private final String NAME;
    private final ImmutableList<Member> MEMBERS;
    private final ImmutableMap<Vote, Votes> VOTES;

    public Party(String name, List<Member> members) {
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

    private static ImmutableMap<Vote, Votes> parseVotes(List<Member> members) {
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

    public String getName() {
        return NAME;
    }

    public ImmutableList<Member> getMembers() {
        return MEMBERS;
    }

    public Votes getVotes(Vote vote) {
        return VOTES.get(vote);
    }

    @Override
    public int compareTo(Party o) {
        return getName().compareTo(o.getName());
    }

    public int compareVote(Party o, Vote vote, Decision decision) {
        Result thisResult = getVotes(vote).getResult(decision);
        Result otherResult = o.getVotes(vote).getResult(decision);
        return otherResult.compareTo(thisResult);
    }

}
