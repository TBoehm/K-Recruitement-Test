package com.toboehm.walktracker.network.endpoints;

import com.toboehm.walktracker.network.responsmodel.PPhotoResponse;

import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Tobias Boehm on 24.10.2015.
 * <p>
 * Example query http://www.panoramio.com/map/get_panoramas.php?set=public&from=0&to=20&minx=-180&miny=-90&maxx=180&maxy=90&size=medium&mapfilter=true
 */
public interface Panoramio {
    String BASE_PATH = "http://www.panoramio.com/map/";

    @Headers({
            "Content-Type: application/json"
    })
    @GET("/get_panoramas.php?set=full&from=0&to=3&size=medium&mapfilter=false")
    Observable<PPhotoResponse> getPanorama(@Query("minx") double minLongitude, @Query("miny") double minLatitude,
                                           @Query("maxx") double maxLongitude, @Query("maxy") double maxLatitude);
}
