package com.github.drsmugleaf.tripwire.session;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by DrSmugleaf on 18/05/2018.
 */
public class Session {

    public final long ID;
    public final String NAME;
    public final Map<String, String> COOKIES = new HashMap<>();
    public final String VERSION;
    public final String INSTANCE = getInstance();

    Session(long id, String name, Map<String, String> cookies, String version) {
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
