package com.codepath.flickster.util;

import java.net.URL;
import java.util.HashMap;

/**
 * Created by usarfraz on 3/11/17.
 */

public class Constants {
    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing";
    public static final String TRAILER_URL = "https://api.themoviedb.org/3/movie/%d/trailers";
    public static final String API_KEY_STR = "api_key";
    public static final String API_KEY_VAL = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String IMAGE_URL = "https://image.tmdb.org/t/p/%s/%s";
    public static final String KEY_STR = "key";
    public static final String YOU_TUBE_API_KEY_VAL = "AIzaSyA8TlIMDQfTG3fcFUdI9peHefpo6DXqfDg";
    public static final String YOU_TUBE_RELATED_URL = "https://www.googleapis.com/youtube/v3/search";
    public static final String PART_STR = "part";
    public static final String SNIPPET_STR = "snippet";
    public static final String RELATED_TO_VIDEO_STR = "relatedToVideoId";
    public static final String TYPE_STR = "type";
    public static final String VIDEO_STR = "video";
    public static final String PUBLISHED = "Published on";
    public static final String RELEASE_DATE = "Release Date";
    public static final String MORE_STR = "...";

    public static final HashMap<String, String> movieAPIParams = new HashMap<String, String>() {{
        put(Constants.API_KEY_STR, Constants.API_KEY_VAL);
    }};

    public static HashMap<String, String> youtubeAPIParams = new HashMap<String, String>() {{
        put(Constants.KEY_STR, Constants.YOU_TUBE_API_KEY_VAL);
        put(PART_STR, SNIPPET_STR);
        put(RELATED_TO_VIDEO_STR, "5rOiW_xY-kc");
        put(TYPE_STR, VIDEO_STR);
    }};
}
