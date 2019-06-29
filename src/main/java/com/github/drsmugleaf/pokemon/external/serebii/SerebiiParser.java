package com.github.drsmugleaf.pokemon.external.serebii;

import com.github.drsmugleaf.pokemon.external.ParsingException;
import com.github.drsmugleaf.pokemon.item.Items;
import com.github.drsmugleaf.pokemon.moves.BaseMove;
import com.github.drsmugleaf.pokemon.moves.MoveCategory;
import com.github.drsmugleaf.pokemon.moves.Hit;
import com.github.drsmugleaf.pokemon.moves.Target;
import com.github.drsmugleaf.pokemon.types.Type;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 16/06/2017.
 */
public class SerebiiParser {

    public static void printMoveListAsEnums() {
        Document doc1;
        try {
            doc1 = Jsoup.connect("http://www.serebii.net/attackdex-sm/").get();
        } catch (IOException e) {
            throw new ParsingException("Error parsing Serebii move list", e);
        }

        Elements movesHtml = doc1.body().select("div > table > tbody > tr > td > form > div > select > option");
        for (Element element : movesHtml) {
            if (element.text().startsWith("AttackDex: ")) {
                continue;
            }

            String move = element
                    .text()
                    .toUpperCase()
                    .replaceAll(",|\\.|'", "")
                    .replaceAll(" ", "_")
                    .replaceAll("-", "_")
                    .trim() +
                    "(\"" + element.text() + "\"),";

            System.out.println(move);
        }
    }

