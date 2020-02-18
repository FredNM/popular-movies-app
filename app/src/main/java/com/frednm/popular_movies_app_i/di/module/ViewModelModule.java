package com.frednm.popular_movies_app_i.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.frednm.popular_movies_app_i.common.factory.FactoryViewModel;
import com.frednm.popular_movies_app_i.di.key.ViewModelKey;
import com.frednm.popular_movies_app_i.features.detail.DetailViewModel;
import com.frednm.popular_movies_app_i.features.home.HomeViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel bindHomeViewModel(HomeViewModel homeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel.class)
    abstract ViewModel bindDetailViewModel(DetailViewModel homeViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(FactoryViewModel factory);
}
