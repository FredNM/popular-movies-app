package com.frednm.popular_movies_app_i.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResult<T> {


    @SerializedName("results")
    private List<T> results ;


    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
