package com.konggit.appdagerexample.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.konggit.appdagerexample.D2EApplication;
import com.konggit.appdagerexample.di.DebugDependency;

import javax.inject.Inject;

public class BaseActivity extends FragmentActivity {

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    DebugDependency debugDependency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        D2EApplication.component(this).inject(this);

        debugDependency.doSthInDebug();
    }
}
