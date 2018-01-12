package com.konggit.appdagerexample.di;


import com.konggit.appdagerexample.home.BaseActivity;
import com.konggit.appdagerexample.internal.di.D2EGraph;
import com.konggit.appdagerexample.internal.di.D2EUtilsModule;
import com.konggit.appdagerexample.internal.di.SystemServicesModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * This class is in debug/ folder. You can use it to define injects or getters for dependencies only in debug variant
 */
@Singleton
@Component(modules = {DebugDependenciesModule.class, SystemServicesModule.class, D2EUtilsModule.class})
public interface D2EComponent extends D2EGraph {

    void inject(BaseActivity baseActivity);
}
