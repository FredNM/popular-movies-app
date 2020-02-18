package com.frednm.popular_movies_app_i.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.frednm.popular_movies_app_i.data.model.Movie;
import com.frednm.popular_movies_app_i.data.model.Video;

@Database(entities = {Movie.class, Video.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class MovieAppDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile MovieAppDatabase INSTANCE;

    // --- DAO ---
    public abstract MovieDao movieDao();

    public abstract VideoDao videoDao();

}
