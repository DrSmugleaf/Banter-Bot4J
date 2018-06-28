package com.github.drsmugleaf.tripwire.session;

import javax.annotation.Nonnull;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by DrSmugleaf on 18/05/2018.
 */
public class Session {

    @Nonnull
    public final long ID;

    @Nonnull
    public final String NAME;

    @Nonnull
    public final Map<String, String> COOKIES = new HashMap<>();

    @Nonnull
    public final String VERSION;

    @Nonnull
    public final String INSTANCE = getInstance();

    Session(long id, @Nonnull String name, @Nonnull Map<String, String> cookies, @Nonnull String version) {
        ID = id;
        NAME = name;
        COOKIES.putAll(cookies);
        VERSION = version;
    }

    private static String getInstance() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.UK);
        DecimalFormat df = new DecimalFormat("#");
        df.setDecimalFormatSymbols(symbols);
        df.setMaximumFractionDigits(3);

        double unixSeconds = System.currentTimeMillis() / 1000.0;
        return df.format(unixSeconds);
    }

}
