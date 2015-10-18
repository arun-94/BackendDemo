package com.tisser.puneet.tisserartisan.Injection.Component;

import android.content.Context;

import com.tisser.puneet.tisserartisan.UI.Activity.BaseActivity;
import com.tisser.puneet.tisserartisan.Module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent
{
    void inject(BaseActivity baseActivity);

    //Exposed to sub-graphs
    Context context();
}

