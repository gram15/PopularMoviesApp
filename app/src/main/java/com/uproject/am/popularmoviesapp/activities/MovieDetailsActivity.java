package com.uproject.am.popularmoviesapp.activities;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.uproject.am.popularmoviesapp.R;
import com.uproject.am.popularmoviesapp.fragments.MovieDetailsFragment;
import com.uproject.am.popularmoviesapp.utilities.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(Constants.Tags.MOVIE_TAG,
                    getIntent().getParcelableExtra(Constants.Tags.MOVIE_TAG));

            MovieDetailsFragment fragment = new MovieDetailsFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_details_fragment, fragment, Constants.Tags.MOVIE_DETAILS_FRAGMENT_TAG)
                    .commit();
        }
    }

}

