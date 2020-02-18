package com.frednm.popular_movies_app_i.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoResult {
    @SerializedName("results")
    private List<Video> results ;

    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }
}
