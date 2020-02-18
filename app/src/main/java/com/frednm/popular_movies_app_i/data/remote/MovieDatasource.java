package com.frednm.popular_movies_app_i.data.remote;

import android.util.Log;

import com.frednm.popular_movies_app_i.data.model.*;

import javax.inject.Inject;

import retrofit2.Call;

/**
 * Implementation of MovieService interface
 */
public class MovieDatasource {

    private static final String apiKey = ""; // TODO Put your API key here !
    private static final String videos = "videos";
    private MovieService movieService;

    @Inject
    public MovieDatasource(MovieService movieService) {
        this.movieService = movieService;
    }

    public Call<ApiResult<Movie>> fetchTopRatedMovies(){
        Log.d("Movie DataSource", "Fetching Top Rated movies");
        return movieService.fetchTopRatedMovies(apiKey);
    }

    public Call<ApiResult<Movie>> fetchPopularMovies(){
        Log.d("Movie DataSource", "Fetching Popular movies");
        return movieService.fetchPopularMovies(apiKey);
    }

    public Call<Movie> fetchDetailMovie(Integer id){
        Log.d("Movie DataSource", "Fetching Detail movies");
        return movieService.fetchDetailMovie(id, apiKey, videos);
    }

}
