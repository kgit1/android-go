package com.konggit.mvpexample;

import java.util.Observable;

/**
 * Created by erik on 03.01.2018.
 */

public class ModelExampleImpl implements ModelExample{

    private final ViewHolderExample viewHolder;

    public ModelExampleImpl( final ViewHolderExample viewHolder){

        this.viewHolder = viewHolder;
    }


    @Override
    public Observable changeText() {
        return WidgetObservable;
    }

    @Override
    public Observable request(String query) {
        return null;
    }
}
