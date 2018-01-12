package com.konggit.appmvpdaggerted.presenter;

import com.konggit.appmvpdaggerted.common.BaseFragmentPresenter;
import com.konggit.appmvpdaggerted.view.IDetailFragmentView;
import com.octo.android.robospice.SpiceManager;

/**
 * Created by erik on 08.01.2018.
 */

public interface IDetailFragmentPresenter extends BaseFragmentPresenter<IDetailFragmentView>{
    void onResume(SpiceManager spiceManager, int id);
    void onPause();
    void playButtonOnClick();
}
