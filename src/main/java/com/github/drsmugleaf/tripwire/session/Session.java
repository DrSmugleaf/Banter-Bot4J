package com.github.drsmugleaf.tripwire.session;

import org.jetbrains.annotations.NotNull;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by DrSmugleaf on 18/05/2018.
 */
public class Session {

    @NotNull
    public final long ID;

    @NotNull
    public final String NAME;

    @NotNull
    public final Map<String, String> COOKIES = new HashMap<>();

    @NotNull
    public final String VERSION;

    @NotNull
    public final String INSTANCE = getInstance();

    Session(long id, @NotNull String name, @NotNull Map<String, String> cookies, @NotNull String version) {
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
