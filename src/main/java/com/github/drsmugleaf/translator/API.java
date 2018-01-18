package com.github.drsmugleaf.translator;

import com.github.drsmugleaf.env.Env;
import com.github.drsmugleaf.env.Keys;

/**
 * Created by DrSmugleaf on 18/01/2018.
 */
public class API {

    private static final String KEY = Env.get(Keys.AZURE_TRANSLATOR_KEY);
    private static final String PATH = "https://api.microsofttranslator.com/V2/Http.svc/Translate";

}
