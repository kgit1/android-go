package com.konggit.appdagerexample.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DebugDependenciesModule {

    @Singleton
    @Provides
    DebugDependency provideDebugDependency() {
        return new DebugDependency();
    }
}
