package com.codepath.flickster.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.flickster.R;
import com.codepath.flickster.adapters.MovieArrayAdapter;
import com.codepath.flickster.models.Movie;
import com.codepath.flickster.util.Constants;
import com.codepath.flickster.util.NetworkUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;



public class MovieActivity extends AppCompatActivity {
    ArrayList<Movie> movies;
    MovieArrayAdapter movieAdapter;
    @BindView(R.id.lvMovies) ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
        movies = new ArrayList<>();
        movieAdapter = new MovieArrayAdapter(this, movies);
        lvItems.setAdapter(movieAdapter);
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?>parent, View view, int position, long id) {
                Movie movie = movieAdapter.getItem(position);
                Intent intent;
                if (movie.getPopularity() == Movie.Popularity.NOT_POPULAR) {
                    intent = new Intent(MovieActivity.this, MovieDetailActivity.class);
                    intent.putExtra("movie", movie);
                } else {
                    intent = new Intent(MovieActivity.this, PlayYoutubeActivity.class);
                    intent.putExtra("movieId", movie.getMovieId());
                    intent.putExtra("doPlayVideo", true);
                }
                startActivity(intent);

            }
        });
        getMovies();
    }

    protected void getMovies() {
        NetworkUtil.getResponse(Constants.NOW_PLAYING_URL, Constants.movieAPIParams, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                JSONArray movieJsonResults = null;

                try {
                    String responseData = response.body().string();
                    JSONObject responseJson = new JSONObject(responseData);
                    movieJsonResults = responseJson.getJSONArray("results");
                    movies.addAll(Movie.fromJsonArray(movieJsonResults));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Run view-related code back on the main thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        movieAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

}
