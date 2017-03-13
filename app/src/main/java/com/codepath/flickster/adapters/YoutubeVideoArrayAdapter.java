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
import com.codepath.flickster.models.YoutubeVideo;
import com.codepath.flickster.util.Constants;
import com.codepath.flickster.util.DateUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class YoutubeVideoArrayAdapter extends ArrayAdapter<YoutubeVideo> {
    // Video view lookup cache
    static class ViewHolder {
        @BindView(R.id.tvVideoTitle) TextView tvVideoTitle;
        @BindView(R.id.tvChannelTitle) TextView tvChannelTitle;
        @BindView(R.id.ivVideoImage) ImageView ivVideoImage;
        @BindView(R.id.tvPulishDate) TextView tvPublishDate;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public YoutubeVideoArrayAdapter(Context context, List<YoutubeVideo> videos) {
        super(context, android.R.layout.simple_list_item_1, videos);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for position
        YoutubeVideo video = getItem(position);


        // Check to see if existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_video_info, parent, false);

            viewHolder = new ViewHolder(convertView);
            // Clear out the image from convertView
            viewHolder.ivVideoImage.setImageResource(0);

            // Cache the viewholder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled. Retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data from the data object via the viewHolder object into the template view
        viewHolder.tvVideoTitle.setText(video.getTitle());
        viewHolder.tvChannelTitle.setText(video.getChannelTitle());


        viewHolder.tvPublishDate.setText(String.format("%s %s",
                Constants.PUBLISHED, DateUtil.getRelativeTime(video.getPublishedAt())));

        Picasso.with(getContext()).load(video.getThumbnailUrl())
                .placeholder(R.drawable.youtube_thumbnail)
                .transform(new RoundedCornersTransformation(10, 10))
                .into(viewHolder.ivVideoImage);



        // return the completed view to render on screen
        return convertView;

    }
}
