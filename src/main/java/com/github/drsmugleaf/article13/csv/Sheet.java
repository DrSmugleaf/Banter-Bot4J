package com.github.drsmugleaf.article13.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.CSVReaderHeaderAwareBuilder;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
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

    @Nonnull
    private static final String SHEETS_PATH = Objects.requireNonNull(Sheet.class.getClassLoader().getResource("article13/votes")).getFile();

    public Sheet(@NotNull String pathname) {
        super(pathname);
    }

    public Sheet(String parent, @NotNull String child) {
        super(parent, child);
    }

    public Sheet(File parent, @NotNull String child) {
        super(parent, child);
    }

    public Sheet(@NotNull URI uri) {
        super(uri);
    }

    public Sheet(@Nonnull File file) {
        super(file.toURI());
    }

    @Nonnull
    private static String getSheetsPath() {
        return SHEETS_PATH;
    }

    @Nonnull
    private static File getSheetsFolder() {
        return new File(getSheetsPath());
    }

    @Nonnull
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

    @Nonnull
    public String getSheetName() {
        return FilenameUtils.removeExtension(this.getName());
    }

    @Nonnull
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

    @Nonnull
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
