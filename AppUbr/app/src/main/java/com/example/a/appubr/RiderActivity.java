package com.example.a.appubr;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

//https://github.com/googlemaps/ - google maps examples
public class RiderActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;

    Button buttonCallCancelTaxi;
    private boolean requestActive;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastKnownLocation != null) {
                    updateMap(lastKnownLocation);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /*Button button = new Button(getApplicationContext());
        button.setText("Test");
        button.setBackgroundColor(Color.rgb(70, 80, 90));//button.setX(800);
        //button.setY(1800);
        addContentView(button,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));*/

        buttonCallCancelTaxi = (Button) findViewById(R.id.buttonCallCancel);

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Request");
        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {

                        requestActiveTrue();

                    }
                }
            }
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i("Location", location.toString());

                updateMap(location);
                /*mMap.addMarker(new MarkerOptions().position(userLocation).title(latitude + " : " + longitude));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));*/
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        //if SDK < 23(Marshmallow) - we don't need permission and can ask location immediately
        if (Build.VERSION.SDK_INT < 23) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }//if SDK > 23
        else {
            //check do we have permission, if haven't - ask
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //ask permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            }//if have - run code to update location ,take lastKnownLocation to do this
            else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastKnownLocation != null) {

                    updateMap(lastKnownLocation);

                }
            }
        }

        /*// Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

    public void callCancelTaxi(View view) {
        if (requestActive) {

            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Request");
            query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        if (objects.size() > 0) {

                            for (ParseObject object : objects) {

                                object.deleteInBackground();

                            }

                            requestActiveFalse();

                        }
                    }
                }
            });

            makeToast("Taxi cancelled");
            requestActiveFalse();
        } else {
            requestActiveTrue();
            callTaxi();
        }
    }

    public void requestActiveFalse() {
        Log.i("Info", "Cancel a taxi");
        requestActive = false;
        buttonCallCancelTaxi.setText("Call a taxi");
    }

    public void requestActiveTrue() {
        Log.i("Info", "Call a taxi");
        requestActive = true;
        buttonCallCancelTaxi.setText("Cancel a taxi");
    }

    public void makeToast(String toast) {
        Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
    }

    public void callTaxi() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (lastKnownLocation != null) {
                ParseObject request = new ParseObject("Request");
                request.put("username", ParseUser.getCurrentUser().getUsername());
                ParseGeoPoint parseGeoPoint = new ParseGeoPoint(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());

                request.put("location", parseGeoPoint);
                request.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(getApplicationContext(), "Taxi called", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            } else {
                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateMap(Location location) {
        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
        //to remove any previous markers
        mMap.clear();
        //add new marker for current location
        mMap.addMarker(new MarkerOptions().position(userLocation).title("You location"));

        //move camera to marker
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
        // Zoom in, animating the camera.
        // mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null);

        //move camera to marker + zoom
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 12));
    }


}

 /*Provide marker position.

    private void pointToPosition(LatLng position) {
    //Build camera position
    CameraPosition cameraPosition = new CameraPosition.Builder()
            .target(position)
            .zoom(17).build();
    //Zoom in and animate the camera.
    mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
}*/


