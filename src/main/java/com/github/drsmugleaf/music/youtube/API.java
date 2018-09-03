package com.github.drsmugleaf.music.youtube;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.env.Keys;
import com.github.drsmugleaf.music.SearchErrorException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;

/**
 * Created by DrSmugleaf on 04/09/2017.
 */
public class API {

    @Nullable
    public static SearchResult search(String query) throws SearchErrorException {
        YouTube youtube = new YouTube.Builder(
                new NetHttpTransport(),
                new JacksonFactory(),
                httpRequest -> {}
        ).setApplicationName("youtube-cmdline-search").build();

        YouTube.Search.List request;
        try {
            request = youtube.search().list("id");
        } catch (IOException e) {
            BanterBot4J.LOGGER.error("Error creating search for youtube video: " + query, e);
            throw new SearchErrorException(e);
        }

        request.setKey(Keys.GOOGLE_KEY.VALUE);
        request.setQ(query);
        request.setType("video");
        request.setMaxResults(1L);

        SearchListResponse searchResponse;
        try {
            searchResponse = request.execute();
        } catch (IOException e) {
            BanterBot4J.LOGGER.error("Error executing search for youtube video: " + query, e);
            throw new SearchErrorException(e);
        }

        List<SearchResult> searchItems = searchResponse.getItems();
        if (searchItems.size() == 0) {
            BanterBot4J.LOGGER.warn("No search results found for youtube video: " + query);
            return null;
        }

        return searchItems.get(0);
    }

}
