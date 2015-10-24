package com.toboehm.walktracker.views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.toboehm.walktracker.R;
import com.toboehm.walktracker.network.responsmodel.PPhoto;

import butterknife.Bind;
import butterknife.ButterKnife;


public class PPhotoView extends RelativeLayout {

    @Bind(R.id.ppv_image)
    ImageView photoIV;
    @Bind(R.id.ppv_owner)
    TextView ownerTV;


    public PPhotoView(Context context) {
        super(context);

        final View view = inflate(context, R.layout.pphoto_view, this);
        ButterKnife.bind(context, view);
    }

    public void setContent(final PPhoto pphoto){

        Picasso.with(getContext()).load(pphoto.photoFileURL).into(photoIV);
        photoIV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent openPhotoWebIntent = new Intent(Intent.ACTION_VIEW);
                openPhotoWebIntent.setData(Uri.parse(pphoto.photoWebURL));
                getContext().startActivity(openPhotoWebIntent);
            }
        });

        ownerTV.setText(getContext().getString(R.string.ma_photo_subheading, pphoto.owner));
        ownerTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent openOwnerWebIntent = new Intent(Intent.ACTION_VIEW);
                openOwnerWebIntent.setData(Uri.parse(pphoto.ownerProfileURL));
                getContext().startActivity(openOwnerWebIntent);
            }
        });
    }
}
