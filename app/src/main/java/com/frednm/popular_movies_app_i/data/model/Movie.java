package com.frednm.popular_movies_app_i.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.concurrent.TimeUnit;


@Entity
public class Movie {

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;

    @SerializedName("runtime")
    @Expose
    private Integer runtime;

    @Ignore
    @SerializedName("videos")
    @Expose
    private VideoResult videos;

    // only for DB
    @ColumnInfo(defaultValue = "0")
    private Boolean isTopRated;
    @ColumnInfo(defaultValue = "0")
    private Boolean isPopular;
    @ColumnInfo(defaultValue = "0")
    private Boolean isUserFavorite;
    private Date lastRefreshed;


    // ----------- Constructors

    // Constructor just for Room
    public Movie(Integer id, String posterPath, String title, Double voteAverage, String overview, String releaseDate, String backdropPath, Integer runtime, Boolean isTopRated, Boolean isPopular, Boolean isUserFavorite, Date lastRefreshed) {
        this.id = id;
        this.posterPath = posterPath;
        this.title = title;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.backdropPath = backdropPath;
        this.runtime = runtime;
        this.isTopRated = isTopRated;
        this.isPopular = isPopular;
        this.isUserFavorite = isUserFavorite;
        this.lastRefreshed = lastRefreshed;
    }

    public Movie(Integer id, String posterPath, String title, Double voteAverage, String overview, String releaseDate, String backdropPath, Integer runtime, VideoResult videos, Boolean isTopRated, Boolean isPopular, Boolean isUserFavorite, Date lastRefreshed) {
        this.id = id;
        this.posterPath = posterPath;
        this.title = title;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.backdropPath = backdropPath;
        this.runtime = runtime;
        this.videos = videos;
        this.isTopRated = isTopRated;
        this.isPopular = isPopular;
        this.isUserFavorite = isUserFavorite;
        this.lastRefreshed = lastRefreshed;
    }

    public Boolean haveToRefreshFromNetwork(){
        return TimeUnit.MILLISECONDS.toMinutes((new Date()).getTime() - lastRefreshed.getTime() ) >= 10;
    }

    // ----------- Getters and Setters

    public Boolean getTopRated() {
        return isTopRated;
    }

    public void setTopRated(Boolean topRated) {
        isTopRated = topRated;
    }

    public Boolean getPopular() {
        return isPopular;
    }

    public void setPopular(Boolean popular) {
        isPopular = popular;
    }

    public Boolean getUserFavorite() {
        return isUserFavorite;
    }

    public void setUserFavorite(Boolean userFavorite) {
        isUserFavorite = userFavorite;
    }

    public Date getLastRefreshed() {
        return lastRefreshed;
    }

    public void setLastRefreshed(Date lastRefreshed) {
        this.lastRefreshed = lastRefreshed;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getRuntime() {
        return runtime;
    }
    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public VideoResult getVideos() {
        return videos;
    }

    public void setVideos(VideoResult videos) {
        this.videos = videos;
    }

}


