package com.konggit.appmvpdaggerted.view;

import com.konggit.appmvpdaggerted.model.ItemTalk;

import java.util.List;

/**
 * Created by erik on 08.01.2018.
 */

public interface IListFragmentView {
    void setTalkListAdapter(List<ItemTalk> itemTalkList, int totalTalks);
    void addListToAdapter(List<ItemTalk> itemTalkList);
    void showProgressDialog();
    void hideProgressDialog();
    void replaceToDetailFragment(int id);
    void startService();
    void stopService();
}
