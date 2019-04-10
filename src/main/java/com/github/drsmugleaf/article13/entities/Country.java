package com.github.drsmugleaf.article13.entities;

import com.github.drsmugleaf.article13.csv.Sheet;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Created by DrSmugleaf on 07/04/2019
 */
public class Country {

    @Nonnull
    private static final ImmutableMap<String, Country> COUNTRIES = parseCountries();

    @Nonnull
    private final String NAME;

    @Nonnull
    private final ImmutableList<Member> MEMBERS;

    @Nonnull
    private final ImmutableMap<String, Party> PARTIES;

    private Country(@Nonnull Sheet csv) {
        NAME = csv.getSheetName();
        MEMBERS = parseMembers(csv);
        PARTIES = parseParties(MEMBERS);
    }

    @Nonnull
    private static ImmutableMap<String, Country> parseCountries() {
        Map<String, Country> countries = new HashMap<>();

        List<Sheet> sheets = Sheet.getVoteSheets();
        for (Sheet sheet : sheets) {
            Country country = new Country(sheet);
            countries.put(country.getName(), country);
        }

        return ImmutableMap.copyOf(countries);
    }

    @Nonnull
    public static ImmutableList<Member> parseMembers(@Nonnull Sheet csv) {
        List<Member> members = new ArrayList<>();
        for (Map<String, String> line : csv.read()) {
            members.add(new Member(line));
        }

        return ImmutableList.copyOf(members);
    }

    @Nonnull
    public static ImmutableMap<String, Party> parseParties(@Nonnull List<Member> members) {
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

    @Nonnull
    public static ImmutableMap<String, Country> getCountries() {
        return COUNTRIES;
    }

    @Nonnull
    public static ImmutableSet<String> getCountryNames() {
        return COUNTRIES.keySet();
    }

    @Nonnull
    public static Country getCountry(@Nonnull String name) {
        return COUNTRIES.get(name);
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
    public ImmutableMap<String, Party> getParties() {
        return PARTIES;
    }

    @Nonnull
    public ImmutableSet<String> getPartyNames() {
        return PARTIES.keySet();
    }

}
