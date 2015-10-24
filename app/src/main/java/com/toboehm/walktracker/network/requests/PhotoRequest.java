package com.toboehm.walktracker.network.requests;

import android.location.Location;

import com.toboehm.walktracker.network.responsmodel.PPhotoResponse;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Tobias Boehm on 24.10.2015.
 */
public class PhotoRequest extends Request<PPhotoResponse> {

    private final double minLongitude;
    private final double maxLongitude;
    private final double minLatitude;
    private final double maxLatitude;


    private PhotoRequest(final Location lastLocation, final Location currentLocation){

        // determine long/lat deltas
        final double longitudeDelta = Math.abs(currentLocation.getLongitude() - lastLocation.getLongitude());
        final double latitudeDelta = Math.abs(currentLocation.getLatitude() - lastLocation.getLatitude());

        // calculate ~50meter area around the current location by assuming that both locations lie around ~100meters apart
        minLongitude = currentLocation.getLongitude() - longitudeDelta / 2;
        maxLongitude = currentLocation.getLongitude() + longitudeDelta / 2;

        minLatitude = currentLocation.getLatitude() - latitudeDelta / 2;
        maxLatitude = currentLocation.getLatitude() + latitudeDelta / 2;
    }

    @Override
    public Observable<PPhotoResponse> getConfiguredObservable() {
        return panoramioEndpoint.getPanorama(minLongitude, minLatitude, maxLongitude, maxLatitude)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(3);
    }
}
