package com.uproject.am.popularmoviesapp.services;

import com.uproject.am.popularmoviesapp.entities.Movie;
import com.uproject.am.popularmoviesapp.entities.Review;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieService {

        @GET("3/movie/popular?")
        Call<ListResponse<Movie>> getPopularMovies();

        @GET("3/movie/top_rated?")
        Call<ListResponse<Movie>> getTopRatedMovies();

        @GET("3/movie/{id}/reviews?")
        Call<ListResponse<Review>> getMovieReviews(@Path("id") String id);

        @GET("3/movie/{id}/videos?")
        Call<TrailerListResponse> getMovieTrailers(@Path("id") String id);
    }
