package com.konggit.appmvpdaggerted.dependenciesInjection.modules;

import com.konggit.appmvpdaggerted.dependenciesInjection.components.IMainActivityComponent;

import dagger.Module;
import dagger.Provides;

/**
 * Created by erik on 08.01.2018.
 */
@Module
public class MainActivityModule {

    private IMainActivityView view;

    public MainActivityModule(IMainActivityView view) {
        this.view = view;
    }

    @Provides
    public IMainActivityView provideView() {
        return view;
    }

    @Provides
    public MainActivityPresenterImpl provideMainActivityPresenterImpl() {
        return new MainActivityPresenterImpl();
    }

    @Provides
    public ListFragmentPresenterImpl provideListFragmentPresenterImpl() {
        return new ListFragmentPresenterImpl();
    }

    @Provides
    public DetailFragmentPresenterImpl provideDetailFragmentPresenterImpl() {
        return new DetailFragmentPresenterImpl();
    }

    @Provides
    public ShowFragmentPresenterImpl providesShowFragmentPresenterImpl() {
        return new ShowFragmentPresenterImpl();
    }
}
