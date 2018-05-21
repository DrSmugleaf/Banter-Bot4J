package com.github.drsmugleaf.eve;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 20/05/2018.
 */
public class Systems {

    @Nonnull
    private static final String FILE_NAME = "./src/main/java/com/github/drsmugleaf/eve/stargates.csv";

    @Nonnull
    public static final Multimap<Integer, Integer> CONNECTIONS = ArrayListMultimap.create();

    @Nonnull
    public static final Map<Integer, String> NAMES = new HashMap<>();

    static {
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(FILE_NAME)).withSkipLines(1).build()) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                Integer from = Integer.parseInt(nextLine[1]);
                NAMES.put(from, nextLine[0]);
                Integer to = Integer.parseInt(nextLine[3]);
                NAMES.put(to, nextLine[2]);

                CONNECTIONS.put(from, to);
            }
        } catch (FileNotFoundException e) {
            EVE.LOGGER.error("File " + FILE_NAME + " doesn't exist", e);
            System.exit(1);
        } catch (IOException e) {
            EVE.LOGGER.error("Error reading csv " + FILE_NAME, e);
            System.exit(1);
        }
    }

}
