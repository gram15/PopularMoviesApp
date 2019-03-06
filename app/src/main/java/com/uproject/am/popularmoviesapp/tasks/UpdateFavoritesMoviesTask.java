package com.uproject.am.popularmoviesapp.tasks;


import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.Toast;

import com.uproject.am.popularmoviesapp.R;
import com.uproject.am.popularmoviesapp.db.DbUtils;
import com.uproject.am.popularmoviesapp.db.MovieContracts;
import com.uproject.am.popularmoviesapp.entities.Movie;

public class UpdateFavoritesMoviesTask extends AsyncTask<Void, Void, Void> {
    private Context mContext;
    private Movie mMovie;
    private Boolean mIsAlreadyFavorite;
    private Button mFavoriteButton;


    public UpdateFavoritesMoviesTask(Context mContext, Movie movie, Boolean isAlreadyFavorite, Button favoriteButton) {
        this.mContext = mContext;
        this.mMovie = movie;
        this.mIsAlreadyFavorite = isAlreadyFavorite;
        this.mFavoriteButton = favoriteButton;
    }

    @Override
    protected Void doInBackground(Void... params) {
        if (!mIsAlreadyFavorite) {
            ContentValues contentValues = DbUtils.toContentValue(mMovie);
            mContext.getContentResolver().insert(MovieContracts.MOVIES_TABLE.CONTENT_URI, contentValues);

        } else {
            mContext.getContentResolver().delete(
                    MovieContracts.MOVIES_TABLE.CONTENT_URI,
                    MovieContracts.MOVIES_TABLE._ID + " = ?",
                    new String[]{mMovie.getId()}
            );
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        int toastStrRes;
        if (!mIsAlreadyFavorite) {
            toastStrRes = R.string.mark_favorite_message;
            mFavoriteButton.setText(mContext.getString(R.string.mark_unfavorite));
        } else {
            toastStrRes = R.string.mark_unfavorite_message;
            mFavoriteButton.setText(mContext.getString(R.string.mark_favorite));
        }
        Toast.makeText(mContext, mContext.getString(toastStrRes), Toast.LENGTH_SHORT).show();
    }
}