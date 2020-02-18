package com.frednm.popular_movies_app_i.data.local;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.frednm.popular_movies_app_i.data.model.Movie;

import java.util.List;

@Dao
public abstract class MovieDao extends BaseDao<Movie> {
    // TODO All the work

    // -- SELECT
    // In Room, true = 1, false = 0. Read here https://stackoverflow.com/questions/47730820/hardcode-boolean-query-in-room-database
    @Query("SELECT * FROM Movie WHERE isTopRated = 1 ")
    public abstract List<Movie> getTopRatedMovies();

    @Query("SELECT * FROM Movie WHERE isPopular = 1 ")
    public abstract List<Movie> getPopularMovies();

    @Query("SELECT * FROM Movie WHERE id = :id")
    public abstract Movie getMovie(Integer id);

    // -- DELETE
    @Query("DELETE FROM Movie WHERE isTopRated = 1 ")
    abstract void deleteTopRatedMovies();

    @Query("DELETE FROM Movie WHERE isPopular = 1 ")
    abstract void deletePopularMovies();

    //For insertion of movies list, delete before saving new results, in order to avoid having deprecated data
    @Transaction
    public void saveTopRatedMovies(List<Movie> movies) {
        deleteTopRatedMovies();
        insert(movies);
    }

    @Transaction
    public void savePopularMovies(List<Movie> movies) {
        deletePopularMovies();
        insert(movies);
    }

    // I didn't use @Update because it will have over written all the existing fields in the movie item, even the isPopular, or
    // isTopRated one, thus setting them to null. When calling the updateMovie method, the movie already exists, and the only field
    // that has to be filled is the runtine one, because its value is not obtained after any of the 2 first network request.
    // @See https://stackoverflow.com/questions/45789325/update-some-specific-field-of-an-entity-in-android-room
    @Query("UPDATE Movie SET runtime = :runtime WHERE id = :movieId")
    public abstract int updateMovie(Integer runtime, Integer movieId);

    public void saveMovie(Movie movie){
        insert(movie);
    }


    //TODO For next project : configure Flowable to update list of favorite movies. Don't forget to avoid false positive notif !

}
