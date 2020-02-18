package com.frednm.popular_movies_app_i.data.remote;

import com.frednm.popular_movies_app_i.data.model.*;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

    // https://api.themoviedb.org/3/movie/top_rated?api_key=###  // Get list of Top Rated movies
    @GET("top_rated")
    Call<ApiResult<Movie>> fetchTopRatedMovies(@Query("api_key")  String apiKey);

    // https://api.themoviedb.org/3/movie/popular?api_key=###  // Get list of Popuplar movies
    @GET("popular")
    Call<ApiResult<Movie>> fetchPopularMovies(@Query("api_key") String apiKey);

    // http://api.themoviedb.org/3/movie/496243?api_key=###&append_to_response=videos // Give a movie runtime (duration) having its id !
    @GET("{id}")
    Call<Movie> fetchDetailMovie(@Path("id") Integer id,
                                                    @Query("api_key") String apiKey,
                                                    @Query("append_to_response") String videos );

}
