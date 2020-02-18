package com.frednm.popular_movies_app_i.di.component;

import android.app.Application;

import com.frednm.popular_movies_app_i.App;
import com.frednm.popular_movies_app_i.di.module.ActivityModule;
import com.frednm.popular_movies_app_i.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules={AndroidInjectionModule.class, ActivityModule.class,  AppModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

    void inject(App app);
}
