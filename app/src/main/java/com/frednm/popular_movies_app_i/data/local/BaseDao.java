package com.frednm.popular_movies_app_i.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import java.util.List;

@Dao
abstract class BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract void insert(List<T> data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract void insert(T data);

    // TODO for next project : should configure update, for the case when user update a video, saying if it's favorite or no.
    //  isUserFavorite might become true or false
}
