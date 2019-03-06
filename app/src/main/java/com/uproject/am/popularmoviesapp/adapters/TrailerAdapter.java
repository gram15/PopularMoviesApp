package com.uproject.am.popularmoviesapp.adapters;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uproject.am.popularmoviesapp.R;
import com.uproject.am.popularmoviesapp.entities.Trailer;
import com.uproject.am.popularmoviesapp.utilities.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TrailerAdapter  extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {

    private List<Trailer> mTrailsersList;
    private Context mContext;

    public TrailerAdapter(Context context, List<Trailer> trailerList) {
        this.mContext = context;
        this.mTrailsersList = trailerList;
    }

    @Override
    public TrailerAdapter.TrailerAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, parent, false);
        return new TrailerAdapterViewHolder(view);
    }



    @Override
    public void onBindViewHolder(TrailerAdapter.TrailerAdapterViewHolder trailerAdapterViewHolder, int position) {
        final Trailer trailer = mTrailsersList.get(position);
        trailerAdapterViewHolder.mTrailerName.setText(trailer.getName());

        trailerAdapterViewHolder.mTrailerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watchYoutubeVideo(trailer.getKey());
            }
        });
    }


    public void watchYoutubeVideo(String id) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.MovieDbAPIConstants.YOUTUBE_PREFIX + id));
            mContext.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(Constants.MovieDbAPIConstants.YOUTUBE_URL+ id));
            mContext.startActivity(intent);
        }
    }
    @Override
    public int getItemCount() {
        if (null == mTrailsersList) return 0;
        return mTrailsersList.size();
    }


    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_trailer_name)
        TextView mTrailerName;


        public TrailerAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
