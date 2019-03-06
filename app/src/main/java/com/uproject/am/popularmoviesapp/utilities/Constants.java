package com.uproject.am.popularmoviesapp.utilities;

public class Constants {

    public enum SortBy {
        MOST_POPULAR, TOP_RATED, FAVORITES;
    }
    public static class Tags{
        public final static String MOVIE_DETAILS_FRAGMENT_TAG = "MDFTAG";
        public final static String MOVIE_TAG = "MOVIE";
    }

    public final static class MovieDbAPIConstants {
        public static final String BASE_URL = "http://api.themoviedb.org/";
        public final static String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
        public final static String RATING_MAX = "10";
        public static final String APP_KEY_QUERY_PARAM = "api_key";
        public final static String IMAGE_SMALL_SIZE = "w500";
        public final static String YOUTUBE_PREFIX = "vnd.youtube:";
        public final static String YOUTUBE_URL= "http://www.youtube.com/watch?v=";
    }

}
