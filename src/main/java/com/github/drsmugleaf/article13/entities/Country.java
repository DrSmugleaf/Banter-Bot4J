package com.github.drsmugleaf.article13.entities;

import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.article13.csv.Sheet;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.*;

/**
 * Created by DrSmugleaf on 07/04/2019
 */
public class Country {

    private static final ImmutableMap<String, Country> COUNTRIES = parseCountries();

    private final String NAME;
    private final ImmutableList<Member> MEMBERS;
    private final ImmutableMap<String, Party> PARTIES;

    private Country(Sheet csv) {
        NAME = csv.getSheetName();
        MEMBERS = parseMembers(csv);
        PARTIES = parseParties(MEMBERS);
    }

    private static ImmutableMap<String, Country> parseCountries() {
        Map<String, Country> countries = new HashMap<>();

        List<Sheet> sheets = Sheet.getVoteSheets();
        for (Sheet sheet : sheets) {
            Country country = new Country(sheet);
            countries.put(country.getName(), country);
        }

        return ImmutableMap.copyOf(countries);
    }

    public static ImmutableList<Member> parseMembers(Sheet csv) {
        List<Member> members = new ArrayList<>();
        for (Map<String, String> line : csv.read()) {
            members.add(new Member(line));
        }

        return ImmutableList.copyOf(members);
    }

    public static ImmutableMap<String, Party> parseParties(List<Member> members) {
        Map<String, Party> parties = new TreeMap<>();

        for (Member member : members) {
            String partyName = member.getNationalPoliticalGroup();
            if (parties.containsKey(partyName)) {
                continue;
            }

            Party party = new Party(partyName, members);
            parties.put(partyName, party);
        }

        return ImmutableMap.copyOf(parties);
    }

    public static ImmutableMap<String, Country> getCountries() {
        return COUNTRIES;
    }

    public static ImmutableSet<String> getCountryNames() {
        return getCountries().keySet();
    }

    @Nullable
    public static Country getCountry(String name) {
        return getCountries().get(name);
    }

    public String getName() {
        return NAME;
    }

    public ImmutableList<Member> getMembers() {
        return MEMBERS;
    }

    public ImmutableMap<String, Party> getParties() {
        return PARTIES;
    }

    public ImmutableSet<String> getPartyNames() {
        return getParties().keySet();
    }

}
