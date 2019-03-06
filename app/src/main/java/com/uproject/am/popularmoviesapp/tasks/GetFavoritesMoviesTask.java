package com.uproject.am.popularmoviesapp.tasks;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.Toast;

import com.uproject.am.popularmoviesapp.R;
import com.uproject.am.popularmoviesapp.adapters.MovieAdapter;
import com.uproject.am.popularmoviesapp.db.MovieContracts;
import com.uproject.am.popularmoviesapp.entities.Movie;

import java.util.ArrayList;
import java.util.List;

public class GetFavoritesMoviesTask extends AsyncTask<Void, Void, List<Movie>> {

    private Context mContext;
    private MovieAdapter mMovieAdapter;

   private static final String[] MOVIE_COLUMNS = {
                MovieContracts.MOVIES_TABLE._ID,
                MovieContracts.MOVIES_TABLE.COLUMN_TITLE,
                MovieContracts.MOVIES_TABLE.COLUMN_OVERVIEW,
                MovieContracts.MOVIES_TABLE.COLUMN_POSTER_IMAGE,
                MovieContracts.MOVIES_TABLE.COLUMN_VOTE_AVERAGE,
                MovieContracts.MOVIES_TABLE.COLUMN_RELEASE_DATE
        };

        public static final int COL_ID = 0;
        public static final int COL_TITLE = 1;
        public static final int COL_OVERVIEW = 2;
        public static final int COL_POSTER_IMAGE = 3;
        public static final int COL_VOTE = 4;
        public static final int COL_RELEASE_DATE = 5;



    public GetFavoritesMoviesTask(Context context, MovieAdapter movieAdapter) {
        mContext = context;
        mMovieAdapter = movieAdapter;
    }
        private List<Movie> getFavoriteMoviesDataFromCursor(Cursor cursor) {
            List<Movie> results = new ArrayList<>();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Movie movie = new Movie(cursor.getString(COL_ID),
                        cursor.getString(COL_TITLE),
                        cursor.getString(COL_OVERVIEW),
                        cursor.getString(COL_POSTER_IMAGE),
                        cursor.getFloat(COL_VOTE),
                        cursor.getString(COL_RELEASE_DATE));
                    results.add(movie);

                } while (cursor.moveToNext());
                cursor.close();
            }
            return results;
        }

        @Override
        protected List<Movie> doInBackground(Void... params) {
            Cursor cursor = mContext.getContentResolver().query(
                    MovieContracts.MOVIES_TABLE.CONTENT_URI,
                    MOVIE_COLUMNS,
                    null,
                    null,
                    null
            );
            return getFavoriteMoviesDataFromCursor(cursor);
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            if (movies != null && !movies.isEmpty()) {
                if (mMovieAdapter != null) {
                    mMovieAdapter.setMoviesData(movies);
                }
            }
            else {
                Toast.makeText(mContext,mContext.getString(R.string.error_empty_favorite_list).toString(), Toast.LENGTH_LONG).show();
            }
        }
    }