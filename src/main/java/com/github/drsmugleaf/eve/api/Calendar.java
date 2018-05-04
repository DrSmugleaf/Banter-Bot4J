package com.github.drsmugleaf.eve.api;

import com.google.gson.JsonElement;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DrSmugleaf on 04/05/2018.
 */
public class Calendar {

    @Nonnull
    public static JsonElement getCharacterCalendar(long characterID) {
        return API.getResponse("characters/" + characterID + "/calendar/");
    }

    @Nonnull
    public static JsonElement getCharacterCalendarEvent(long characterID, long eventID) {
        return API.getResponse("characters/" + characterID + "/calendar/" + eventID + "/");
    }

    @Nonnull
    public static JsonElement setCharacterCalendarEventResponse(long characterID, long eventID, @Nonnull String response) {
        Map<String, String> properties = new HashMap<>();
        properties.put("response", response);
        return API.getResponse("characters/" + characterID + "/calendar/" + eventID + "/", properties, "POST");
    }

    @Nonnull
    public static JsonElement getCharacterCalendarEventAttendees(long characterID, long eventID) {
        return API.getResponse("characters/" + characterID + "/orders/" + eventID + "/attendees/");
    }

}
