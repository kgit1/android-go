package com.konggit.appmvpdaggerted.presenter;

import com.konggit.appmvpdaggerted.view.IShowFragmentView;
import com.konggit.appmvpdaggerted.view.ShowFragment;

import javax.inject.Inject;

/**
 * Created by erik on 12.01.2018.
 */

public class ShowFragmentPresenterImpl implements IShowFragmentPresenter {

    private IShowFragmentView view;

    @Inject
    public ShowFragmentPresenterImpl() {
    }

    @Override
    public void init(IShowFragmentView view) {
        this.view = view;
    }
}
