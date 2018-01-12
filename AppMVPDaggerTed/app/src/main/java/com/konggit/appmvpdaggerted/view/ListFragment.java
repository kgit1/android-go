package com.konggit.appmvpdaggerted.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.konggit.appmvpdaggerted.R;
import com.konggit.appmvpdaggerted.common.BaseFragment;
import com.konggit.appmvpdaggerted.dependenciesInjection.components.IMainActivityComponent;
import com.konggit.appmvpdaggerted.model.ItemTalk;
import com.octo.android.robospice.SpiceManager;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by erik on 08.01.2018.
 */

public class ListFragment extends BaseFragment implements IListFragmentView {

    @Inject
    ListFragmentPresenterImpl presenter;

    protected SpiceManager spiceManager = new SpiceManager(TalksTEDService.class);

    private Activity activity;
    private ListView listView;
    private TalksListAdapter talksListAdapter;
    private View rootView;

    public ListFragment() {
    }

    //Lifecycle override method

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setReatinInstance(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);
        this.getComponent(IMainActivityComponent.class).inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init(this);
        presenter.onResume(spiceManager);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        if (rootView == null) {
            rootView = inflater.inflate(android.support.v4.R.layout.fragment_list, container, false);
            listView = (ListView) rootView.findViewById(R.id.talksListView);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        if (rootView.getParent() != null) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstance) {
        listView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore() {
                presenter.onLoadMore();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                presenter.onItemClick((ItemTalk) listView.getAdapter().getItem(position));
            }
        });
    }

    @Override
    public void onPause() {
        presenter.onPause();
        super.onPause();
    }
    //IListFragmentView methods

    @Override
    public void setTalkListAdapter(List<ItemTalk> itemTalkList, int totalTalks) {
        if (talksListAdapter == null) {
            talksListAdapter = new TalkListAdapter(activity, itemTalkList, totalTalks);
            listView.setAdapter(talksListAdapter);
        } else {
            presenter.addListToAdapter(itemTalkList);
        }
    }

    @Override
    public void addListToAdapter(List<ItemTalk> itemTalkList) {
        talksListAdapter.add(itemTalkList);
    }

    @Override
    public void showProgressDialog() {
        ProgressBar progressBar = (ProgressBar) activity.findViewById(R.id.toolbar_progress_bar);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressDialog() {
        ProgressBar progressBar = (ProgressBar) activity.findViewById(R.id.toolbar_progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void replaceToDetailFragment(int id) {

    }

    @Override
    public void startService() {
        if (!spiceManager.isStarted()) {
            spiceManager.start(activity);
        }
    }

    @Override
    public void stopService() {
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
    }
}
