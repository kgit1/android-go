package com.konggit.appmvpdaggerted.dependenciesInjection.components;

import com.konggit.appmvpdaggerted.app.TalksTEDApp;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by erik on 07.01.2018.
 */
@Singleton
@Component(modules = {TalksTEDAppModule.class})
public interface ITalksTEDAppComponent {

    void inject(TalksTEDApp app);
}
