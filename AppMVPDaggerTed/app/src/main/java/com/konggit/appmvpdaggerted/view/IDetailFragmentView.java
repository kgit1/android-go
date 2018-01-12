package com.konggit.appmvpdaggerted.view;

/**
 * Created by erik on 08.01.2018.
 */

public interface IDetailFragmentView {
    void updateView(String url, String name, String desc);
    void showProgressDialog();
    void hideProgressDialog();
    void startService();
    void stopService();
    void replaceToShowFragment(String mediaUrl);
}
