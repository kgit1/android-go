package com.konggit.appdagerexample.internal.di;

import com.konggit.appdagerexample.utils.D2ECollectionUtils;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class D2EUtilsModule {

    @Provides
    @Singleton
    D2ECollectionUtils provideStringUtils() {
        return new D2ECollectionUtils();
    }
}
