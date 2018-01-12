package com.konggit.appmvpdaggerted.presenter;

import com.konggit.appmvpdaggerted.common.BaseFragmentPresenter;
import com.konggit.appmvpdaggerted.model.ItemTalk;
import com.konggit.appmvpdaggerted.view.IListFragmentView;
import com.octo.android.robospice.SpiceManager;

import java.util.List;

/**
 * Created by erik on 12.01.2018.
 */

public interface IListFragmentPresenter extends BaseFragmentPresenter<IListFragmentView>{
    void onResume(SpiceManager spiceManager);
    void onPause();
    void onLoadMore();
    void onItemClick(ItemTalk itemTalk);
    void addListToAdapter(List<ItemTalk> itemTalkList);
}
