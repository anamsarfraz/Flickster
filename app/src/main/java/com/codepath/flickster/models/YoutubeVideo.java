package com.codepath.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class YoutubeVideo implements Serializable {
    String videoId;
    String title;
    String channelTitle;
    String publishedAt;
    String thumbnailUrl;

    public String getVideoId() {
        return videoId;
    }

    public String getTitle() {
        return title;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public YoutubeVideo(JSONObject jsonObject) throws JSONException {
        this.videoId = jsonObject.getJSONObject("id").optString("videoId", "");
        this.title = jsonObject.getJSONObject("snippet").optString("title", "");
        this.channelTitle = jsonObject.getJSONObject("snippet").optString("channelTitle", "");
        this.publishedAt = jsonObject.getJSONObject("snippet").optString("publishedAt", "");
        this.thumbnailUrl = jsonObject.getJSONObject("snippet").getJSONObject("thumbnails")
                .getJSONObject("high").optString("url", "");
    }

    public static ArrayList<YoutubeVideo> fromJsonArray(JSONArray array) {
        ArrayList<YoutubeVideo> results = new ArrayList<>();

        for (int i=0; i < array.length(); i++) {
            try {
                results.add(new YoutubeVideo(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}
