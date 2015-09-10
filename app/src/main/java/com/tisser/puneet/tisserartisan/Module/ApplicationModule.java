package com.tisser.puneet.tisserartisan.Module;

import android.content.Context;

import com.tisser.puneet.tisserartisan.Global.AppManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule
{

    private  AppManager application;

    public ApplicationModule(AppManager application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }


}
