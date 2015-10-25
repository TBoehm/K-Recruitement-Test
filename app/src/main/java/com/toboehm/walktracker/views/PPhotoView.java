package com.toboehm.walktracker.views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
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

    private int photoWidth;
    private int photoHeight;

    public PPhotoView(Context context) {
        super(context);
        init();
    }

    public PPhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PPhotoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.pphoto_view, this);
        ButterKnife.bind(this);

        photoWidth = getContext().getResources().getDisplayMetrics().widthPixels;
        photoHeight = photoWidth / 5 * 3;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.bind(getContext(), this);
    }

    public void setContent(final PPhoto pphoto) {

        Picasso.with(getContext()).load(pphoto.photoFileURL)
                .placeholder(getContext().getResources().getDrawable(R.color.primary_light))
                .resize(photoWidth, photoHeight).centerCrop()
                .into(photoIV);
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
