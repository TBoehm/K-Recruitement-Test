package com.toboehm.walktracker.network.responsmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Tobias Boehm on 24.10.2015.
 */
public class PPhotoResponse extends BaseModel {

    private static final String PHOTOS_ATTRIBUTE = "photos";
    private static final String PHOTOS_COUNT_ATTRIBUTE = "count";


    public final int photoCount;
    public final List<PPhoto> photos;


    public PPhotoResponse(@JsonProperty(PHOTOS_ATTRIBUTE) final List<PPhoto> photos,
                          @JsonProperty(PHOTOS_COUNT_ATTRIBUTE) final int photoCount) {
        this.photos = photos;
        this.photoCount = photoCount;
    }

    public boolean hasPhotos() {

        return !photos.isEmpty();
    }
}
