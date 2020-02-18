package com.frednm.popular_movies_app_i.features.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.frednm.popular_movies_app_i.R;
import com.frednm.popular_movies_app_i.common.base.BaseViewModel;
import com.frednm.popular_movies_app_i.common.utils.Event;
import com.frednm.popular_movies_app_i.data.model.Movie;
import com.frednm.popular_movies_app_i.data.repository.utils.Resource;
import com.frednm.popular_movies_app_i.features.home.domain.GetPopularMoviesUseCase;
import com.frednm.popular_movies_app_i.features.home.domain.GetTopRatedMoviesUseCase;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

/**
 * Question 1:
 * I saw in some medium posts people encapsulating their navigation in a LiveData. Should I have encapsulated the navigation event
 * in a LiveData? When configuring navigation like here (without treating it as a single livedata event), is it wrong ? Or at least,
 * could it lead to some drawbacks ?
 *
 * Question 2:
 * How to get a String resource here in the ViewModel !? For example, how can I get the resource R.string.pref_sort_popular_value ?
 * I have read that it's not recommended to use AndroidViewModel, so ...
 *
 * Question 3:
 * Do I have to clear Glide memory at a certain frequency !?
 */



public class HomeViewModel extends BaseViewModel {

    public interface Listeners {
        void navigateToDetailActivity(Integer movieId);
    }
    private WeakReference<Listeners> callback;

    private GetPopularMoviesUseCase getPopularMoviesUseCase;
    private GetTopRatedMoviesUseCase getTopRatedMoviesUseCase;


    // FOR DATA SHOWN ON UI
    private MediatorLiveData<Resource<List<Movie>>> _movies = new MediatorLiveData<>();
    public  LiveData<Resource<List<Movie>>> movies = _movies;
    private LiveData<Resource<List<Movie>>> moviesSource = new MutableLiveData<>();

    // SOME SETTING PARAMETERS
    private String sortingCriteria;
    // For the two string constants bellow, I know that my approach is error prone. Normally, I should take the
    // R.string.pref_sort_popular_value and R.string.pref_sort_top_rated_value values directly from the strings.xml resource.
    // But I didn't find a way to do that in the ViewModel. How to get strings resources in ViewModel !??
    private static final String popular = "popular";
    private static final String topRated = "topRated";


    // --- CONSTRUCTOR
    @Inject
    public HomeViewModel(GetPopularMoviesUseCase getPopularMoviesUseCase, GetTopRatedMoviesUseCase getTopRatedMoviesUseCase) {
        this.getPopularMoviesUseCase = getPopularMoviesUseCase;
        this.getTopRatedMoviesUseCase = getTopRatedMoviesUseCase;
    }

    //  --- USER ACTIONS
    public void userClicksOnItem(Movie movie) {
        Log.d("HomeViewModel", "Entering in userClicksOnItem method");
        this.callback.get().navigateToDetailActivity(movie.getId());
    }

    public void userRefreshesItems() {
        getMovies(true);
    }

    public void userChangesSettings(String sortingCriteria) {
        this.sortingCriteria = sortingCriteria;
        getMovies(false);
    }

    //  --- METHODS USED FOR USER ACTIONS
    private void getMovies (Boolean forceRefresh) {
        _movies.removeSource(moviesSource);
        if (this.sortingCriteria.equals(popular)) { // This approach is error prone. I should take the value of R.String.pref_sort_popular_value directly from the String resources, and not use a constant in which I put the value.
            moviesSource = getPopularMoviesUseCase.invoke(forceRefresh);
        } else if (this.sortingCriteria.equals(topRated)) {
            moviesSource = getTopRatedMoviesUseCase.invoke(forceRefresh);
        } else {
            moviesSource = null;
        }
        _movies.addSource(moviesSource, this::treatData);
    }

    private void treatData(Resource<List<Movie>> resource) {
        this._movies.setValue(resource);
        if (resource.getStatus() == Resource.Status.ERROR) _snackbarError.setValue(new Event<>(R.string.an_error_happened));
        if (resource.getStatus() == Resource.Status.NONETWORK) _snackbarError.setValue(new Event<>(R.string.no_network));
    }

    public void implementedListener(Listeners callback) { // call in onCreate of HomeActivity, to initialise callback with its implementation present in HomeActivity
        Log.d("HomeViewModel", "Initializing implementedListener method");
        this.callback  = new WeakReference<> (callback);
    }

    private void getFavoriteMoviesFromCache() {
        //  TODO For next project
        //  TODO Don't forget to configure fall down animation and dispose() disposable in onCleared
    }

}
