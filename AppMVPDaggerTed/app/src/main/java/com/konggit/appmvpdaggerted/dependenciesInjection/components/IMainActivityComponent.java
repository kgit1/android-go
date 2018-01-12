package com.konggit.appmvpdaggerted.dependenciesInjection.components;

import com.konggit.appmvpdaggerted.view.MainActivity;
import com.konggit.appmvpdaggerted.dependenciesInjection.ActivityScope;

import dagger.Component;

/**
 * Created by erik on 08.01.2018.
 */
@ActivityScope
@Component(dependencies = ITalksTEDAppComponent.class,
        modules = MainActivityModule.class)
public interface IMainActivityComponent {
    void inject(MainActivity activity);
    void inject(ListFragment talksListFragment);
    void inject(DetailFragment talkDetailFragment);
    void inject(ShowFragment showFragment);
}
