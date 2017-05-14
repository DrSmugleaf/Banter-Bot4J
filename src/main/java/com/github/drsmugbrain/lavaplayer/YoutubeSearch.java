package com.github.drsmugbrain.lavaplayer;

import com.github.drsmugbrain.EnvVariables;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
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

    private static YouTube youtube;

    public static String search(String query) {
        try {
            youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest httpRequest) throws IOException {
                }
            }).setApplicationName("youtube-cmdline-search").build();

            YouTube.Search.List search = youtube.search().list("snippet");

            String apiKey = EnvVariables.getEnvVariables().get("googleKey");
            search.setKey(apiKey);
            search.setQ(query);

            search.setType("video");

            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);

            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            if(searchResultList != null) {
                String url = "https://www.youtube.com/watch?v=" + searchResultList.get(0).getId().getVideoId();
                return url;
//                prettyPrint(searchResultList.iterator(), query);
            }
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return null;
    }

}
