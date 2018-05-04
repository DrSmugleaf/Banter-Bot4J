package com.github.drsmugleaf.eve.api;

import com.google.gson.JsonElement;

import javax.annotation.Nonnull;

/**
 * Created by DrSmugleaf on 04/05/2018.
 */
public class Bookmarks {

    @Nonnull
    public static JsonElement getCharacterBookmarks(long characterID) {
        return API.getResponse("characters/" + characterID + "/bookmarks/");
    }

    @Nonnull
    public static JsonElement getCharacterBookmarkFolders(long characterID) {
        return API.getResponse("characters/" + characterID + "/bookmarks/folders/");
    }

    @Nonnull
    public static JsonElement getCorporationBookmarks(long corporationID) {
        return API.getResponse("corporations/" + corporationID + "/bookmarks/");
    }

    @Nonnull
    public static JsonElement getCorporationBookmarkFolders(long corporationID) {
        return API.getResponse("corporations/" + corporationID + "/bookmarks/folders/");
    }

}
