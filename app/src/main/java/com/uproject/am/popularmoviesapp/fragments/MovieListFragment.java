package com.uproject.am.popularmoviesapp.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.uproject.am.popularmoviesapp.R;
import com.uproject.am.popularmoviesapp.adapters.MovieAdapter;
import com.uproject.am.popularmoviesapp.entities.Movie;
import com.uproject.am.popularmoviesapp.interfaces.OnMovieSelected;
import com.uproject.am.popularmoviesapp.services.MovieClient;
import com.uproject.am.popularmoviesapp.services.ListResponse;
import com.uproject.am.popularmoviesapp.services.MovieService;
import com.uproject.am.popularmoviesapp.tasks.GetFavoritesMoviesTask;
import com.uproject.am.popularmoviesapp.utilities.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListFragment extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar mProggressBar;
    @BindView(R.id.tv_error_message_display)
    TextView mErrorMessageDisplay;
    private MovieAdapter mMovieAdapter;
    private Constants.SortBy mSortBy = Constants.SortBy.MOST_POPULAR;
    private MovieService mMovieService;

    OnMovieSelected onMovieClickedListener;
    private MenuItem mMenuItemSortPopular;
    private MenuItem mMenuItemSortRating;
    private MenuItem mMenuItemSortFav;

    public MovieListFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getActivity());
        mMovieService = MovieClient.createService(MovieService.class);
        getMoviesList(mSortBy);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_movie_list, menu);
        mMenuItemSortPopular = menu.findItem(R.id.sort_by_most_popular);
        mMenuItemSortRating = menu.findItem(R.id.sort_by_top_rated);
        mMenuItemSortFav = menu.findItem(R.id.favorites);
        if (mSortBy.equals(Constants.SortBy.MOST_POPULAR)) {
            if (!mMenuItemSortPopular.isChecked()) {
                mMenuItemSortPopular.setChecked(true);
            }
        } else if (mSortBy.equals(Constants.SortBy.TOP_RATED)) {
            if (!mMenuItemSortRating.isChecked()) {
                mMenuItemSortRating.setChecked(true);
            }
        } else if (mSortBy.equals(Constants.SortBy.FAVORITES)) {
            if (!mMenuItemSortFav.isChecked()) {
                mMenuItemSortFav.setChecked(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_by_most_popular:
                mSortBy = Constants.SortBy.MOST_POPULAR;
                item.setChecked(true);
                getMoviesList(mSortBy);

                break;
            case R.id.sort_by_top_rated:
                mSortBy = Constants.SortBy.TOP_RATED;
                item.setChecked(true);
                getMoviesList(mSortBy);
                break;
            case R.id.favorites:
                mSortBy = Constants.SortBy.FAVORITES;
                item.setChecked(true);
                getFavoritesMoviesList();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method will get movies data in the background.
     */
    private void getMoviesList(Constants.SortBy sortBy) {

        if (isOnline()) {
            Call<ListResponse<Movie>> moviesCall = null;
            if (sortBy.equals(Constants.SortBy.MOST_POPULAR)) {
                moviesCall = mMovieService.getPopularMovies();
            } else {
                moviesCall = mMovieService.getTopRatedMovies();
            }

            moviesCall.enqueue(new Callback<ListResponse<Movie>>() {
                @Override
                public void onResponse(Call<ListResponse<Movie>> call, Response<ListResponse<Movie>> response) {
                    mErrorMessageDisplay.setVisibility(View.INVISIBLE);
                    List<Movie> movieList = response.body().getResults();
                    GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
                    mMovieAdapter = new MovieAdapter(getContext(), onMovieClickedListener, movieList);
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mMovieAdapter);

                    if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
                        onMovieClickedListener.onMovieSelected(movieList.get(0));
                    }
                }

                @Override
                public void onFailure(Call<ListResponse<Movie>> call, Throwable t) {
                    Toast.makeText(getContext(), getString(R.string.error_internet_connection), Toast.LENGTH_SHORT).show();
                }

            });
        } else {
            mErrorMessageDisplay.setVisibility(View.VISIBLE);
            List<Movie> movieList = new ArrayList<Movie>();
            GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
            mMovieAdapter = new MovieAdapter(getContext(), onMovieClickedListener, movieList);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mMovieAdapter);
            Toast.makeText(getContext(), getString(R.string.error_internet_connection), Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * This method checks internet connection
     */
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onMovieClickedListener = (OnMovieSelected) context;
    }

    @Override
    public void onDetach() {
        onMovieClickedListener = null;
        super.onDetach();
    }

    public void getFavoritesMoviesList() {
        final GetFavoritesMoviesTask getFavoritesMoviesTask = new GetFavoritesMoviesTask(getContext(), mMovieAdapter);
          getFavoritesMoviesTask.execute();
          mErrorMessageDisplay.setVisibility(View.INVISIBLE);
    }
}
