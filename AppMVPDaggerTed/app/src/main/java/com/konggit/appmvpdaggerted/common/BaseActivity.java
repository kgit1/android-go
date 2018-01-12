package com.konggit.appmvpdaggerted.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.konggit.appmvpdaggerted.app.TalksTEDApp;
import com.konggit.appmvpdaggerted.dependenciesInjection.components.ITalksTEDAppComponent;

/**
 * Created by erik on 08.01.2018.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstance){

       super.onCreate(savedInstance);
       setupComponent(TalksTEDApp.get(this).getAppComponent());
    }

    protected abstract void SetupComponent(ITalksTEDAppComponent appComponent);
}
