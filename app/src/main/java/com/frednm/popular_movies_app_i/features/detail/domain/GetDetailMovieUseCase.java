package com.frednm.popular_movies_app_i.features.detail.domain;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.frednm.popular_movies_app_i.data.model.Movie;
import com.frednm.popular_movies_app_i.data.repository.MovieRepository;
import com.frednm.popular_movies_app_i.data.repository.utils.Resource;

import javax.inject.Inject;


/**
 * This UseCase is almost useless in this app, but exists for "Clean coding" purpose :)
 */
public class GetDetailMovieUseCase {

    public MovieRepository repository;

    @Inject
    public GetDetailMovieUseCase(MovieRepository repository) {
        this.repository = repository;
    }

    public LiveData<Resource<Movie>> invoke(Boolean forceRefresh, Integer movieId){
        return Transformations.map(repository.getDetailMovie(forceRefresh, movieId),this::transform);
    }

    // Useless so far, but could be interesting to apply something here
    private Resource<Movie> transform(Resource<Movie> data) {
        return data;
    }

}
