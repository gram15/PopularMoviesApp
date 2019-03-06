package com.uproject.am.popularmoviesapp.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Button;

import com.uproject.am.popularmoviesapp.R;
import com.uproject.am.popularmoviesapp.db.DbUtils;
import com.uproject.am.popularmoviesapp.entities.Movie;

public class ManageFavoritesMoviesTask extends AsyncTask<Void, Void, Boolean> {
    private Context mContext;
    private Movie mMovie;
    private Boolean mAction;
    private Button mFavoriteButton;

    public ManageFavoritesMoviesTask(Context mContext, Movie movie, Boolean action, Button favoriteButton) {
        this.mContext = mContext;
        this.mMovie = movie;
        this.mAction = action;
        this.mFavoriteButton = favoriteButton;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return DbUtils.isFavorite(mContext, mMovie.getId());
    }


    @Override
    protected void onPostExecute(Boolean isFavorited) {
        if (mAction) {
            new UpdateFavoritesMoviesTask(mContext, mMovie, isFavorited, mFavoriteButton).execute();
        } else {
            if (isFavorited) {
                mFavoriteButton.setText(mContext.getString(R.string.mark_unfavorite));
            } else {
                mFavoriteButton.setText(mContext.getString(R.string.mark_favorite));
            }
        }
    }
}
