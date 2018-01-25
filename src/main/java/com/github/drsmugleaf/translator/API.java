package com.github.drsmugleaf.translator;

import com.github.drsmugleaf.env.Env;
import com.github.drsmugleaf.env.Keys;
import org.json.XML;

import javax.annotation.Nullable;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by DrSmugleaf on 18/01/2018.
 */
public class API {

    private static final String PATH = "https://api.microsofttranslator.com/V2/Http.svc/Translate";
    private static final String KEY_PROPERTY = "Ocp-Apim-Subscription-Key";
    private static final String KEY = Env.get(Keys.AZURE_TRANSLATOR_KEY);

    @Nullable
    public static String translate(String from, String to, String text) {
        if (KEY == null) {
            throw new IllegalStateException("Azure Translator Key not found.");
        }

        try {
            text = URLEncoder.encode(text, "UTF8");
            URL url = new URL(PATH + "?from=" + from + "&to=" + to + "&text=" + text);

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty(KEY_PROPERTY, KEY);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            return XML.toJSONObject(builder.toString()).getJSONObject("string").getString("content");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
