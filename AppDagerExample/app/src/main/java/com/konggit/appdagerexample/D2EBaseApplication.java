package com.konggit.appdagerexample;

import android.app.Application;
import android.content.Context;

import com.konggit.appdagerexample.internal.di.SystemServicesModule;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import com.konggit.appdagerexample.di.D2EComponent;

import com.konggit.appdagerexample.di.DaggerD2EComponent;

public abstract class D2EBaseApplication extends Application {

    private D2EComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        buildComponentAndInject();
    }

    public void buildComponentAndInject() {
        component = DaggerComponentInitializer.init(this);
    }

    public static D2EComponent component(Context context) {
        return ((D2EBaseApplication) context.getApplicationContext()).component;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public final static class DaggerComponentInitializer {

        public static D2EComponent init(D2EBaseApplication app) {
            return DaggerD2EComponent.builder()
                    .systemServicesModule(new SystemServicesModule(app))
                    .build();
        }

    }
}
