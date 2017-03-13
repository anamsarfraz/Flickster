package com.codepath.flickster.models;

import com.codepath.flickster.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;


public class Movie implements Serializable {

    public enum Popularity {
        NOT_POPULAR, POPULAR
    }

    private int movieId;
    private String posterPath;
    private String backdropPath;
    private String originalTitle;
    private String overview;
    private float voteAverage;
    private Popularity popularity;
    private String releaseDate;

    public Movie(JSONObject jsonObject) throws JSONException {
        this.movieId = jsonObject.optInt("id", 0);
        this.posterPath = jsonObject.optString("poster_path", "");
        this.backdropPath = jsonObject.optString("backdrop_path", "");
        this.originalTitle = jsonObject.optString("original_title", "");
        this.overview = jsonObject.optString("overview");
        this.voteAverage = (float)jsonObject.optDouble("vote_average", 0.0);
        this.popularity = this.voteAverage > 5.0 ? Popularity.POPULAR : Popularity.NOT_POPULAR;
        this.releaseDate = jsonObject.optString("release_date", "");
    }

    public static ArrayList<Movie> fromJsonArray(JSONArray array) {
        ArrayList<Movie> results = new ArrayList<>();

        for (int i=0; i < array.length(); i++) {
            try {
                results.add(new Movie(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getPosterPath(String size) {
        return String.format(Constants.IMAGE_URL,size, posterPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getBackdropPath(String size) {
        return String.format(Constants.IMAGE_URL, size, backdropPath);
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public Popularity getPopularity() {
        return popularity;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
