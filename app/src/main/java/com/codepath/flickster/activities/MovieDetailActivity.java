package com.codepath.flickster.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.flickster.R;
import com.codepath.flickster.models.Movie;
import com.codepath.flickster.util.Constants;
import com.codepath.flickster.util.DateUtil;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static com.codepath.flickster.util.DateUtil.parseReleaseDate;


public class MovieDetailActivity extends AppCompatActivity {

    Movie movie;
    @BindView(R.id.ivMovieDetailImage) ImageView ivMovieImage;
    @BindView(R.id.tvDetailTitle) TextView tvTitle;
    @BindView(R.id.tvReleaseDate) TextView tvReleaseDate;
    @BindView(R.id.rbMovieRating) RatingBar rbMovieRating;
    @BindView(R.id.tvSynopsis) TextView tvSnopsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        movie = (Movie) getIntent().getSerializableExtra("movie");

        Picasso.with(this).load(movie.getBackdropPath("w780"))
                    .placeholder(R.drawable.movie_reel_land)
                    .transform(new RoundedCornersTransformation(10, 10))
            .into(ivMovieImage);

        tvTitle.setText(movie.getOriginalTitle());
        tvReleaseDate.setText(String.format("%s: %s",
                Constants.RELEASE_DATE, DateUtil.parseReleaseDate(movie.getReleaseDate())));
        rbMovieRating.setRating(movie.getVoteAverage());
        tvSnopsis.setText(movie.getOverview());

        ivMovieImage.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                Intent intent = new Intent(MovieDetailActivity.this, PlayYoutubeActivity.class);
                intent.putExtra("movieId", movie.getMovieId());
                intent.putExtra("doPlayVideo", false);
                startActivity(intent);
            }
        });
    }
}
