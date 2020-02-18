package com.frednm.popular_movies_app_i.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * To read a Video on Youtube, all we need here is the Key attribute
 * The youtube URL is like this : https://www.youtube.com/watch?v=Key
 * Where Key is the key of the movie. For example, if Key =SUXWAEX2jlg
 * The full URL will be : https://www.youtube.com/watch?v=SUXWAEX2jlg
 */

@Entity(foreignKeys = @ForeignKey(entity = Movie.class,
        parentColumns = "id",
        childColumns = "movieId",
        onDelete = ForeignKey.CASCADE),
        indices = @Index("movieId"))

public class Video {

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    private String id;

    @SerializedName("key")
    private String key;

    @SerializedName("name")
    private String name;

    @SerializedName("site")
    private String site;

    @SerializedName("type")
    private String type;

    private Integer movieId;

    // --- Constructor

    public Video(String id, String key, String name, String site, String type, Integer movieId) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.site = site;
        this.type = type;
        this.movieId = movieId;
    }

    // ---- Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

}
