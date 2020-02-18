package com.frednm.popular_movies_app_i.data.local;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.frednm.popular_movies_app_i.data.model.Video;

import java.util.List;

@Dao
public abstract class VideoDao extends BaseDao<Video> {

    @Query("SELECT * FROM Video WHERE movieId = :movieId LIMIT 10") // Take max 10 trailers !
    public abstract List<Video> getSomeVideos(Integer movieId);

    @Query("DELETE FROM Video WHERE movieId = :movieId ")
    abstract void deleteSomeVideos(Integer movieId);

    //For insertion of movies list, delete before saving new results, in order to avoid having deprecated data
    @Transaction
    public void saveVideos(List<Video> videos, Integer movieId) {
        if (videos != null || !videos.isEmpty()) {
            deleteSomeVideos(movieId);
            insert(videos); // In the different videos, the movieId value has been already set to the selected movie in the MovieRepository method.
        }
    }

    public void saveVideo(Video video){
        insert(video);
    }


}
