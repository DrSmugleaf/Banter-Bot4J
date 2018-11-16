package com.github.drsmugleaf.translator;

import com.github.drsmugleaf.env.Keys;
import org.json.XML;

import org.jetbrains.annotations.Nullable;
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

    @Nullable
    public static String translate(Languages from, Languages to, String text) {
        if (from == to) {
            return text;
        }

        try {
            text = URLEncoder.encode(text, "UTF8");
            URL url = new URL(PATH + "?from=" + from.getCode() + "&to=" + to.getCode() + "&text=" + text);

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty(KEY_PROPERTY, Keys.AZURE_TRANSLATOR_KEY.VALUE);

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
