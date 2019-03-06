package com.uproject.am.popularmoviesapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.uproject.am.popularmoviesapp.R;
import com.uproject.am.popularmoviesapp.adapters.ReviewAdapter;
import com.uproject.am.popularmoviesapp.adapters.TrailerAdapter;
import com.uproject.am.popularmoviesapp.entities.Movie;
import com.uproject.am.popularmoviesapp.entities.Review;
import com.uproject.am.popularmoviesapp.entities.Trailer;
import com.uproject.am.popularmoviesapp.services.ListResponse;
import com.uproject.am.popularmoviesapp.services.MovieClient;
import com.uproject.am.popularmoviesapp.services.MovieService;
import com.uproject.am.popularmoviesapp.services.TrailerListResponse;
import com.uproject.am.popularmoviesapp.tasks.ManageFavoritesMoviesTask;
import com.uproject.am.popularmoviesapp.utilities.Constants;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieDetailsFragment extends Fragment {


    @BindView(R.id.tv_movie_title)
    TextView mTitle;
    @BindView(R.id.tv_movie_release_date)
    TextView mReleaseDate;
    @BindView(R.id.tv_movie_rating)
    TextView mRating;
    @BindView(R.id.tv_movie_overview)
    TextView mOverview;
    @BindView(R.id.iv_movie_poster)
    ImageView mMoviePoster;
    @BindView(R.id.btn_favorite_movie)
    Button mFavoriteButton;
    LinearLayout mFragmentMovieDetailsLayout;
    @BindView(R.id.recycler_view_review)
    RecyclerView mReviewRecyclerView;
    @BindView(R.id.recycler_view_trailer)
    RecyclerView mTrailerRecyclerView;

    private RecyclerView.LayoutManager mTrailerLayoutManager;
    private RecyclerView.LayoutManager mReviewLayoutManager;
    public ReviewAdapter mReviewAdapter;
    public TrailerAdapter mTrailerAdapter;
    public MovieService mMovieService;
    private Movie mMovieDetails;

    public MovieDetailsFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ButterKnife.bind(this, getActivity());
        if (mMovieDetails == null) {

        } else {
            mTitle.setText(mMovieDetails.getOriginalTitle());
            mOverview.setText(mMovieDetails.getOverview());
            mRating.setText(mMovieDetails.getRating());
            mReleaseDate.setText(mMovieDetails.getMovieReleaseDate());
            Picasso.with(getContext()).load(mMovieDetails.getImageFullURL()).placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder).into(mMoviePoster);


            new ManageFavoritesMoviesTask(getActivity(), mMovieDetails, false, mFavoriteButton).execute();

            mFavoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ManageFavoritesMoviesTask(getActivity(), mMovieDetails, true, mFavoriteButton).execute();
                }
            });

            mMovieService = MovieClient.createService(MovieService.class);

            getReviews();
            getTrailers();

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);
        mFragmentMovieDetailsLayout = (LinearLayout) rootView.findViewById(R.id.ll_fragment_movie_details);
        if (getArguments() != null) {
            mMovieDetails = getArguments().getParcelable(Constants.Tags.MOVIE_TAG);
        }
        return rootView;
    }

    private void getReviews() {
        Call<ListResponse<Review>> reviewsListCall = mMovieService.getMovieReviews(mMovieDetails.getId());
        reviewsListCall.enqueue(new Callback<ListResponse<Review>>() {
            @Override
            public void onResponse(Call<ListResponse<Review>> call, Response<ListResponse<Review>> response) {
                List<Review> reviews = response.body().getResults();
                mReviewRecyclerView.setHasFixedSize(true);
                mReviewLayoutManager = new LinearLayoutManager(getActivity());
                mReviewRecyclerView.setLayoutManager(mReviewLayoutManager);
                mReviewAdapter = new ReviewAdapter(getContext(), reviews);
                mReviewRecyclerView.setAdapter(mReviewAdapter);
            }

            @Override
            public void onFailure(Call<ListResponse<Review>> call, Throwable t) {
                Toast.makeText(getActivity(),getString(R.string.error_service).toString(), Toast.LENGTH_LONG).show();
            }

        });
    }

    private void getTrailers() {
        Call<TrailerListResponse> trailersListCall = mMovieService.getMovieTrailers(mMovieDetails.getId());
        trailersListCall.enqueue(new Callback<TrailerListResponse>() {
            @Override
            public void onResponse(Call<TrailerListResponse> call, Response<TrailerListResponse> response) {
                List<Trailer> trailers = response.body().getResults();
                mTrailerRecyclerView.setHasFixedSize(true);
                mTrailerLayoutManager = new LinearLayoutManager(getActivity());
                mTrailerRecyclerView.setLayoutManager(mTrailerLayoutManager);
                mTrailerAdapter = new TrailerAdapter(getContext(), trailers);
                mTrailerRecyclerView.setAdapter(mTrailerAdapter);
            }

            @Override
            public void onFailure(Call<TrailerListResponse> call, Throwable t) {
                Toast.makeText(getActivity(),getString(R.string.error_service).toString(), Toast.LENGTH_LONG).show();
            }

        });
    }

}
