package com.github.drsmugbrain.pokemon;

import com.github.drsmugbrain.pokemon.types.Type;
import com.github.drsmugbrain.util.Bot;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.FileReader;
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

    public static void parse() {
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader("moves.csv"), ',');
        } catch (FileNotFoundException e) {
            Bot.LOGGER.error("Error parsing moves.csv", e);
        }

        String[] nextLine;
        try {
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                BaseMove move = BaseMove.getMove(nextLine[0]);
                move
                        .setType(Type.getType(nextLine[1]))
                        .setCategory(Category.getCategory(nextLine[2]))
                        .setPP(Integer.parseInt(nextLine[3]))
                        .setPower(Integer.parseInt(nextLine[4]))
                        .setAccuracy(Integer.parseInt(nextLine[5]))
                        .setBattleEffect(nextLine[6])
                        .setInDepthEffect(nextLine[7].isEmpty() ? null : nextLine[7])
                        .setSecondaryEffect(nextLine[8])
                        .setEffectRate(nextLine[9].isEmpty() ? null : Integer.valueOf(nextLine[9]))
                        .setIsSelfZMove(Boolean.parseBoolean(nextLine[10]))
                        .setCorrespondingZMove(nextLine[11].isEmpty() ? null : BaseMove.getMove(nextLine[11]))
                        .setZMoveItem(nextLine[12].isEmpty() ? null : Item.getItem(nextLine[12]))
                        .setDetailedEffect(nextLine[13].isEmpty() ? null : nextLine[13])
                        .setZMovePower(nextLine[14].isEmpty() ? null : Integer.valueOf(nextLine[14]))
                        .setIsZMove(Boolean.parseBoolean(nextLine[15]))
                        .setZMoveRequiredPokemon(nextLine[16].isEmpty() ? null : nextLine[16].split(","))
                        .setZMoveRequiredMove(nextLine[17].isEmpty() ? null : BaseMove.getMove(nextLine[17]))
                        .setZMoveMovesThatTurnIntoThis(nextLine[18].isEmpty() ? null : nextLine[18].split(","))
                        .setBaseCriticalHitRate(nextLine[19].isEmpty() ? null : CriticalHitStage.getStageByPercentage(Double.parseDouble(nextLine[19])))
                        .setPriority(Integer.parseInt(nextLine[20]))
                        .setTarget(nextLine[21].isEmpty() ? null : Target.getTarget(nextLine[21]))
                        .setPokemonHit(Hit.getHit(nextLine[22]))
                        .setPhysicalContact(Boolean.parseBoolean(nextLine[23]))
                        .setSoundType(Boolean.parseBoolean(nextLine[24]))
                        .setPunchMove(Boolean.parseBoolean(nextLine[25]))
                        .setSnatchable(Boolean.parseBoolean(nextLine[26]))
                        .setZMove(Boolean.parseBoolean(nextLine[27]))
                        .setDefrostsWhenUsed(Boolean.parseBoolean(nextLine[28]))
                        .setHitsOppositeSideInTriples(Boolean.parseBoolean(nextLine[29]))
                        .setReflected(Boolean.parseBoolean(nextLine[30]))
                        .setBlocked(Boolean.parseBoolean(nextLine[31]))
                        .setCopyable(Boolean.parseBoolean(nextLine[32]));
            }
        } catch (IOException e) {
            Bot.LOGGER.error("Error reading line in moves.csv", e);
        }
    }

    public static void printMoveListAsEnums() {
        Document doc1 = null;
        try {
            doc1 = Jsoup.connect("http://www.serebii.net/attackdex-sm/").get();
        } catch (IOException e) {
            Bot.LOGGER.error("Error parsing Serebii move list", e);
        }

        Elements movesHtml = doc1.body().select("div > table > tbody > tr > td > form > div > select > option");
        for (Element element : movesHtml) {
            if (element.text().startsWith("AttackDex: ")) {
                continue;
            }
            System.out.println(element.text().toUpperCase().replaceAll(",|\\.|'", "").replaceAll(" ", "_").replaceAll("-", "_").trim() + "(\"" + element.text() + "\"),");
        }
    }

    public static void parseMovesToCSV() throws Exception {
        Document doc1;
        try {
            doc1 = Jsoup.connect("http://www.serebii.net/attackdex-sm/").get();
        } catch (IOException e) {
            Bot.LOGGER.error("Error getting Serebii move list", e);
            return;
        }

        CSVWriter writer;
        writer = new CSVWriter(new FileWriter("moves.csv"), ',');
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
                "Effect Rate",
                "Is Self Z-Move?",
                "Corresponding Z-Move",
                "Z-Move Item",
                "Detailed Effect",
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
            if (element.attr("value").isEmpty()) continue;

            String moveUrl = element.attr("value");

            Document doc;
            try {
                doc = Jsoup.connect("http://www.serebii.net" + moveUrl).get();
            } catch (IOException e) {
                Bot.LOGGER.error("Error getting Serebii page for move " + element.attr("value"), e);
                continue;
            }

            String name = null;
            Type type = null;
            Category category = null;
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
            Item zMoveItem = null;
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
                for (int i1 = 0; i1 < tableRows.size(); i1 = i1 + 2) {
                    Element propertyNameRow;
                    Element propertyValueRow;
                    Elements propertyNameGroup;
                    Elements propertyValueGroup;
                    try {
                        propertyNameRow = tableRows.get(i1);
                        propertyValueRow = tableRows.get(i1 + 1);
                        propertyNameGroup = propertyNameRow.children();
                        propertyValueGroup = propertyValueRow.children();
                    } catch (NullPointerException | IndexOutOfBoundsException e) {
                        Bot.LOGGER.error("Error parsing move " + element.attr("value") + ", skipping", e);
                        continue;
                    }

                    if (propertyNameGroup == null || propertyValueGroup == null) {
                        Bot.LOGGER.error("Null property name or value group for move " + element.attr("value") + ", skipping");
                        continue;
                    }

                    for (int i2 = 0; i2 < propertyNameGroup.size(); i2++) {
                        String propertyName = propertyNameGroup.get(i2).text();
                        Element propertyValue = propertyValueGroup.get(i2);
                        if (Objects.equals(propertyName, "Z-" + name)) {
                            isSelfZMove = true;
                            continue;
                        }

                        switch (propertyName) {
                            case "Attack Name":
                                name = propertyValue.html().split("<br>")[0];
                                switch (name) {
                                    case "10,000,000 Volt Thunderbolt":
                                        zMoveItem = Item.PIKASHUNIUM_Z;
                                        zMoveRequiredPokemon.add("Pikachu");
                                        zMoveRequiredMove.add(BaseMove.THUNDERBOLT);
                                        break;
                                    case "Catastropika":
                                        zMoveItem = Item.PIKANIUM_Z;
                                        zMoveRequiredPokemon.add("Pikachu");
                                        zMoveRequiredMove.add(BaseMove.VOLT_TACKLE);
                                        break;
                                    case "Extreme Evoboost":
                                        zMoveItem = Item.EEVIUM_Z;
                                        zMoveRequiredPokemon.add("Eevee");
                                        zMoveRequiredMove.add(BaseMove.LAST_RESORT);
                                        break;
                                    case "Guardian of Alola":
                                        zMoveItem = Item.TAPUNIUM_Z;
                                        zMoveRequiredPokemon.add("Tapu Bulu");
                                        zMoveRequiredPokemon.add("Tapu Fini");
                                        zMoveRequiredPokemon.add("Tapu Koko");
                                        zMoveRequiredPokemon.add("Tapu Lele");
                                        zMoveRequiredMove.add(BaseMove.NATURES_MADNESS);
                                        break;
                                    case "Malicious Moonsault":
                                        zMoveItem = Item.INCINIUM_Z;
                                        zMoveRequiredPokemon.add("Incineroar");
                                        zMoveRequiredMove.add(BaseMove.DARKEST_LARIAT);
                                        break;
                                    case "Oceanic Operetta":
                                        zMoveItem = Item.PRIMARIUM_Z;
                                        zMoveRequiredPokemon.add("Primarina");
                                        zMoveRequiredMove.add(BaseMove.SPARKLING_ARIA);
                                        break;
                                    case "Pulverizing Pancake":
                                        zMoveItem = Item.SNORLIUM_Z;
                                        zMoveRequiredPokemon.add("Snorlax");
                                        zMoveRequiredMove.add(BaseMove.GIGA_IMPACT);
                                        break;
                                    case "Sinister Arrow Raid":
                                        zMoveItem = Item.DECIDIUM_Z;
                                        zMoveRequiredPokemon.add("Decidueye");
                                        zMoveRequiredMove.add(BaseMove.SPIRIT_SHACKLE);
                                        break;
                                    case "Soul-Stealing 7-Star Strike":
                                        zMoveItem = Item.MARSHADIUM_Z;
                                        zMoveRequiredPokemon.add("Marshadow");
                                        zMoveRequiredMove.add(BaseMove.SPECTRAL_THIEF);
                                        break;
                                    case "Stoked Sparksurfer":
                                        zMoveItem = Item.ALORAICHIUM_Z;
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
                                category = Category.getCategory(cMatcher.group(1));
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

                                Document bulbapediaDoc = null;
                                try {
                                    bulbapediaDoc = Jsoup.connect("https://bulbapedia.bulbagarden.net/wiki/" + name.replace(" ", "_").replace("'", "%27") + "_(move)").get();
                                } catch (HttpStatusException e) {
                                    Bot.LOGGER.error("Error getting Bulbapedia page for move " + name, e);
                                    continue;
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
                                Bot.LOGGER.error("Missing case for Serebii move property " + propertyName);
                                break;
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
                        zMoveItem = Item.NORMALIUM_Z;
                        break;
                    case FIRE:
                        zMoveItem = Item.FIRIUM_Z;
                        break;
                    case WATER:
                        zMoveItem = Item.WATERIUM_Z;
                        break;
                    case ELECTRIC:
                        zMoveItem = Item.ELECTRIUM_Z;
                        break;
                    case GRASS:
                        zMoveItem = Item.GRASSIUM_Z;
                        break;
                    case ICE:
                        zMoveItem = Item.ICIUM_Z;
                        break;
                    case FIGHTING:
                        zMoveItem = Item.FIGHTINIUM_Z;
                        break;
                    case POISON:
                        zMoveItem = Item.POISONIUM_Z;
                        break;
                    case GROUND:
                        zMoveItem = Item.GROUNDIUM_Z;
                        break;
                    case FLYING:
                        zMoveItem = Item.FLYINIUM_Z;
                        break;
                    case PSYCHIC:
                        zMoveItem = Item.PSYCHIUM_Z;
                        break;
                    case BUG:
                        zMoveItem = Item.BUGINIUM_Z;
                        break;
                    case ROCK:
                        zMoveItem = Item.ROCKIUM_Z;
                        break;
                    case GHOST:
                        zMoveItem = Item.GHOSTIUM_Z;
                        break;
                    case DRAGON:
                        zMoveItem = Item.DRAGONIUM_Z;
                        break;
                    case DARK:
                        zMoveItem = Item.DARKINIUM_Z;
                        break;
                    case STEEL:
                        zMoveItem = Item.STEELIUM_Z;
                        break;
                    case FAIRY:
                        zMoveItem = Item.FAIRIUM_Z;
                        break;
                }
            }

            String[] move = {
                    name, // Name
                    type.getName(), // Type
                    category.getName(), // Category
                    String.valueOf(pp), // PP
                    String.valueOf(power), // Power
                    String.valueOf(accuracy), // Accuracy
                    battleEffect, // Battle Effect
                    inDepthEffect == null ? "" : String.valueOf(inDepthEffect), // In-Depth Effect
                    secondaryEffect, // Secondary Effect
                    effectRate != null ? String.valueOf(effectRate) : "", // Effect Rate
                    String.valueOf(isSelfZMove), // Is Self Z-Move?
                    correspondingZMove, // Corresponding Z-Move
                    zMoveItem != null ? zMoveItem.getName() : "", // Z-Move Item
                    detailedEffect != null ? detailedEffect : "", // Detailed Effect
                    zMovePower != null ? String.valueOf(zMovePower) : "", // Z-Move Power
                    !isSelfZMove && correspondingZMove == null ? "true" : "false", // Is Z-Move?
                    zMoveRequiredPokemon.isEmpty() ? "" : zMoveRequiredPokemon.stream().collect(Collectors.joining(",")), // Z-Move Required Pokemon
                    zMoveRequiredMove.isEmpty() ? "" : zMoveRequiredMove.stream().map(BaseMove::getName).collect(Collectors.joining(",")), // Z-Move Required Moves
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