/*
184
down vote
favorite
97
I have 10 markers in the GoogleMap. I want to zoom in as much as possible and keep all markers in view? In the earlier version this can be achieved from zoomToSpan() but in v2 I have no idea how about doing that. Further, I know the radius of the circle that needs to be visible.

android google-maps google-maps-api-2 google-maps-android-api-2
shareimprove this question
edited Oct 6 '14 at 11:58

JJD
19.7k29124204
asked Feb 12 '13 at 8:17

Asanka Senavirathna
1,76231116
1
My answer will give you an easiest solution for latest Android apis. stackoverflow.com/a/38013331/1348522 – Zumry Mohamed Nov 8 '16 at 10:12
add a comment
9 Answers
active oldest votes
Не нашли ответ? Задайте вопрос на Stack Overflow на русском.

✕
up vote
555
down vote
accepted
You should use the CameraUpdate class to do (probably) all programmatic map movements.

To do this, first calculate the bounds of all the markers like so:

LatLngBounds.Builder builder = new LatLngBounds.Builder();
for (Marker marker : markers) {
    builder.include(marker.getPosition());
}
LatLngBounds bounds = builder.build();
Then obtain a movement description object by using the factory: CameraUpdateFactory:

int padding = 0; // offset from edges of the map in pixels
CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
Finally move the map:

googleMap.moveCamera(cu);
Or if you want an animation:

googleMap.animateCamera(cu);
That's all :)

Clarification 1

Almost all movement methods require the Map object to have passed the layout process. You can wait for this to happen using the addOnGlobalLayoutListener construct. Details can be found in comments to this answer and remaining answers. You can also find a complete code for setting map extent using addOnGlobalLayoutListener here.

Clarification 2

One comment notes that using this method for only one marker results in map zoom set to a "bizarre" zoom level (which I believe to be maximum zoom level available for given location). I think this is expected because:

The LatLngBounds bounds instance will have northeast property equal to southwest, meaning that the portion of area of the earth covered by this bounds is exactly zero. (This is logical since a single marker has no area.)
By passing bounds to CameraUpdateFactory.newLatLngBounds you essentially request a calculation of such a zoom level that bounds (having zero area) will cover the whole map view.
You can actually perform this calculation on a piece of paper. The theoretical zoom level that is the answer is +∞ (positive infinity). In practice the Map object doesn't support this value so it is clamped to a more reasonable maximum level allowed for given location.
Another way to put it: how can Map object know what zoom level should it choose for a single location? Maybe the optimal value should be 20 (if it represents a specific address). Or maybe 11 (if it represents a town). Or maybe 6 (if it represents a country). API isn't that smart and the decision is up to you.

So, you should simply check if markers has only one location and if so, use one of:

CameraUpdate cu = CameraUpdateFactory.newLatLng(marker.getPosition()) - go to marker position, leave current zoom level intact.
CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 12F) - go to marker position, set zoom level to arbitrarily chosen value 12.
shareimprove this answer
edited May 23 at 11:47

Community♦
11
answered Feb 12 '13 at 8:53

andr
11.5k103449
16
It should be noted that the move cannot happen from the onCreate calls, the view must be created. I needed to use addOnGlobalLayoutListener to get the appropriate sample. – Baruch Even Aug 1 '13 at 17:57
6
@Bar Well, this is partly true. To be precise: some movement methods won't work right after creating the map object. The reason for this is the map object hasn't been measured yet i.e. it has not undergone the layout process. A typical fix is to use addOnGlobalLayoutListener() or post() with an appropriate Runnable. This is exactly the reason why getting marker screen coordinates can't be done in onCreate - see stackoverflow.com/q/14429877/1820695 However you can use some methods even before layout has happened - eg. CameraUpdateFactory.newLatLngBounds() with 4 params. – andr Aug 1 '13 at 19:18
1
Man, you save my day ... was actually trying to do it on my own, calculate manually bounds, and zoom while marker is in it ... was working pretty ugly, but with your simple method, it works like a charm. Thanks – Bibu Mar 8 '14 at 20:40
5
googleMap .setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() { @Override public void onMapLoaded() { googleMap.moveCamera(cu); } }); This will avoid the error on measure. – Ramz Jul 11 '14 at 7:32
1
@user2103379 please read "Clarification 2". – andr Jul 23 '14 at 20:48
show 9 more comments
up vote
22
down vote
Google Map V2: Zoom MarkerOptions within the bounds Best working solution for Android Marshmallow 6 (API 23, API 24) as well,

LatLngBounds.Builder builder = new LatLngBounds.Builder();

//the include method will calculate the min and max bound.
builder.include(marker1.getPosition());
builder.include(marker2.getPosition());
builder.include(marker3.getPosition());
builder.include(marker4.getPosition());

LatLngBounds bounds = builder.build();

int width = getResources().getDisplayMetrics().widthPixels;
int height = getResources().getDisplayMetrics().heightPixels;
int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen

CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);

mMap.animateCamera(cu);*/
