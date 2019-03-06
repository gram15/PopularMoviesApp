package com.uproject.am.popularmoviesapp.adapters;


import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import com.uproject.am.popularmoviesapp.R;
import com.uproject.am.popularmoviesapp.entities.Movie;
import com.uproject.am.popularmoviesapp.interfaces.OnMovieSelected;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private List<Movie> mMoviesList;
    private final OnMovieSelected mClickHandler;
    private Context mContext;

    public MovieAdapter(Context context, OnMovieSelected clickHandler, List<Movie> movieList) {
        this.mContext = context;
        this.mClickHandler = clickHandler;
        this.mMoviesList = movieList;
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new MovieAdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        final Movie movie = mMoviesList.get(position);
        Picasso.with(mContext)
                .load(movie.getImageFullURL())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(movieAdapterViewHolder.mMovieImageView);

        movieAdapterViewHolder.mMovieImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickHandler != null)
                    mClickHandler.onMovieSelected(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null == mMoviesList) return 0;
        return mMoviesList.size();
    }

    public void setMoviesData(List<Movie> moviesData) {
        if(mMoviesList!=null && !mMoviesList.isEmpty()) {
            mMoviesList.clear();
        }
        mMoviesList = moviesData;
        if ((mContext.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            mClickHandler.onMovieSelected(mMoviesList.get(0));
        }
        notifyDataSetChanged();
    }


    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.image) ImageView mMovieImageView;

        public MovieAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }

    }


}



