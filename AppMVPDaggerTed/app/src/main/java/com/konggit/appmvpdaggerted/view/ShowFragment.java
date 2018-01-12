package com.konggit.appmvpdaggerted.view;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.konggit.appmvpdaggerted.R;
import com.konggit.appmvpdaggerted.common.BaseFragment;
import com.konggit.appmvpdaggerted.dependenciesInjection.components.IMainActivityComponent;

import javax.inject.Inject;

/**
 * Created by erik on 08.01.2018.
 */

public class ShowFragment extends BaseFragment implements IShowFragmentView{

    @Inject
    ShowFragmentPresenterImpl presenter;

    public static final String TAG="ShowFragment";
    public static final String BUNDLE_URL="bundleURL";

    private Activity activity;
    private String mediaURL;

    public ShowFragment(){}

    public static ShowFragment newInstance(String mediaURL){
        ShowFragment showFragment = new ShowFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_URL, mediaURL);
        showFragment.setArguments(bundle);
        return  showFragment;
    }

    //Lifecycle methods
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments()!=null &&getArguments().containsKey(BUNDLE_URL)){
            this.mediaURL = getArguments().getString(BUNDLE_URL);
        }else{
            throw new IllegalArgumentException("Must be created through newInstance(int id)");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        this.getComponent(IMainActivityComponent.class).inject(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        presenter.init(this);
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_show, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        VideoView videoView =(VideoView)view.findViewById(R.id.videoView);
        videoView.setVideoURI(Uri.parse(mediaURL));
        videoView.setMediaController(new MediaController((activity)));
        videoView.requestFocus(0);
        videoView.start();
    }












































}
