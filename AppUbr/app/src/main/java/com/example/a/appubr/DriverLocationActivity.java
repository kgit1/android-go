package com.example.a.appubr;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class DriverLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        intent = getIntent();

        LatLng driverLocation = new LatLng(intent.getDoubleExtra("driverLatitude", 0), intent.getDoubleExtra("driverLongitude", 0));
        LatLng requestLocation = new LatLng(intent.getDoubleExtra("requestLatitude", 0), intent.getDoubleExtra("requestLongitude", 0));
        Log.i("Info driver", "requestLat " + intent.getDoubleExtra("requestLatitude", 0));
        Log.i("Info driver", "requestLng " + intent.getDoubleExtra("requestLongitude", 0));
        Log.i("Info driver", "driverLat " + intent.getDoubleExtra("driverLatitude", 0));
        Log.i("Info driver", "driverLng " + intent.getDoubleExtra("driverLongitude", 0));

        if (!driverLocation.equals(requestLocation)) {
            ArrayList<Marker> markers = new ArrayList<>();
            markers.add(mMap.addMarker(new MarkerOptions().position(driverLocation).title("driverLocation").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))));
            markers.add(mMap.addMarker(new MarkerOptions().position(requestLocation).title("requestLocation").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))));


            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Marker marker : markers) {

                builder.include(marker.getPosition());

            }

            LatLngBounds bounds = builder.build();

            int markersPadding = 290;

            //to avoid error map cant be 0 size - when map didn't load to the moment when method called
            //use on maploadcallback
            /*mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, markersPadding));
                }
            });*/

            //this better way to avoid crash on map size 0 when app cant get in time with map layer calulation and deploy
            int mapWidth = getResources().getDisplayMetrics().widthPixels;
            int mapHeight = getResources().getDisplayMetrics().heightPixels;
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, mapWidth, mapHeight, markersPadding));


        } else {
            mMap.addMarker(new MarkerOptions().position(driverLocation).title("driverLocation"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(driverLocation, 10));
        }

    }

    public void buttonBack(View view) {
        Intent intent = new Intent(getApplicationContext(), DriverActivity.class);

        startActivity(intent);
    }

    public void buttonLogout(View view) {
        ParseUser.logOut();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        startActivity(intent);
    }

    public void buttonRequestList(View view){
        Intent intent = new Intent(getApplicationContext(), DriverActivity.class);
        startActivity(intent);
    }

    public void buttonAcceptRequest(View view) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Request");
        query.whereEqualTo("username", intent.getStringExtra("username"));

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null) {

                    if (objects.size() > 0) {

                        for (ParseObject object : objects) {

                            object.put("driverUserName", ParseUser.getCurrentUser().getUsername());

                            object.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {

                                    if (e == null) {

                                        Intent directionsIntent = new Intent(android.content.Intent.ACTION_VIEW,
                                                Uri.parse("http://maps.google.com/maps?saddr="
                                                        + intent.getDoubleExtra("driverLatitude", 0)
                                                        + ","
                                                        + intent.getDoubleExtra("driverLongitude", 0)
                                                        + "&daddr="
                                                        + intent.getDoubleExtra("requestLatitude", 0)
                                                        + ","
                                                        + intent.getDoubleExtra("requestLongitude", 0)));
                                        startActivity(directionsIntent);

                                    }

                                }
                            });

                        }

                    }

                }

            }
        });


    }
}


//to zoom map to fit all markers
/*You should use the CameraUpdate class to do (probably) all programmatic map movements.

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
CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 12F) - go to marker position, set zoom level to arbitrarily chosen value 12.*/