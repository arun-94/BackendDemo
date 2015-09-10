package com.tisser.puneet.tisserartisan.Module;

import android.app.Activity;

import com.tisser.puneet.tisserartisan.Scope.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule
{
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @PerActivity
    @Provides
    Activity activity() {
        return this.activity;
    }
}
