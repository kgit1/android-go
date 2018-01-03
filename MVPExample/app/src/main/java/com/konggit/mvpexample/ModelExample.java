package com.konggit.mvpexample;

import java.util.Observable;

/**
 * Created by erik on 03.01.2018.
 */

public interface ModelExample {

    Observable changeText();
    Observable request(String query);
}
