package com.toboehm.walktracker.views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.toboehm.walktracker.R;
import com.toboehm.walktracker.network.responsmodel.PPhoto;

/**
 * Created by Tobias Boehm on 22.09.2015.
 */
public class PhotosAdapter extends ArrayAdapter<PPhoto> {

    public PhotosAdapter(final Context context) {
        super(context, R.layout.support_simple_spinner_dropdown_item);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        PPhotoView view = (PPhotoView) convertView;
        if (view == null) {
            view = new PPhotoView(getContext());
        }
        view.setContent(getItem(position));

        return view;
    }

}
