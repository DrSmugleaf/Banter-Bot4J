package com.github.drsmugleaf.deadbydaylight.dennisreep;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by DrSmugleaf on 19/04/2019
 */
public class Survivor implements ICharacter {

    private final String NAME;
    private final String IMAGE_URL;

    public Survivor(String name, String imageURL) {
        NAME = name;
        IMAGE_URL = imageURL;
    }

    public String getName() {
        return NAME;
    }

    public String getImageURL() {
        return IMAGE_URL;
    }

    public InputStream getImage() {
        File file = new File(IMAGE_URL);

        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            BanterBot4J.warn("No file found with path " + file.getAbsolutePath());
            return API.getDBDLogo();
        }
    }

    @Nullable
    @Override
    public Double getRating() {
        return null;
    }

}
