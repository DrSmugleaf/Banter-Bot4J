package com.github.drsmugleaf.article13.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.CSVReaderHeaderAwareBuilder;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.Contract;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.*;

/**
 * Created by DrSmugleaf on 08/04/2019
 */
public class Sheet extends File {

    private static final String SHEETS_PATH = Objects.requireNonNull(Sheet.class.getClassLoader().getResource("article13/votes")).getFile();

    public Sheet(String pathname) {
        super(pathname);
    }

    public Sheet(String parent, String child) {
        super(parent, child);
    }

    public Sheet(File parent, String child) {
        super(parent, child);
    }

    public Sheet(URI uri) {
        super(uri);
    }

    public Sheet(File file) {
        super(file.toURI());
    }

    @Contract(pure = true)
    private static String getSheetsPath() {
        return SHEETS_PATH;
    }

    @Contract(" -> new")
    private static File getSheetsFolder() {
        return new File(getSheetsPath());
    }

    public static List<Sheet> getVoteSheets() {
        File[] files = getSheetsFolder().listFiles(file -> file.isFile() && file.getName().endsWith(".csv"));
        if (files == null) {
            throw new IllegalStateException("Error reading article 13 vote files");
        }

        List<Sheet> sheets = new ArrayList<>();
        for (File file : files) {
            sheets.add(new Sheet(file));
        }

        return sheets;
    }

    public String getSheetName() {
        return FilenameUtils.removeExtension(this.getName());
    }

    public List<Map<String, String>> read() {
        List<Map<String, String>> lines = new ArrayList<>();

        try (
                FileReader fileReader = new FileReader(this);
                CSVReaderHeaderAware reader = new CSVReaderHeaderAwareBuilder(fileReader).build()
        ) {
            Map<String, String> line;
            while ((line = reader.readMap()) != null) {
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found: " + this, e);
        } catch (IOException e) {
            throw new IllegalStateException("Error closing resources", e);
        }

        return lines;
    }

    public Set<String> getHeaders() {
        Set<String> headers;

        try (
                FileReader fileReader = new FileReader(this);
                CSVReader reader = new CSVReaderBuilder(fileReader).build()
        ) {
            headers = new HashSet<>(Arrays.asList(reader.readNext()));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found: " + this, e);
        } catch (IOException e) {
            throw new IllegalStateException("Error closing resources", e);
        }

        return headers;
    }

}
