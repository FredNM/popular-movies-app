package com.frednm.popular_movies_app_i.data.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.frednm.popular_movies_app_i.data.local.MovieDao;
import com.frednm.popular_movies_app_i.data.local.VideoDao;
import com.frednm.popular_movies_app_i.data.model.ApiResult;
import com.frednm.popular_movies_app_i.data.model.Movie;
import com.frednm.popular_movies_app_i.data.remote.MovieDatasource;
import com.frednm.popular_movies_app_i.data.repository.utils.NetworkBoundResource;
import com.frednm.popular_movies_app_i.data.repository.utils.Resource;

import java.util.List;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;

/**
 * Repository produces only LiveData.
 * After passing them to NetworkBoundResource, data produced are always wrapped in LiveData<Resource> thanks to asLiveData()
 */

@Singleton
public class MovieRepository {

    private MovieDatasource movieDatasource ;
    private MovieDao movieDao;
    private VideoDao videoDao  ;

    @Inject
    public MovieRepository(MovieDatasource movieDatasource, MovieDao movieDao, VideoDao videoDao) {
        this.movieDatasource = movieDatasource;
        this.movieDao = movieDao;
        this.videoDao = videoDao;
    }

    /**
     * Get top rated movies only from DB. If shouldFetch returns true, then do the network request first, and save results in DB.
     * shouldFetch returns true if data is null or empty(empty DB), or when user swipes to refresh the screen.
     */
    public LiveData<Resource<List<Movie>>> getTopRatedMovies(Boolean forceRefresh){
        return new NetworkBoundResource<List<Movie>,ApiResult<Movie>>(){

            @Override
            protected List<Movie> processResponse(ApiResult<Movie> response) {
                List<Movie> list = response.getResults();
                for(int i=0;i<list.size();i++){
                    list.get(i).setLastRefreshed(new Date()) ;
                    list.get(i).setTopRated(true);
                }
                return list;
            }

            @Override
            protected void saveCallResults(@NonNull List<Movie> items) {
                movieDao.saveTopRatedMovies(items);
            }

            @Override
            protected Boolean shouldFetch(@Nullable List<Movie> data) {
                return data==null || data.isEmpty() || forceRefresh ;
            }

            @NonNull
            @Override
            protected List<Movie> loadFromDb() {
                return movieDao.getTopRatedMovies();
            }

            @NonNull
            @Override
            protected Call<ApiResult<Movie>> createCallAsync() {
                return movieDatasource.fetchTopRatedMovies();
            }
        }.asLiveData();
    }

    /**
     * Get popular movies only from DB. If shouldFetch returns true, then do the network request first, and save results in DB.
     * shouldFetch returns true if data is null or empty(empty DB), or when user swipes to refresh the screen.
     */
    public LiveData<Resource<List<Movie>>> getPopularMovies(Boolean forceRefresh){
        return new NetworkBoundResource<List<Movie>,ApiResult<Movie>>(){

            @Override
            protected List<Movie> processResponse(ApiResult<Movie> response) {
                List<Movie> list = response.getResults();
                for(int i=0;i<list.size();i++){
                    list.get(i).setLastRefreshed(new Date()) ;
                    list.get(i).setPopular(true);
                }
                return list;
            }

            @Override
            protected void saveCallResults(@NonNull List<Movie> items) {
                movieDao.savePopularMovies(items);
            }

            @Override
            protected Boolean shouldFetch(@Nullable List<Movie> data) {
                return data==null || data.isEmpty() || forceRefresh ;
            }

            @NonNull
            @Override
            protected List<Movie> loadFromDb() {
                return movieDao.getPopularMovies();
            }

            @NonNull
            @Override
            protected Call<ApiResult<Movie>> createCallAsync() {
                return movieDatasource.fetchPopularMovies();
            }
        }.asLiveData();
    }

    /**
     * Get detail of a movie from DB. If shouldFetch returns true, then do the network request first, and save results in DB.
     * shouldFetch returns true if data is null (empty DB), or the runtime of the movie is missing (it's the only movie information
     * we don't have after fetching list of popular or top rated movies), or user swipes to refresh, or the time spent between
     * now and the previous network request is > 10 min (see haveToRefreshFromNetwork() method in Movie class).
     */
    public LiveData<Resource<Movie>> getDetailMovie(Boolean forceRefresh, Integer movieId){
        return new NetworkBoundResource<Movie,Movie>(){

            @Override
            protected Movie processResponse(Movie response) {
                if (response.getVideos().getResults() != null) {
                    for (int i = 0; i < response.getVideos().getResults().size(); i++) {
                        response.getVideos().getResults().get(i).setMovieId(movieId);
                    }
                }
                return response;
            }

            @Override
            protected void saveCallResults(@NonNull Movie item) {
                movieDao.updateMovie(item.getRuntime(),movieId); // Update Movie information in the DB, the runtime was missing
                videoDao.saveVideos(item.getVideos().getResults(), movieId); // Save videos (trailer's videos) of that Movie.
            }

            @Override
            protected Boolean shouldFetch(@Nullable Movie data) {
                return data==null || data.getRuntime()==null || forceRefresh || data.haveToRefreshFromNetwork() ;
            }

            @NonNull
            @Override
            protected Movie loadFromDb() {
                return movieDao.getMovie(movieId);
            }

            @NonNull
            @Override
            protected Call<Movie> createCallAsync() {
                return movieDatasource.fetchDetailMovie(movieId);
            }
        }.asLiveData();
    }

    public void updateMoviesFavorite(){
        // TODO For next project : update a Movie in DB by changing it's isUserFavorite attribute when user clicks on isfavorite button
        // TODO This should be reactive, so no need to wrap here in NetworkBoundResource, it doesn't come from network !
    }

}
