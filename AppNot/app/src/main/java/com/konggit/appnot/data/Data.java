package com.konggit.appnot.data;

import com.konggit.appnot.Results;

import java.util.Date;
import java.util.Map;

public interface Data {

    Map<Date, Results> getData(String saveName);

    void setData(Map<Date, Results> data, String saveName);

}
