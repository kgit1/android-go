package com.konggit.appmvpdaggerted.presenter;

import com.konggit.appmvpdaggerted.model.ItemTalk;
import com.konggit.appmvpdaggerted.model.Tags;
import com.konggit.appmvpdaggerted.view.IListFragmentView;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.simple.SimpleTextRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by erik on 12.01.2018.
 */

public class ListFragmentPresenterImpl implements IListFragmentPresenter {

    int offset = 0;

    private static final String URL_LIST_TALKS_API = "https://api.ted.com/v1/talks.json?api-key=umdz5qctsk4g9nmqnp5btsmf&limit=30";

    private IListFragmentView view;
    int totalTalks;
    private SpiceManager spiceManager;

    @Inject
    public ListFragmentPresenterImpl() {
    }

    @Override
    public void init(IListFragmentView view) {
        this.view = view;
    }

    @Override
    public void onResume(SpiceManager spiceManager) {
        view.startService();
        this.spiceManager = spiceManager;
        String url = URL_LIST_TALKS_API + "&offset=" + String.valueOf(offset);
        sendRequest(url, spiceManager, false);
    }

    @Override
    public void onPause() {
        view.stopService();
    }

    @Override
    public void onLoadMore() {
        // формируем новый URL в зависисмости от смещения
        if (offset <= totalTalks - 30) {
            offset += 30;
            String url = URL_LIST_TALKS_API + "&offset=" + String.valueOf(offset);
            sendRequest(url, spiceManager, true);
        }
    }

    @Override
    public void onItemClick(ItemTalk itemTalk) {
        int id = itemTalk.getId();
        view.replaceToDetailFragment(id);
    }

    @Override
    public void addListToAdapter(List<ItemTalk> itemTalkList) {
        view.addListToAdapter(itemTalkList);
    }

    private void sendRequest(String url, SpiceManager spiceManager, boolean isLoadMore) {
        SimpleTextRequest request = new SimpleTextRequest(url);
        // если это не догрузка в лист, то показываем основной прогрессбар в тулбаре
        if (!isLoadMore) {
            view.showProgressDialog();
        }
        spiceManager.execute(request, "jsonTEDTalks" + String.valueOf(offset), DurationInMillis.ONE_HOUR, new TedApiJsonRequestListener());
    }

    private final class TedApiJsonRequestListener implements RequestListener<String> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            view.hideProgressDialog();
        }

        @Override
        public void onRequestSuccess(String result) {
            view.hideProgressDialog();
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONObject counts = jsonObject.getJSONObject(Tags.TAG_COUNTS);
                totalTalks = counts.getInt(Tags.TAG_TOTAL);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            view.setTalkListAdapter(getListFronJson(result), totalTalks);
        }

        private List<ItemTalk> getListFronJson(String jsonString) {
            List<ItemTalk> itemTalkList = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray talks = jsonObject.getJSONArray(Tags.TAG_TALKS);
                for (int i = 0; i < talks.length(); i++) {
                    JSONObject jsonTalkItem = talks.getJSONObject(i);
                    JSONObject jsonTalk = jsonTalkItem.getJSONObject(Tags.TAG_TALK);
                    int id = jsonTalk.getInt(Tags.TAG_ID);
                    String name = jsonTalk.getString(Tags.TAG_NAME);
                    String desc = jsonTalk.getString(Tags.TAG_DESC);
                    String published = jsonTalk.getString(Tags.TAG_PUBLISHED);
                    ItemTalk itemTalk = new ItemTalk();
                    itemTalk.setId(id);
                    itemTalk.setName(name);
                    itemTalk.setDescription(desc);
                    itemTalk.setPublished(published);
                    itemTalkList.add(itemTalk);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return itemTalkList;
        }
    }
}