package com.github.drsmugleaf.eve;

import com.google.common.collect.*;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

/**
 * Created by DrSmugleaf on 20/05/2018.
 */
public class EVE {

    private static final Logger LOGGER = LoggerFactory.getLogger(EVE.class);
    private static final String EVE_RESOURCES_PATH = Objects.requireNonNull(EVE.class.getClassLoader().getResource("eve")).getFile();
    private static final ImmutableMultimap<Integer, Integer> STARGATES = parseStargates();
    private static final ImmutableBiMap<Integer, String> SYSTEMS = parseSystems();

    private static ImmutableMultimap<Integer, Integer> parseStargates() {
        Multimap<Integer, Integer> map = ArrayListMultimap.create();
        String path = EVE_RESOURCES_PATH + "/stargates.csv";
        try (
                FileReader fileReader = new FileReader(path);
                CSVReader stargates = new CSVReaderBuilder(fileReader).withSkipLines(1).build()
        ) {
            String[] nextLine;

            while ((nextLine = stargates.readNext()) != null) {
                Integer from = Integer.parseInt(nextLine[0]);
                Integer to = Integer.parseInt(nextLine[1]);
                map.put(from, to);
            }
        } catch (FileNotFoundException e) {
            EVE.LOGGER.error("File " + path + " doesn't exist", e);
            System.exit(1);
        } catch (IOException e) {
            EVE.LOGGER.error("Error reading file " + path, e);
            System.exit(1);
        }

        return ImmutableMultimap.copyOf(map);
    }

    private static ImmutableBiMap<Integer, String> parseSystems() {
        BiMap<Integer, String> map = HashBiMap.create();
        String path = EVE_RESOURCES_PATH + "/systems.csv";
        try (
                FileReader fileReader = new FileReader(path);
                CSVReader systems = new CSVReaderBuilder(fileReader).withSkipLines(1).build()
        ) {
            String[] nextLine;

            while ((nextLine = systems.readNext()) != null) {
                Integer id = Integer.parseInt(nextLine[0]);
                String name = nextLine[1];
                map.put(id, name);
            }
        } catch (FileNotFoundException e) {
            EVE.LOGGER.error("File " + path + " doesn't exist", e);
            System.exit(1);
        } catch (IOException e) {
            EVE.LOGGER.error("Error reading file " + path, e);
            System.exit(1);
        }

        return ImmutableBiMap.copyOf(map);
    }

    public static ImmutableMultimap<Integer, Integer> getStargates() {
        return STARGATES;
    }

    public static ImmutableBiMap<Integer, String> getSystems() {
        return SYSTEMS;
    }

}
