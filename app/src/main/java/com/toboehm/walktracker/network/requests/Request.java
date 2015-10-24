package com.toboehm.walktracker.network.requests;


import com.toboehm.walktracker.network.endpoints.Panoramio;
import com.toboehm.walktracker.network.responsmodel.BaseModel;

import retrofit.RestAdapter;
import retrofit.converter.JacksonConverter;
import rx.Observable;

/**
 * Created by Tobias Boehm on 15.10.2015.
 */
public abstract class Request<RESULT extends BaseModel> {

    protected static final Panoramio panoramioEndpoint = new RestAdapter.Builder()
            .setEndpoint(Panoramio.BASE_PATH).setConverter(new JacksonConverter())
            .build().create(Panoramio.class);

    public abstract Observable<RESULT> getConfiguredObservable();
}
