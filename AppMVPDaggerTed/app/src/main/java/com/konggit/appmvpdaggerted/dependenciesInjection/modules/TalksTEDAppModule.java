package com.konggit.appmvpdaggerted.dependenciesInjection.modules;

import android.app.Application;

import com.konggit.appmvpdaggerted.app.TalksTEDApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by erik on 08.01.2018.
 */
@Module
public class TalksTEDAppModule {

    private final TalksTEDApp app;

    public TalksTEDAppModule(TalksTEDApp app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return app;
    }
}
