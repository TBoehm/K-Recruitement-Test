package com.toboehm.walktracker.network.responsmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Tobias Boehm on 24.10.2015.
 */
public class PPhoto {

    private static final String PHOTO_ID_ATTRIBUTE = "photo_id";
    private static final String PHOTO_FILE_URL_ATTRIBUTE = "photo_file_url";
    private static final String PHOTO_WEB_URL_ATTRIBUTE = "photo_url";
    private static final String LONGITUDE_ATTRIBUTE = "longitude";
    private static final String LATITUDE_ATTRIBUTE = "latitude";
    private static final String OWNER_NAME_ATTRIBUTE = "owner_name";
    private static final String OWNER_PROFILE_URL_ATTRIBUTE = "owner_url";


    public final int id;
    public final String photoWebURL;
    public final String photoFileURL;
    public final double longitude;
    public final double latitude;
    public final String owner;
    public final String ownerProfileURL;


    public PPhoto(@JsonProperty(PHOTO_ID_ATTRIBUTE) final int id, @JsonProperty(PHOTO_WEB_URL_ATTRIBUTE) final String photoWebURL,
                  @JsonProperty(PHOTO_FILE_URL_ATTRIBUTE) final String photoFileURL, @JsonProperty(LONGITUDE_ATTRIBUTE) final double longitude,
                  @JsonProperty(LATITUDE_ATTRIBUTE) final double latitude, @JsonProperty(OWNER_NAME_ATTRIBUTE) final String owner,
                  @JsonProperty(OWNER_PROFILE_URL_ATTRIBUTE) final String ownerProfileURL) {
        this.id = id;
        this.photoWebURL = photoWebURL;
        this.photoFileURL = photoFileURL;
        this.longitude = longitude;
        this.latitude = latitude;
        this.owner = owner;
        this.ownerProfileURL = ownerProfileURL;
    }
}
