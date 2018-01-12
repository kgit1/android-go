package com.konggit.appdagerexample.internal.di;

import com.konggit.appdagerexample.D2EApplication;
//import net.grzechocinski.android.dagger2example.home.HomeActivity;
//import net.grzechocinski.android.dagger2example.utils.D2ECollectionUtils;

import com.konggit.appdagerexample.home.HomeActivity;
import com.konggit.appdagerexample.utils.D2ECollectionUtils;

public interface D2EGraph {

    void inject(D2EApplication app);

    void inject(HomeActivity app);

    D2ECollectionUtils getD2EStringUtils();
}
