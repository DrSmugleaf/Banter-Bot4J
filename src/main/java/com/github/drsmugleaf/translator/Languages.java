package com.github.drsmugleaf.translator;

import com.github.drsmugleaf.Nullable;
import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 18/01/2018.
 */
public enum Languages {

    AFRIKAANS("Afrikaans", "af"),
    ARABIC("Arabic", "ar"),
    BANGLA("Bangla", "bn"),
    BOSNIAN_LATIN("Bosnian (Latin)", "bs"),
    BULGARIAN("Bulgarian", "bg"),
    CANTONESE_TRADITIONAL("Cantonese (Traditional)", "yue"),
    CATALAN("Catalan", "ca"),
    CHINESE_SIMPLIFIED("Chinese Simplified", "zh-Hans"),
    CHINESE_TRADITIONAL("Chinese Traditional", "zh-Hant"),
    CROATIAN("Croatian", "hr"),
    CZECH("Czech", "cs"),
    DANISH("Danish", "da"),
    DUTCH("Dutch", "nl"),
    ENGLISH("English", "en"),
    ESTONIAN("Estonian", "et"),
    FIJIAN("Fijian", "fj"),
    FILIPINO("Filipino", "fil"),
    FINNISH("Finnish", "fi"),
    FRENCH("French", "fr"),
    GERMAN("German", "de"),
    GREEK("Greek", "el"),
    HAITIAN_CREOLE("Haitian Creole", "ht"),
    HEBREW("Hebrew", "he"),
    HINDI("Hindi", "hi"),
    HMONG_DAW("Hmong Daw", "mww"),
    HUNGARIAN("Hungarian", "hu"),
    INDONESIAN("Indonesian", "id"),
    ITALIAN("Italian", "it"),
    JAPANESE("Japanese", "ja"),
    KISWAHILI("Kiswahili", "sw"),
    KLINGON("Klingon", "tlh"),
    KLINGON_PLQAD("Klingon (plqaD)", "tlh-Qaak"),
    KOREAN("Korean", "ko"),
    LATVIAN("Latvian", "lv"),
    LITHUANIAN("Lithuanian", "lt"),
    MALAGASY("Malagasy", "mg"),
    MALAY("Malay", "ms"),
    MALTESE("Maltese", "mt"),
    NORWEGIAN("Norwegian", "nb"),
    PERSIAN("Persian", "fa"),
    POLISH("Polish", "pl"),
    PORTUGUESE("Portuguese", "pt"),
    QUERETARO_OTOMI("Queretaro Otomi", "otq"),
    ROMANIAN("Romanian", "ro"),
    RUSSIAN("Russian", "ru"),
    SAMOAN("Samoan", "sm"),
    SERBIAN_CYRILLIC("Serbian (Cyrillic)", "sr-Cyrl"),
    SERBIAN_LATIN("Serbian (Latin)", "sr-Latn"),
    SLOVAK("Slovak", "sk"),
    SLOVENIAN("Slovenian", "sl"),
    SPANISH("Spanish", "es"),
    SWEDISH("Swedish", "sv"),
    TAHITIAN("Tahitian", "ty"),
    TAMIL("Tamil", "ta"),
    THAI("Thai", "th"),
    TONGAN("Tongan", "to"),
    TURKISH("Turkish", "tr"),
    UKRAINIAN("Ukrainian", "uk"),
    URDU("Urdu", "ur"),
    VIETNAMESE("Vietnamese", "vi"),
    WELSH("Welsh", "cy"),
    YUCATEC_MAYA("Yucatec Maya", "yua");

    private final String NAME;
    private final String CODE;

    Languages(String name, String code) {
        NAME = name;
        CODE = code;
        Holder.MAP.put(NAME.toLowerCase(), this);
        Holder.MAP.put(CODE.toLowerCase(), this);
    }

    @Nullable
    public static Languages getLanguage(String name) {
        return Holder.MAP.get(name.toLowerCase());
    }

    @Contract(pure = true)

    public String getName() {
        return NAME;
    }

    @Contract(pure = true)

    public String getCode() {
        return CODE;
    }

    private static class Holder {
        static final Map<String, Languages> MAP = new HashMap<>();
    }

}
