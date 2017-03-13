package com.codepath.flickster.adapters;

import android.content.Context;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flickster.R;
import com.codepath.flickster.models.Movie;
import com.codepath.flickster.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    // Movie view lookup cache
    static class ViewHolderMovieInfo {
        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.tvOverview) TextView tvOverview;
        @BindView(R.id.ivMovieImage) ImageView ivImage;

        public ViewHolderMovieInfo(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderMovieBackdrop {
        @BindView(R.id.ivMovieBackdrop) ImageView ivImage;

        public ViewHolderMovieBackdrop(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);

    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getPopularity().ordinal();
    }

    @Override
    public int getViewTypeCount() {
        return Movie.Popularity.values().length;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for position
        Movie movie = getItem(position);

        String moviePath;
        int movieImagePlaceholder;
        int viewType = this.getItemViewType(position);

        if (viewType == Movie.Popularity.NOT_POPULAR.ordinal()) {
            // Check to see if existing view is being reused, otherwise inflate the view
            ViewHolderMovieInfo viewHolder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater)getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_movie_info, parent, false);

                viewHolder = new ViewHolderMovieInfo(convertView);
                // Clear out the image from convertView
                viewHolder.ivImage.setImageResource(0);

                // Cache the viewholder object inside the fresh view
                convertView.setTag(viewHolder);
            } else {
                // View is being recycled. Retrieve the viewHolder object from tag
                viewHolder = (ViewHolderMovieInfo) convertView.getTag();
            }

            // Populate the data from the data object via the viewHolder object into the template view
            String movieOverview = movie.getOverview();
            int cutOff = movieOverview.indexOf(' ', 140);
            viewHolder.tvTitle.setText(movie.getOriginalTitle());
            viewHolder.tvOverview.setText(String.format("%s%s",
                    movieOverview.substring(0, cutOff), Constants.MORE_STR));



            int orientation = getContext().getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                moviePath = movie.getPosterPath("w342");
                movieImagePlaceholder = R.drawable.movie_reel_potrait;
            } else {
                moviePath = movie.getBackdropPath("w780");
                movieImagePlaceholder = R.drawable.movie_reel_land;
            }

            Picasso.with(getContext()).load(moviePath)
                    .placeholder(movieImagePlaceholder)
                    .transform(new RoundedCornersTransformation(10, 10))
                    .into(viewHolder.ivImage);
        } else if (viewType == Movie.Popularity.POPULAR.ordinal()) {
            // Check to see if existing view is being reused, otherwise inflate the view
            ViewHolderMovieBackdrop viewHolder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater)getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_movie_backdrop, parent, false);
                viewHolder = new ViewHolderMovieBackdrop(convertView);
                // Clear out the image from convertView
                viewHolder.ivImage.setImageResource(0);

                // Cache the viewholder object inside the fresh view
                convertView.setTag(viewHolder);
            } else {
                // View is being recycled. Retrieve the viewHolder object from tag
                viewHolder = (ViewHolderMovieBackdrop) convertView.getTag();
            }
            // Update the image of the popular movie
            moviePath = movie.getBackdropPath("w780");
            movieImagePlaceholder = R.drawable.movie_reel_backdrop;
            Picasso.with(getContext()).load(moviePath)
                    .placeholder(movieImagePlaceholder)
                    .resize(parent.getWidth(), 0)
                    .transform(new RoundedCornersTransformation(10, 10))
                    .into(viewHolder.ivImage);
        } else {
            throw new IllegalArgumentException("Unsupported view type");
        }


        // return the completed view to render on screen
        return convertView;

    }
}
