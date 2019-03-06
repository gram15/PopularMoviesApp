package com.uproject.am.popularmoviesapp.activities;

import android.content.Intent;
import android.os.Bundle;

import com.uproject.am.popularmoviesapp.R;
import com.uproject.am.popularmoviesapp.entities.Movie;
import com.uproject.am.popularmoviesapp.fragments.MovieDetailsFragment;
import com.uproject.am.popularmoviesapp.interfaces.OnMovieSelected;
import com.uproject.am.popularmoviesapp.utilities.Constants;
import android.support.v7.app.AppCompatActivity;

public class MovieListActivity extends AppCompatActivity implements OnMovieSelected {

    private boolean mTwoPane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        if (findViewById(R.id.movie_details_fragment) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_details_fragment, new MovieDetailsFragment(), Constants.Tags.MOVIE_DETAILS_FRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
        }
   }
    @Override
    public void onMovieSelected(Movie movie) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(Constants.Tags.MOVIE_TAG, movie);

            MovieDetailsFragment fragment = new MovieDetailsFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_details_fragment, fragment, Constants.Tags.MOVIE_DETAILS_FRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, MovieDetailsActivity.class)
                    .putExtra(Constants.Tags.MOVIE_TAG, movie);
            startActivity(intent);
        }
    }

}
