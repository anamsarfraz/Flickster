package com.codepath.flickster.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.flickster.R;

import com.codepath.flickster.adapters.YoutubeVideoArrayAdapter;
import com.codepath.flickster.models.Movie;
import com.codepath.flickster.models.YoutubeVideo;
import com.codepath.flickster.util.Constants;
import com.codepath.flickster.util.NetworkUtil;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.codepath.flickster.util.Constants.RELATED_TO_VIDEO_STR;
import static java.security.AccessController.getContext;

public class PlayYoutubeActivity extends YouTubeBaseActivity {

    int movieId;
    boolean doPlayVideo;
    String youTubeId;
    ArrayList<YoutubeVideo> videos;
    YoutubeVideoArrayAdapter videoArrayAdapter;

    @BindView(R.id.player) YouTubePlayerView youTubePlayerView;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_you_tube);
        ButterKnife.bind(this);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            lvItems = (ListView) findViewById(R.id.lvVideos);
            videos = new ArrayList<>();
            videoArrayAdapter = new YoutubeVideoArrayAdapter(this, videos);
            lvItems.setAdapter(videoArrayAdapter);

        }


        movieId = getIntent().getIntExtra("movieId", 0);
        doPlayVideo = getIntent().getBooleanExtra("doPlayVideo", false);



        youTubePlayerView.initialize(Constants.YOU_TUBE_API_KEY_VAL,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        getTrailer(youTubePlayer);

                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {
                        Toast.makeText(PlayYoutubeActivity.this, "Youtube Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    protected void getTrailer(final YouTubePlayer youTubePlayer) {
        NetworkUtil.getResponse(String.format(Constants.TRAILER_URL, movieId),
                Constants.movieAPIParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            throw new IOException("Unexpected code " + response);
                        }

                        JSONArray trailerJsonResults = null;

                        try {
                            String responseData = response.body().string();
                            JSONObject responseJson = new JSONObject(responseData);
                            trailerJsonResults = responseJson.getJSONArray("youtube");
                            JSONObject trailerJson = trailerJsonResults.getJSONObject(0);
                            youTubeId = trailerJson.optString("source", "");
                            int orientation = getResources().getConfiguration().orientation;
                            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                                getRelatedVideos(youTubePlayer);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // Run view-related code back on the main thread
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Cue or play video based on the passed in flag
                                if (doPlayVideo) {
                                    youTubePlayer.loadVideo(youTubeId);

                                } else {
                                    youTubePlayer.cueVideo(youTubeId);
                                }

                            }
                        });
                    }
                });
    }

    protected void getRelatedVideos(final YouTubePlayer youTubePlayer) {
        Constants.youtubeAPIParams.put(RELATED_TO_VIDEO_STR, youTubeId);
        NetworkUtil.getResponse(Constants.YOU_TUBE_RELATED_URL,
                Constants.youtubeAPIParams, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            throw new IOException("Unexpected code " + response);
                        }

                        JSONArray videosJsonResults = null;

                        try {
                            String responseData = response.body().string();
                            JSONObject responseJson = new JSONObject(responseData);
                            videosJsonResults = responseJson.getJSONArray("items");
                            videos.addAll(YoutubeVideo.fromJsonArray(videosJsonResults));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // Run view-related code back on the main thread
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                videoArrayAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
    }

}
