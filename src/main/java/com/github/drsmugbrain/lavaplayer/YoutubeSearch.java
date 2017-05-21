package com.github.drsmugbrain.lavaplayer;

import com.github.drsmugbrain.util.Bot;
import com.github.drsmugbrain.util.Env;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by DrSmugleaf on 13/05/2017.
 */
public class YoutubeSearch {

    private static final long NUMBER_OF_VIDEOS_RETURNED = 1;

    public static String search(String query) {
        try {
            YouTube youtube = new YouTube.Builder(
                    new NetHttpTransport(),
                    new JacksonFactory(),
                    httpRequest -> {}
            ).setApplicationName("youtube-cmdline-search").build();

            YouTube.Search.List search = youtube.search().list("snippet");

            String apiKey = Env.readFile().get("GOOGLE_KEY");
            search.setKey(apiKey);
            search.setQ(query);

            search.setType("video");

            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);

            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            if(searchResultList != null) {
                return "https://www.youtube.com/watch?v=" + searchResultList.get(0).getId().getVideoId();
            }
        } catch (GoogleJsonResponseException e) {
            Bot.LOGGER.error("Service error", e);
        } catch (IOException e) {
            Bot.LOGGER.error("IO Error", e);
        } catch (Throwable t) {
            Bot.LOGGER.error("Generic error", t);
        }

        return null;
    }

}
