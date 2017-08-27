package com.konggit.appnot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    private TextView dateTextView;
    private TextView sumTextView;
    private EditText numbersEditText;
    private Button addButton;

    private Map<Date, Results> info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateTextView = (TextView) findViewById(R.id.dateTextView);
        sumTextView = (TextView) findViewById(R.id.sumTextView);
        numbersEditText = (EditText) findViewById(R.id.numbersEditText);
        addButton = (Button) findViewById(R.id.addButton);

        info = new TreeMap<>();

        addButton.setOnClickListener(new AddButtonListener());

        dateTextView.setText(formattedDate(currentDate()));


    }

    private class AddButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            //Log.i("Button", "0 " + info);

            if (numbersEditText.getText().toString().trim().length() > 0) {

                int number = Integer.valueOf(numbersEditText.getText().toString());

                if (!info.isEmpty()) {

                    //Log.i("Button", "00 " + info);

                    if (!info.containsKey(normalizedDate())) {

                        Map<Date, Integer> path = new TreeMap<>();
                        path.put(currentDate(), number);

                        Results results = new Results(number, path);

                        info.put(normalizedDate(), results);

                        Log.i("Button", "01 " + number);
                        Log.i("Button", "02 " + path);
                        Log.i("Button", "03 " + results);
                        Log.i("Button", "04 " + info);

                        updateActivity(number);

                    } else {

                        Results results = info.get(normalizedDate());

                        int recalculatedNumber = results.getNumbers() + number;

                        results.setNumbers(recalculatedNumber);

                        results.getPath().put(currentDate(), number);

                        Log.i("Button", "11 " + number);
                        Log.i("Button", "12 " + results.getPath());
                        Log.i("Button", "13 " + results);
                        Log.i("Button", "14 " + info);

                        updateActivity(recalculatedNumber);
                    }

                } else {

                    Map<Date, Integer> path = new TreeMap<>();
                    path.put(currentDate(), number);

                    Results results = new Results(number, path);

                    info.put(normalizedDate(), results);

                    Log.i("Button", "21 " + number);
                    Log.i("Button", "22 " + path);
                    Log.i("Button", "23 " + results);
                    Log.i("Button", "24 " + info);

                    updateActivity(number);
                }

            }else{

                Toast.makeText(getApplicationContext(),"FIELD IS EMPTY", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private Date currentDate() {

        Date currentDate = Calendar.getInstance().getTime();

        Log.i("Info", "1 " + currentDate.toString());

        return currentDate;
    }

    private String formattedDate(Date currentDate) {

        String formattedDate;

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        formattedDate = formatter.format(currentDate);

        return formattedDate;
    }

    private Date normalizedDate() {

        String formattedDate = formattedDate(currentDate());

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.ERA, 0);
        Date normalizedDate = calendar.getTime();

        //Log.i("Info", "2 " + normalizedDate.toString());

        try {
            normalizedDate = formatter.parse(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Log.i("Info", "3 " + normalizedDate.toString());

        return normalizedDate;
    }

    private void updateActivity(int number) {

        sumTextView.setText(String.valueOf(number));
        numbersEditText.setText("");
        toJsonConverter(info);

    }

    private JSONObject toJsonConverter(Map<Date, Results> info) {

        JSONObject jsonObject = new JSONObject();

        try {

            for (Date date : info.keySet()) {

                Log.i("JSON", date.toString());

                JSONObject objectInner = new JSONObject();

                JSONObject objectInnerInner = new JSONObject();

                for (Date pathDate : info.get(date).getPath().keySet()) {

                    int pathNumber = info.get(date).getPath().get(pathDate);

                    objectInnerInner.put(pathDate.toString(), pathNumber);

                    //Log.i("JSOOoooOOooN in", objectInnerInner.toString());

                }

                //Log.i("JSOOoooOOooN in out", objectInnerInner.toString());

                objectInner.put("numbers", info.get(date).getNumbers());

                objectInner.put("path", objectInnerInner);

                jsonObject.put(formattedDate(date), objectInner);

            }


        } catch (JSONException e) {

            Log.i("JSONException", e.toString());

        }

        Log.i("JSOOoooOOooN", jsonObject.toString());
        Log.i("JSOOoooOOooN bytes", String.valueOf(jsonObject.toString().getBytes().length));

        return jsonObject;
    }

}

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