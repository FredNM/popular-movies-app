package com.frednm.popular_movies_app_i.features.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.frednm.popular_movies_app_i.R;
import com.frednm.popular_movies_app_i.common.base.BaseViewModel;
import com.frednm.popular_movies_app_i.common.utils.Event;
import com.frednm.popular_movies_app_i.data.model.Movie;
import com.frednm.popular_movies_app_i.data.repository.utils.Resource;
import com.frednm.popular_movies_app_i.features.detail.domain.GetDetailMovieUseCase;

import javax.inject.Inject;


public class DetailViewModel extends BaseViewModel {

    // FOR DATA SHOWN ON UI
    private MediatorLiveData<Movie> _movie = new MediatorLiveData<>();
    public LiveData<Movie> movie = _movie;
    private LiveData<Resource<Movie>> movieSource = new MutableLiveData<>();

    private Integer movieId;

    private GetDetailMovieUseCase getDetailMovieUseCase;  // TODO Should be injected

    @Inject
    public DetailViewModel(GetDetailMovieUseCase getDetailMovieUseCase) {
        this.getDetailMovieUseCase = getDetailMovieUseCase;
    }


    //  --- USER ACTIONS
    public void loadDataWhenActivityStarts(Integer id){
        movieId = id;
        getDetailMovie(false);
    }


    //  --- METHODS USED FOR USER ACTIONS
    private void getDetailMovie (Boolean forceRefresh) {
        _movie.removeSource(movieSource);
        movieSource = getDetailMovieUseCase.invoke(forceRefresh, movieId);
        _movie.addSource(movieSource , this::treatData);
        // TODO Configure fall down animation
    }

    private void treatData(Resource<Movie> resource) {
        this._movie.setValue(resource.getData());
        if (resource.getStatus() == Resource.Status.ERROR) _snackbarError.setValue(new Event<>(R.string.an_error_happened));
        if (resource.getStatus() == Resource.Status.NONETWORK) _snackbarError.setValue(new Event<>(R.string.no_network));
    }
}
