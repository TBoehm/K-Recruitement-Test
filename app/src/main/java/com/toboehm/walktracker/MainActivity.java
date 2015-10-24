package com.toboehm.walktracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Tobias Boehm on 24.10.2015.
 */
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.ma_start_stop_fab)
    FloatingActionButton startStopFAB;
    @Bind(R.id.ma_listview)
    ListView photosListV;

    private boolean isTracking = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        updateStartStopButton();
    }

    @OnClick(R.id.ma_start_stop_fab)
    void onStartStopClicked(final View button){

        this.isTracking = !isTracking;

        updateStartStopButton();
        informUser();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}
