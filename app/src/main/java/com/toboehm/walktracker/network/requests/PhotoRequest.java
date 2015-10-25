package com.toboehm.walktracker.network.requests;

import android.location.Location;
import android.util.Log;

import com.toboehm.walktracker.network.responsmodel.PPhotoResponse;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Tobias Boehm on 24.10.2015.
 */
public class PhotoRequest extends Request<PPhotoResponse> {

    private static final double AREA_DELTA = 0.00075;


    public static PhotoRequest createFor(final Location currentLocation) {
        return new PhotoRequest(currentLocation);
    }


    private final double minLongitude;
    private final double maxLongitude;
    private final double minLatitude;
    private final double maxLatitude;


    private PhotoRequest(final Location currentLocation) {

        // calculate a picture area around the current location
        minLongitude = currentLocation.getLongitude() - AREA_DELTA;
        maxLongitude = currentLocation.getLongitude() + AREA_DELTA;

        minLatitude = currentLocation.getLatitude() - AREA_DELTA / 2;
        maxLatitude = currentLocation.getLatitude() + AREA_DELTA / 2;

        Log.v("PhotoRequest Vars", "minLongitude = " + minLongitude +
                ", maxLongitude = " + maxLongitude +
                ", minLatitude = " + minLatitude +
                ", maxLatitude = " + maxLatitude);
    }

    @Override
    public Observable<PPhotoResponse> getConfiguredObservable() {
        return panoramioEndpoint.getPanorama(minLongitude, minLatitude, maxLongitude, maxLatitude)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(3);
    }
}
