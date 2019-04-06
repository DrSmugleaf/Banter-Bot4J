package com.github.drsmugleaf.article13;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Parser {

    public static void main(String[] args) {
        Map<String, Party> parties = new HashMap<>();
        String csvPath = Objects.requireNonNull(Parser.class.getClassLoader().getResource("votes.csv")).getFile();

        try {
            CSVReader reader = new CSVReaderBuilder(new FileReader(new File(csvPath))).build();
            String[] line;
            while ((line = reader.readNext()) != null) {
                String partyName = line[13];
                parties.putIfAbsent(partyName, new Party(partyName));
                Party party = parties.get(partyName);

                String finalVote = line[5];
                party.parseFinalVote(finalVote);

                String proceduralVote = line[6];
                party.parseProceduralVote(proceduralVote);

                String pledge2019 = line[7];
                party.parsePledge2019(pledge2019);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Party> sortedFor = parties.values().stream().sorted((o1, o2) -> o2.FOR.getFinalVote() - o1.FOR.getFinalVote()).collect(Collectors.toList());
        List<Party> sortedAgainst = parties.values().stream().sorted((o1, o2) -> o2.AGAINST.getFinalVote() - o1.AGAINST.getFinalVote()).collect(Collectors.toList());

        System.out.print("**For:** ");
        for (Party party : sortedFor) {
            System.out.print(party.getName() + " (" + party.FOR.getFinalVote() + ")");
            if (sortedFor.indexOf(party) < sortedFor.size() - 1) {
                System.out.print(" > ");
            }
        }

        System.out.println();

        System.out.print("**Against:** ");
        for (Party party : sortedAgainst) {
            System.out.print(party.getName() + " (" + party.AGAINST.getFinalVote() + ")");
            if (sortedFor.indexOf(party) < sortedFor.size() - 1) {
                System.out.print(" > ");
            }
        }
    }

}
