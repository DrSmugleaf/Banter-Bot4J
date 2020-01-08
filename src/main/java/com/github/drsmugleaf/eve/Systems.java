package com.github.drsmugleaf.eve;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 20/05/2018.
 */
public class Systems {

    private static final String STARGATES_FILE_NAME = "./src/main/java/com/github/drsmugleaf/eve/stargates.csv";
    private static final String SYSTEMS_FILE_NAME = "./src/main/java/com/github/drsmugleaf/eve/systems.csv";
    public static final Multimap<Integer, Integer> CONNECTIONS = ArrayListMultimap.create();
    public static final Map<Integer, String> NAMES = new HashMap<>();

    static {
        try (
                CSVReader stargates = new CSVReaderBuilder(new FileReader(STARGATES_FILE_NAME)).withSkipLines(1).build();
                CSVReader systems = new CSVReaderBuilder(new FileReader(SYSTEMS_FILE_NAME)).withSkipLines(1).build()
        ) {
            String[] nextLine;

            while ((nextLine = stargates.readNext()) != null) {
                Integer from = Integer.parseInt(nextLine[0]);
                Integer to = Integer.parseInt(nextLine[1]);
                CONNECTIONS.put(from, to);
            }

            while ((nextLine = systems.readNext()) != null) {
                Integer id = Integer.parseInt(nextLine[0]);
                String name = nextLine[1];
                NAMES.put(id, name);
            }
        } catch (FileNotFoundException e) {
            EVE.LOGGER.error("File " + STARGATES_FILE_NAME + " doesn't exist", e);
            System.exit(1);
        } catch (IOException e) {
            EVE.LOGGER.error("Error reading csv " + STARGATES_FILE_NAME, e);
            System.exit(1);
        }
    }

}
