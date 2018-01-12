package com.konggit.appmvpdaggerted.presenter;

import com.konggit.appmvpdaggerted.view.IMainActivityView;

import javax.inject.Inject;

/**
 * Created by erik on 12.01.2018.
 */

public class MainActivityPresenterImpl implements IMainActivityPresenter {

    private IMainActivityView view;

    @Inject
    public MainActivityPresenterImpl(IMainActivityView view) {
        this.view = view;
    }

    @Override
    public void onBackPressed() {
        view.popFragmentFromStack();
    }
}
