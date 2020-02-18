package com.frednm.popular_movies_app_i.di.module;


import android.app.Application;

import androidx.room.Room;

import com.frednm.popular_movies_app_i.data.local.MovieAppDatabase;
import com.frednm.popular_movies_app_i.data.local.MovieDao;
import com.frednm.popular_movies_app_i.data.local.VideoDao;
import com.frednm.popular_movies_app_i.data.remote.MovieDatasource;
import com.frednm.popular_movies_app_i.data.remote.MovieService;
import com.frednm.popular_movies_app_i.data.repository.MovieRepository;
import com.frednm.popular_movies_app_i.data.repository.utils.NetworkUtils;
import com.frednm.popular_movies_app_i.features.detail.domain.GetDetailMovieUseCase;
import com.frednm.popular_movies_app_i.features.home.domain.GetPopularMoviesUseCase;
import com.frednm.popular_movies_app_i.features.home.domain.GetTopRatedMoviesUseCase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class AppModule {

    // --- DATABASE INJECTION ---

    @Provides
    @Singleton
    MovieAppDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application,
                MovieAppDatabase.class, "MovieAppDatabase.db")
                .build();
    }

    @Provides
    @Singleton
    MovieDao provideMovieDao(MovieAppDatabase database) { return database.movieDao(); }

    @Provides
    @Singleton
    VideoDao provideVideoDao(MovieAppDatabase database) { return database.videoDao(); }


    // --- NETWORK INJECTION ---

    private static String BASE_URL = "https://api.themoviedb.org/3/movie/";

    @Provides
    Gson provideGson() { return new GsonBuilder().create(); }

    @Provides
    OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();
        return okHttpClient;
    }

    @Provides
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    MovieService provideMovieService(Retrofit restAdapter) {
        return restAdapter.create(MovieService.class);
    }

    @Provides
    MovieDatasource provideMovieDatasource(MovieService movieService) {
        return new MovieDatasource(movieService);
    }


    // --- REPOSITORY INJECTIONS ---

    @Provides
    NetworkUtils provideNetworkUtils(Application application) {
        return new NetworkUtils(application);
    }

    @Provides
    @Singleton
    MovieRepository provideMovieRepository(MovieDatasource movieDatasource, MovieDao movieDao, VideoDao videoDao) {
        return new MovieRepository(movieDatasource, movieDao, videoDao);
    }

    // --- INJECTIONS FOR NETWORKBOUNDRESOURCE ---
    // Those are the two fields injected in the NetworkBoundResource class

    @Provides
    Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }


    // --- DOMAIN INJECTIONS ---

    @Provides
    GetPopularMoviesUseCase provideGetPopularMoviesUseCase(MovieRepository movieRepository) {
        return new GetPopularMoviesUseCase(movieRepository);
    }

    @Provides
    GetTopRatedMoviesUseCase provideGetTopRatedMoviesUseCase(MovieRepository movieRepository) {
        return new GetTopRatedMoviesUseCase(movieRepository);
    }

    @Provides
    GetDetailMovieUseCase provideGetDetailMovieUseCase(MovieRepository movieRepository) {
        return new GetDetailMovieUseCase(movieRepository);
    }

    // --- For Listener in HomeViewModel. Here we are asking to take the implentation in HomeActivity
//    @Provides
 //   HomeViewModel.Listeners provideHomeViewModelListener(HomeActivity homeActivity){
 //       return homeActivity;
 //   }

}

