package com.konggit.appnot.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.konggit.appnot.Results;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class InternalStorage implements Data {

    Context context;

    public InternalStorage(Context context) {
        this.context = context;
    }

    @Override
    public Map<Date, Results> getData(String saveName) {

        String jsonData;
        StringBuilder sb = new StringBuilder();
        Map<Date, Results> data;

        try {
            FileInputStream fis = context.openFileInput(saveName);

            while (fis.available() > 0) {

                sb.append((char) fis.read());

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        jsonData = sb.toString();

        data = jsonToMap(stringToJsonObject(jsonData));

        Log.i("GET internal", "data " + data.toString());

        return data;
    }

    @Override
    public void setData(Map<Date, Results> data, String saveName) {

        Log.i("SET internal", "data " + data.toString());

        JSONObject jsonObject;

        jsonObject = mapToJson(data);

        String jsonString = jsonObject.toString();

        try {

            Log.i("FILE internal1", "data " + data.toString());

            FileOutputStream fos = context.openFileOutput(saveName, Context.MODE_PRIVATE);

            fos.write(jsonString.getBytes());

            Log.i("FILE internal2", "data " + data.toString());

            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private JSONObject stringToJsonObject(String jsonData) {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject = new JSONObject(jsonData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    private JSONObject mapToJson(Map<Date, Results> data) {

        JSONObject jsonObject = new JSONObject();

        try {
            for (Date date : data.keySet()) {

                JSONObject jsonObjectInner = new JSONObject();
                JSONObject jsonObjectDeepInner = new JSONObject();

                for (Date timeStamp : data.get(date).getPath().keySet()) {

                    int timestampNumber = data.get(date).getPath().get(timeStamp);

                    jsonObjectDeepInner.put(timeStamp.toString(), timestampNumber);

                }

                jsonObjectInner.put("dayDefaultNumber", data.get(date).getDayDefaultNumber());
                jsonObjectInner.put("numbers", data.get(date).getNumbers());
                jsonObjectInner.put("path", jsonObjectDeepInner);

                jsonObject.put(date.toString(), jsonObjectInner);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    private Map<Date, Results> jsonToMap(JSONObject jsonObject) {

        Map<Date, Results> data = new TreeMap<>();

        Iterator<String> iterator = jsonObject.keys();

        while (iterator.hasNext()) {

            String mapKey = iterator.next();

            Date date = stringToDateLong(mapKey);
            Results results = new Results();

            try {

                JSONObject jsonObjectInner = (JSONObject) jsonObject.get(mapKey);

                int dayDefaultNumber = jsonObjectInner.getInt("dayDefaultNumber");

                int numbers = Integer.valueOf(jsonObjectInner.getString("numbers"));
                JSONObject jsonObjectDeepInner = jsonObjectInner.getJSONObject("path");

                Map<Date, Integer> path = new TreeMap<>();

                Iterator<String> iteratorDeep = jsonObjectDeepInner.keys();

                while (iteratorDeep.hasNext()) {

                    String pathKey = iteratorDeep.next();

                    Date timestamp = stringToDateLong(pathKey);
                    int pathNumber = jsonObjectDeepInner.getInt(pathKey);

                    path.put(timestamp, pathNumber);

                }

                results.setDayDefaultNumber(dayDefaultNumber);
                results.setNumbers(numbers);
                results.setPath(path);

                data.put(date, results);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return data;
    }

    private Date stringToDateShort(String dateString) {

        //example - Sun Aug 21
        DateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        try {

            date = formatter.parse(dateString);

        } catch (ParseException e) {

            e.printStackTrace();

        }

        return date;
    }

    private Date stringToDateLong(String stringDate) {

        //example - Mon Sep 04 00:15:12 GMT+00:00 2017
        DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

        Calendar calendar = Calendar.getInstance();

        Date date = calendar.getTime();

        try {

            date = formatter.parse(stringDate);

        } catch (ParseException e) {

            e.printStackTrace();

        }

        return date;
    }

}















/*

public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
    Map<String, Object> retMap = new HashMap<String, Object>();

    if(json != JSONObject.NULL) {
        retMap = toMap(json);
    }
    return retMap;
}

public static Map<String, Object> toMap(JSONObject object) throws JSONException {
    Map<String, Object> map = new HashMap<String, Object>();

    Iterator<String> keysItr = object.keys();
    while(keysItr.hasNext()) {
        String key = keysItr.next();
        Object value = object.get(key);

        if(value instanceof JSONArray) {
            value = toList((JSONArray) value);
        }

        else if(value instanceof JSONObject) {
            value = toMap((JSONObject) value);
        }
        map.put(key, value);
    }
    return map;
}

public static List<Object> toList(JSONArray array) throws JSONException {
    List<Object> list = new ArrayList<Object>();
    for(int i = 0; i < array.length(); i++) {
        Object value = array.get(i);
        if(value instanceof JSONArray) {
            value = toList((JSONArray) value);
        }

        else if(value instanceof JSONObject) {
            value = toMap((JSONObject) value);
        }
        list.add(value);
    }
    return list;
}

 */







