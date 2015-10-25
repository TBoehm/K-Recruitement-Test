package com.toboehm.walktracker;

import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.toboehm.walktracker.network.requests.PhotoRequest;
import com.toboehm.walktracker.network.responsmodel.PPhotoResponse;
import com.toboehm.walktracker.views.PhotosAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/**
 * Created by Tobias Boehm on 24.10.2015.
 */
public class MainActivity extends AppCompatActivity implements LocationListener {

    private static final int LOCATION_UPDATE_INTERVAL_MS = 10000; // around every 10 seconds;
    private static final int MIN_DISTANCE_BETWEEN_WAYPOINTS_M = 100; // meters
    private static final int MIN_ACCURACY_M = 50; // meters radius for 68% acc


    @Bind(R.id.ma_start_stop_fab)
    FloatingActionButton startStopFAB;
    @Bind(R.id.ma_listview)
    ListView photosListV;

    private GoogleApiClient googleApiClient;
    private boolean isTracking = false;
    private Location lastLocation;
    private PhotosAdapter photosAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupActionbar();
        setupGoogleServices();
        updateStartStopButton();
        setupPhotosListView();
    }

    private void setupPhotosListView() {

        this.photosAdapter = new PhotosAdapter(this);
        photosListV.setAdapter(this.photosAdapter);
    }

    private void setupGoogleServices() {
        final GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);
        builder.addApi(LocationServices.API)
                .addConnectionCallbacks(createGoogleConnectionCallbackListener())
                .addOnConnectionFailedListener(createGoogleConnectionFailedListener());
        this.googleApiClient = builder.build();
        this.googleApiClient.connect();
    }

    private GoogleApiClient.OnConnectionFailedListener createGoogleConnectionFailedListener() {
        return new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(ConnectionResult connectionResult) {
                Log.e("Google API", "onConnectionFailed - " + connectionResult.getErrorMessage());
            }
        };
    }

    private GoogleApiClient.ConnectionCallbacks createGoogleConnectionCallbackListener() {
        return new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(Bundle bundle) {
                startStopFAB.setVisibility(View.VISIBLE);
            }

            @Override
            public void onConnectionSuspended(int i) {
                // TODO think about what to do in this case
            }
        };
    }

    private void setupActionbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.ma_start_stop_fab)
    void onStartStopClicked(final View button){

        this.isTracking = !isTracking;

        toggleTracking();
        updateStartStopButton();
        informUser();
    }

    private void toggleTracking() {

        if(isTracking){

            startTracking();

        }else{

            stopTracking();
        }
    }

    private void startTracking() {

        // remove old track pictures from list view
        photosAdapter.clear();

        // configure and register for location updates
        registerForLocationUpdates();

        // get first "last" location and if it is not null get picture for this "start" location
        this.lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if(this.lastLocation != null){

            requestPanoramioPhoto(this.lastLocation);
        }
    }

    private void registerForLocationUpdates() {

        final LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(LOCATION_UPDATE_INTERVAL_MS);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onLocationChanged(final Location currentLocation) {

        // if we don't have a "last" location
        if(lastLocation == null){

            lastLocation = currentLocation;
            
            // if the new location is accurate enough and the distance between last location and current location is at least 100 meters
        }else{

            final float distance = lastLocation.distanceTo(currentLocation);
            Log.v("Point distance", "Distance between last location and current location is about " + distance + "m. Accuracy is " + currentLocation.getAccuracy());
            if((currentLocation.getAccuracy() <= MIN_ACCURACY_M) &&
                    (distance >= MIN_DISTANCE_BETWEEN_WAYPOINTS_M)){

                requestPanoramioPhoto(currentLocation);
                lastLocation = currentLocation;
            }
        }
    }

    private void requestPanoramioPhoto(final Location currentLocation) {

        PhotoRequest.createFor(currentLocation)
                .getConfiguredObservable()
                .subscribe(new Observer<PPhotoResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.v("PhotoRequest", "onCompleted called");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w("PhotoRequest Error", e.getMessage());
                    }

                    @Override
                    public void onNext(PPhotoResponse photoResponse) {

                        Log.d("PPhotoResponse", "PPhotoResponse is " + photoResponse);
                        if (photoResponse.hasPhotos()) {
                            photosAdapter.insert(photoResponse.photos.get(0), 0);
                            Log.v("PPhotoResponse", "PPhotoResponse contained at least one picture which was added to the adapter");
                        }
                    }
                });
    }


    private void stopTracking() {
        
        lastLocation = null;
        LocationServices.FusedLocationApi.removeLocationUpdates(this.googleApiClient, this);
    }

    private void informUser() {

        if(isTracking){
            Toast.makeText(MainActivity.this, R.string.ma_tracking_started, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this, R.string.ma_tracking_stopped, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateStartStopButton(){

        if(isTracking){
            startStopFAB.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_stop));
        }else{
            startStopFAB.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_start));
        }
    }

}
