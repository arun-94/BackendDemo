package com.tisser.puneet.tisserartisan.Module;

import android.content.Context;

import com.tisser.puneet.tisserartisan.Global.AppManager;
import com.tisser.puneet.tisserartisan.UI.Activity.Navigator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule
{

    private final AppManager application;

    public ApplicationModule(AppManager application)
    {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext()
    {
        return this.application;
    }

    @Provides @Singleton Navigator provideNavigator() {
        return new Navigator();
    }
}