    public static void parseMovesToCSV() throws Exception {
        Document doc1;
        try {
            doc1 = Jsoup.connect("http://www.serebii.net/attackdex-sm/").get();
        } catch (IOException e) {
            throw new ParsingException("Error getting Serebii move list", e);
        }

        FileWriter fileWriter = new FileWriter("moves.csv");
        CSVParser parser = new CSVParserBuilder().withSeparator(',').build();
        ICSVWriter writer = new CSVWriterBuilder(fileWriter).withParser(parser).build();

        writer.writeNext(new String[]{
                "Name",
                "Type",
                "Category",
                "PP",
                "Power",
                "Accuracy",
                "Battle Effect",
                "In-Depth Effect",
                "Secondary Effect",
                "Detailed Effect",
                "Effect Rate",
                "Is Self Z-Move?",
                "Corresponding Z-Move",
                "Z-Move Item",
                "Z-Move Power",
                "Is Z-Move?",
                "Z-Move Required Pokemon",
                "Z-Move Required Moves",
                "Moves that turn into this",
                "Base Critical Hit Rate",
                "Priority",
                "Target",
                "Pokemon Hit",
                "Physical Contact?",
                "Sound Type?",
                "Punch Move?",
                "Snatchable?",
                "Z-Move?",
                "Defrosts When Used?",
                "Hits opposite side in triples?",
                "Reflected?",
                "Blocked?",
                "Copyable?"
        });

        Elements movesHtml = doc1.body().select("div > table > tbody > tr > td > form > div > select > option");
        for (Element element : movesHtml) {
            if (element.attr("value").isEmpty()) {
                continue;
            }

            String moveUrl = element.attr("value");

            Document doc;
            try {
                doc = Jsoup.connect("http://www.serebii.net" + moveUrl).get();
            } catch (IOException e) {
                throw new ParsingException("Error getting Serebii page for move " + element.attr("value"), e);
            }

            String name = null;
            Type type = null;
            MoveCategory category = null;
            Integer pp = null;
            Integer power = null;
            Integer accuracy = null;
            String battleEffect = null;
            String inDepthEffect = null;
            String secondaryEffect = null;
            String detailedEffect = null;
            Integer effectRate = null;
            Boolean isSelfZMove = false;
            Double baseCriticalHitRate = null;
            Integer priority = null;
            Hit pokemonHit = null;
            Target target = null;
            Boolean physicalContact = null;
            Boolean soundType = null;
            Boolean punchMove = null;
            Boolean snatchable = null;
            Boolean zMove = null;
            String correspondingZMove = null;
            Integer zMovePower = null;
            Items zMoveItem = null;
            List<String> zMoveRequiredPokemon = new ArrayList<>();
            List<BaseMove> zMoveRequiredMove = new ArrayList<>();
            Boolean defrostsWhenUsed = null;
            Boolean hitsOppositeSideInTriples = null;
            Boolean reflected = null;
            Boolean blocked = null;
            Boolean copyable = null;
            List<String> movesThatTurnIntoThis = new ArrayList<>();

            Elements tables = doc.getElementsByClass("dextable");
            for (int i = 0; i < 2; i++) {
                Element table = tables.get(i);
                Elements tableRows = table.child(0).children();

                for (int j = 0; j < tableRows.size(); j = j + 2) {
                    Element propertyNameRow;
                    Element propertyValueRow;
                    Elements propertyNameGroup;
                    Elements propertyValueGroup;
                    try {
                        propertyNameRow = tableRows.get(j);
                        propertyValueRow = tableRows.get(j + 1);
                        propertyNameGroup = propertyNameRow.children();
                        propertyValueGroup = propertyValueRow.children();
                    } catch (NullPointerException | IndexOutOfBoundsException e) {
                        throw new ParsingException("Error parsing move " + element.attr("value") + ", skipping", e);
                    }

                    if (propertyNameGroup == null || propertyValueGroup == null) {
                        throw new ParsingException("Null property name or value group for move " + element.attr("value") + ", skipping");
                    }

                    for (int k = 0; k < propertyNameGroup.size(); k++) {
                        String propertyName = propertyNameGroup.get(k).text();
                        Element propertyValue = propertyValueGroup.get(k);
                        if (Objects.equals(propertyName, "Z-" + name)) {
                            isSelfZMove = true;
                            continue;
                        }

                        switch (propertyName) {
                            case "Attack Name":
                                name = propertyValue.html().split("<br>")[0];
                                switch (name) {
                                    case "10,000,000 Volt Thunderbolt":
                                        zMoveItem = Items.PIKASHUNIUM_Z;
                                        zMoveRequiredPokemon.add("Pikachu");
                                        zMoveRequiredMove.add(BaseMove.THUNDERBOLT);
                                        break;
                                    case "Catastropika":
                                        zMoveItem = Items.PIKANIUM_Z;
                                        zMoveRequiredPokemon.add("Pikachu");
                                        zMoveRequiredMove.add(BaseMove.VOLT_TACKLE);
                                        break;
                                    case "Extreme Evoboost":
                                        zMoveItem = Items.EEVIUM_Z;
                                        zMoveRequiredPokemon.add("Eevee");
                                        zMoveRequiredMove.add(BaseMove.LAST_RESORT);
                                        break;
                                    case "Guardian of Alola":
                                        zMoveItem = Items.TAPUNIUM_Z;
                                        zMoveRequiredPokemon.add("Tapu Bulu");
                                        zMoveRequiredPokemon.add("Tapu Fini");
                                        zMoveRequiredPokemon.add("Tapu Koko");
                                        zMoveRequiredPokemon.add("Tapu Lele");
                                        zMoveRequiredMove.add(BaseMove.NATURES_MADNESS);
                                        break;
                                    case "Malicious Moonsault":
                                        zMoveItem = Items.INCINIUM_Z;
                                        zMoveRequiredPokemon.add("Incineroar");
                                        zMoveRequiredMove.add(BaseMove.DARKEST_LARIAT);
                                        break;
                                    case "Oceanic Operetta":
                                        zMoveItem = Items.PRIMARIUM_Z;
                                        zMoveRequiredPokemon.add("Primarina");
                                        zMoveRequiredMove.add(BaseMove.SPARKLING_ARIA);
                                        break;
                                    case "Pulverizing Pancake":
                                        zMoveItem = Items.SNORLIUM_Z;
                                        zMoveRequiredPokemon.add("Snorlax");
                                        zMoveRequiredMove.add(BaseMove.GIGA_IMPACT);
                                        break;
                                    case "Sinister Arrow Raid":
                                        zMoveItem = Items.DECIDIUM_Z;
                                        zMoveRequiredPokemon.add("Decidueye");
                                        zMoveRequiredMove.add(BaseMove.SPIRIT_SHACKLE);
                                        break;
                                    case "Soul-Stealing 7-Star Strike":
                                        zMoveItem = Items.MARSHADIUM_Z;
                                        zMoveRequiredPokemon.add("Marshadow");
                                        zMoveRequiredMove.add(BaseMove.SPECTRAL_THIEF);
                                        break;
                                    case "Stoked Sparksurfer":
                                        zMoveItem = Items.ALORAICHIUM_Z;
                                        zMoveRequiredPokemon.add("Raichu-Alola");
                                        zMoveRequiredMove.add(BaseMove.THUNDERBOLT);
                                        break;
                                }
                                break;
                            case "Battle Type":
                                Matcher tMatcher = Pattern.compile("<a href=\"/attackdex-.+/(.+?).shtml\">").matcher(propertyValue.html());
                                tMatcher.find();
                                type = Type.getType(tMatcher.group(1));
                                break;
                            case "Category":
                                Matcher cMatcher = Pattern.compile("<a href=\"/attackdex-.+/(.+).shtml\">").matcher(propertyValue.html());
                                cMatcher.find();
                                category = MoveCategory.getCategory(cMatcher.group(1));
                                break;
                            case "Power Points":
                                pp = Integer.parseInt(propertyValue.text());
                                break;
                            case "Base Power":
                                power = Integer.parseInt(propertyValue.text());
                                break;
                            case "Accuracy":
                                accuracy = Integer.parseInt(propertyValue.text());
                                break;
                            case "Battle Effect:":
                                battleEffect = propertyValue.text();
                                break;
                            case "In-Depth Effect:":
                                inDepthEffect = propertyValue.text();
                                break;
                            case "Secondary Effect:":
                                secondaryEffect = propertyValue.text();
                                break;
                            case "Effect Rate:":
                                effectRate = Objects.equals(propertyValue.text(), "-- %") ? null :
                                        Integer.parseInt(propertyValue.text().replace(" %", ""));
                                break;
                            case "Corresponding Z-Move:":
                                correspondingZMove = propertyValue.text();
                                break;
                            case "Z-Move Power:":
                                zMovePower = Integer.parseInt(propertyValue.text());
                                break;
                            case "Detailed Effect":
                                detailedEffect = propertyValue.text();
                                break;
                            case "Base Critical Hit Rate":
                                if (Objects.equals(propertyValue.text(), "None")) continue;
                                baseCriticalHitRate = Double.parseDouble(propertyValue.text().replace("%", ""));
                                break;
                            case "Speed Priority":
                                priority = Integer.parseInt(propertyValue.text());
                                break;
                            case "Pokémon Hit in Battle":
                                switch (propertyValue.text()) {
                                    case "Selected Target":
                                        pokemonHit = Hit.SELECTED_TARGET;
                                        break;
                                    case "Random Target":
                                        pokemonHit = Hit.RANDOM_TARGET;
                                        break;
                                    case "adjacentFoe":
                                        pokemonHit = Hit.ADJACENT_FOES;
                                        break;
                                    case "Field":
                                        pokemonHit = Hit.FIELD;
                                        break;
                                    case "Enemy Side":
                                        pokemonHit = Hit.ENEMY_SIDE;
                                        break;
                                    case "Team":
                                        pokemonHit = Hit.TEAM;
                                        break;
                                    case "Ally":
                                        pokemonHit = Hit.ALLY;
                                        break;
                                    case "Self or Ally":
                                        pokemonHit = Hit.SELF_OR_ALLY;
                                        break;
                                    case "All Adjacent Foes":
                                        pokemonHit = Hit.ADJACENT_FOES;
                                        break;
                                    case "All Adjacent Pokémon":
                                        pokemonHit = Hit.ALL_ADJACENT;
                                        break;
                                    case "All":
                                        pokemonHit = Hit.ALL;
                                        break;
                                    case "Adjacent Ally":
                                        pokemonHit = Hit.ADJACENT_ALLY;
                                        break;
                                    case "Self":
                                        pokemonHit = Hit.SELF;
                                        break;
                                    case "Special":
                                        pokemonHit = Hit.SPECIAL;
                                        break;
                                    default:
                                        throw new Exception("Missing case \"" + propertyValue.text() + "\" for Pokemon Hit in battle from Serebii for move: " + name);
                                }

                                Document bulbapediaDoc;
                                try {
                                    bulbapediaDoc = Jsoup.connect("https://bulbapedia.bulbagarden.net/wiki/" + name.replace(" ", "_").replace("'", "%27") + "_(move)").get();
                                } catch (HttpStatusException e) {
                                    throw new ParsingException("Error getting Bulbapedia page for move " + name, e);
                                }
                                String bTarget = bulbapediaDoc.select("tbody > tr:nth-child(5) > td > table > tbody > tr:nth-child(3) > td > small").text();
                                switch (bTarget) {
                                    case "May affect anyone adjacent to the user":
                                        target = Target.ANY_ADJACENT;
                                        break;
                                    case "May affect any adjacent foe, but not allies":
                                        target = Target.ADJACENT_FOES;
                                        break;
                                    case "May affect anyone but the user":
                                        target = Target.ALL_BUT_SELF;
                                        break;
                                    case "Affects all foes":
                                        target = Target.ALL_FOES;
                                        break;
                                    case "Affects all Pokémon on the field":
                                        target = Target.ALL;
                                        break;
                                    case "Affects the user and all allies":
                                        target = Target.ALL_ALLIES;
                                        break;
                                    case "Affects the user":
                                        target = Target.SELF;
                                        break;
                                    case "May affect the user or an adjacent ally":
                                        target = Target.SELF_OR_ADJACENT_ALLY;
                                        break;
                                    case "Affects all adjacent foes, but not allies":
                                        target = Target.ADJACENT_FOES;
                                        break;
                                    case "Affects all Pokémon adjacent to the user":
                                        target = Target.ALL_ADJACENT;
                                        break;
                                    case "Affects an adjacent ally":
                                        target = Target.ADJACENT_ALLY;
                                        break;
                                    default:
                                        throw new Exception("Missing case \"" + bTarget + "\" for Pokemon Target in battle from Bulbapedia for move: " + name);
                                }
                            case "Physical Contact":
                                physicalContact = propertyValue.text().equals("Yes");
                                break;
                            case "Sound-Type - Details":
                                soundType = propertyValue.text().equals("Yes");
                                break;
                            case "Punch Move - Details":
                                punchMove = propertyValue.text().equals("Yes");
                                break;
                            case "Snatchable":
                                snatchable = propertyValue.text().equals("Yes");
                                break;
                            case "Z Move?":
                                zMove = propertyValue.text().equals("Yes");
                                break;
                            case "Defrosts When Used?":
                                defrostsWhenUsed = propertyValue.text().equals("Yes");
                                break;
                            case "Hits the opposite side in Triple Battles?":
                                hitsOppositeSideInTriples = propertyValue.text().equals("Yes");
                                break;
                            case "Reflected By Magic Coat/Magic Bounce?":
                                reflected = propertyValue.text().equals("Yes");
                                break;
                            case "Blocked by Protect/Detect?":
                                blocked = propertyValue.text().equals("Yes");
                                break;
                            case "Copyable by Mirror Move?":
                                copyable = propertyValue.text().equals("Yes");
                                break;
                            default:
                                throw new ParsingException("Missing case for Serebii move property " + propertyName);
                        }
                    }
                }
            }

            if (!isSelfZMove && correspondingZMove == null && tables.size() > 2) {
                for (Element element1 : tables.get(2).child(0).children()) {
                    if (!Objects.equals(element1.child(0).className(), "fooinfo") || Objects.equals(element1.child(0).attr("align"), "center")) {
                        continue;
                    }

                    movesThatTurnIntoThis.add(element1.child(0).child(0).text());
                }
            }

            if (isSelfZMove || (!isSelfZMove && correspondingZMove == null)) {
                switch (type) {
                    case NORMAL:
                        zMoveItem = Items.NORMALIUM_Z;
                        break;
                    case FIRE:
                        zMoveItem = Items.FIRIUM_Z;
                        break;
                    case WATER:
                        zMoveItem = Items.WATERIUM_Z;
                        break;
                    case ELECTRIC:
                        zMoveItem = Items.ELECTRIUM_Z;
                        break;
                    case GRASS:
                        zMoveItem = Items.GRASSIUM_Z;
                        break;
                    case ICE:
                        zMoveItem = Items.ICIUM_Z;
                        break;
                    case FIGHTING:
                        zMoveItem = Items.FIGHTINIUM_Z;
                        break;
                    case POISON:
                        zMoveItem = Items.POISONIUM_Z;
                        break;
                    case GROUND:
                        zMoveItem = Items.GROUNDIUM_Z;
                        break;
                    case FLYING:
                        zMoveItem = Items.FLYINIUM_Z;
                        break;
                    case PSYCHIC:
                        zMoveItem = Items.PSYCHIUM_Z;
                        break;
                    case BUG:
                        zMoveItem = Items.BUGINIUM_Z;
                        break;
                    case ROCK:
                        zMoveItem = Items.ROCKIUM_Z;
                        break;
                    case GHOST:
                        zMoveItem = Items.GHOSTIUM_Z;
                        break;
                    case DRAGON:
                        zMoveItem = Items.DRAGONIUM_Z;
                        break;
                    case DARK:
                        zMoveItem = Items.DARKINIUM_Z;
                        break;
                    case STEEL:
                        zMoveItem = Items.STEELIUM_Z;
                        break;
                    case FAIRY:
                        zMoveItem = Items.FAIRIUM_Z;
                        break;
                }
            }

            String[] move = {
                    name, // Name
                    type.getName(), // Type
                    category.NAME, // Category
                    String.valueOf(pp), // PP
                    String.valueOf(power), // Power
                    String.valueOf(accuracy), // Accuracy
                    battleEffect, // Battle Effect
                    inDepthEffect == null ? "" : String.valueOf(inDepthEffect), // In-Depth Effect
                    secondaryEffect, // Secondary Effect
                    effectRate != null ? String.valueOf(effectRate) : "", // Effect Rate
                    String.valueOf(isSelfZMove), // Is Self Z-Move?
                    correspondingZMove, // Corresponding Z-Move
                    zMoveItem != null ? zMoveItem.NAME : "", // Z-Move Item
                    detailedEffect != null ? detailedEffect : "", // Detailed Effect
                    zMovePower != null ? String.valueOf(zMovePower) : "", // Z-Move Power
                    !isSelfZMove && correspondingZMove == null ? "true" : "false", // Is Z-Move?
                    zMoveRequiredPokemon.isEmpty() ? "" : zMoveRequiredPokemon.stream().collect(Collectors.joining(",")), // Z-Move Required Pokemon
                    zMoveRequiredMove.isEmpty() ? "" : zMoveRequiredMove.stream().map(baseMove -> baseMove.NAME).collect(Collectors.joining(",")), // Z-Move Required Moves
                    movesThatTurnIntoThis.isEmpty() ? "" : movesThatTurnIntoThis.stream().collect(Collectors.joining(",")), // Moves that turn into this
                    baseCriticalHitRate != null ? String.valueOf(baseCriticalHitRate) : "", // Base Critical Hit Rate
                    String.valueOf(priority), // Priority
                    String.valueOf(target), // Target
                    String.valueOf(pokemonHit), // Pokemon Hit
                    String.valueOf(physicalContact), // Physical Contact?
                    String.valueOf(soundType), // Sound Type?
                    String.valueOf(punchMove), // Punch Move?
                    String.valueOf(snatchable), // Snatchable?
                    String.valueOf(zMove), // Z-Move?
                    String.valueOf(defrostsWhenUsed), // Defrosts When Used?
                    String.valueOf(hitsOppositeSideInTriples), // Hits opposite side in triples?
                    String.valueOf(reflected), // Reflected?
                    String.valueOf(blocked), // Blocked?
                    String.valueOf(copyable) // Copyable?
            };

            writer.writeNext(move);
        }

        writer.close();
    }

}
