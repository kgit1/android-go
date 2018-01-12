package com.konggit.appmvpdaggerted.common;

import android.app.Fragment;

import com.konggit.appmvpdaggerted.dependenciesInjection.IHasComponent;

/**
 * Created by erik on 08.01.2018.
 */

public abstract class BaseFragment extends Fragment {

    protected <T> T getComponent(Class<T> componentType) {

        return componentType.cast(((IHasComponent<T>) getActivity()).getComponent());
    }
}
