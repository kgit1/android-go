package com.konggit.appnot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.konggit.appnot.data.Data;
import com.konggit.appnot.data.SharedPreferencesStorage;

import java.util.Date;
import java.util.Map;

public class SharedPreferencesListActivity extends AppCompatActivity {

    public static String PREFERENCES_VALUE_NAME = "DATA_JSON_STRING";

    private Data sharedPreferencesStorage;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences_list);

        setTitle("SharedPreferences List");

        sharedPreferencesStorage = new SharedPreferencesStorage(getApplicationContext());

        Map<Date, Results> data = sharedPreferencesStorage.getData(PREFERENCES_VALUE_NAME);

        listView = (ListView) findViewById(R.id.sharedPreferencesListView);
        ResultListAdapter adapter = new ResultListAdapter(getApplicationContext(), data);

        listView.setAdapter(adapter);

    }
}


//////////////////////save get methods

//getSharedPreferences() - Use this if you need multiple preferences files identified by name, which you specify with the first parameter.
//getPreferences() - Use this if you need only one preferences file for your Activity. Because this will be the only preferences file for your Activity, you don't supply a name.
//To write values: Call edit() to get a SharedPreferences.Editor.
//Add values with methods such as putBoolean() and putString().
//Commit the new values with commit()
//To read values: SharedPreferences methods such as getBoolean() and getString().

//    private void save(String jsonData) {
//
//        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("DATA_STRING", jsonData);
//        editor.commit();
//
//    }
//
//    private void multiSave(String saveName, String jsonData) {
//
//        SharedPreferences sharedPreferences = getSharedPreferences("DATA_SAVE1", 0);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(saveName, jsonData);
//        editor.commit();
//
//    }
//
//    private String getSave(String saveName) {
//
//        String jsonData;
//        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
//        jsonData = sharedPreferences.getString(saveName, "empty");
//
//        return jsonData;
//    }
//
//    private String getMultiSave(String saveName) {
//
//        String jsonData;
//        SharedPreferences sharedPreferences = getSharedPreferences("DATA_SAVE1", 0);
//        jsonData = sharedPreferences.getString(saveName, "empty");
//
//        return jsonData;
//    }


/*   Storing JSON object in SharedPreferences
To store JSONObject or JSONArray in SharedPreferences, use the toString() method and store as String in SharedPreferences.

To store JSONObject in SharedPreferences

public void putJson(Context context, JSONObject jsonObject) {
	SharedPreferences settings;
	Editor editor;

	settings = context.getSharedPreferences(PREFS_NAME,
			Context.MODE_PRIVATE);
	editor = settings.edit();

	editor.putString("JSONString", jsonObject.toString());

	editor.commit();
}

public void putJson(Context context, JSONObject jsonObject) {
    SharedPreferences settings;
    Editor editor;

    settings = context.getSharedPreferences(PREFS_NAME,
            Context.MODE_PRIVATE);
    editor = settings.edit();

    editor.putString("JSONString", jsonObject.toString());

    editor.commit();
}

To get JSONObject from SharedPreferences

public ArrayList<Product> getJson(Context context, String category) {
	SharedPreferences settings;
	String json;
	JSONArray jPdtArray;
	Product product = null;
	ArrayList<Product> products = null;

	settings = context.getSharedPreferences(PREFS_NAME,
			Context.MODE_PRIVATE);
	json = settings.getString("JSONString", null);

	JSONObject jsonObj = null;
		try {
			if (json != null) {
				jsonObj = new JSONObject(json);
				jPdtArray = jsonObj.optJSONArray(category);
				if (jPdtArray != null) {
					products = new ArrayList<Product>();
					for (int i = 0; i < jPdtArray.length(); i++) {
						product = new Product();
						JSONObject pdtObj = jPdtArray
								.getJSONObject(i);
						product.setName(pdtObj.getString("name"));
						product.setId(pdtObj
								.getInt("id"));
						products.add(product);
					}
				}
			}
		} catch (JSONException e) {
		}
		return products;
	}
public ArrayList<Product> getJson(Context context, String category) {
    SharedPreferences settings;
    String json;
    JSONArray jPdtArray;
    Product product = null;
    ArrayList<Product> products = null;

    settings = context.getSharedPreferences(PREFS_NAME,
            Context.MODE_PRIVATE);
    json = settings.getString("JSONString", null);

    JSONObject jsonObj = null;
        try {
            if (json != null) {
                jsonObj = new JSONObject(json);
                jPdtArray = jsonObj.optJSONArray(category);
                if (jPdtArray != null) {
                    products = new ArrayList<Product>();
                    for (int i = 0; i < jPdtArray.length(); i++) {
                        product = new Product();
                        JSONObject pdtObj = jPdtArray
                                .getJSONObject(i);
                        product.setName(pdtObj.getString("name"));
                        product.setId(pdtObj
                                .getInt("id"));
                        products.add(product);
                    }
                }
            }
        } catch (JSONException e) {
        }
        return products;
    }
    */

/* You convert the json array to a string using toString() method. Then save the string of the json array in a SharedPreferences.

getSharedPreferences("PREFS_NAME",MODE_PRIVATE)

                .edit()

                .putString("jsonarray", jsonArray.toString())

                .commit();


You can convert the string back to json array as below.
SharedPreferences pref = getSharedPreferences("PREFS_NAME", MODE_PRIVATE);

String json_array = pref.getString("jsonarray", null);

JSONArray jsoArray=new JSONArray(json_array);*/