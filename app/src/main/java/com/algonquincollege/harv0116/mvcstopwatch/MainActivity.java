package com.algonquincollege.harv0116.mvcstopwatch;

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

import model.StopwatchModel;

/**
 *
 * Purpose of this app is to make a stopwatch program with start/pause and reset functionality
 * @author Paul Harvey (harv0116@algonquinlive.com)
 *
 */

public class MainActivity extends AppCompatActivity implements Observer {

    private static final String ABOUT_DIALOG_TAG;

    static {
        ABOUT_DIALOG_TAG = "About Dialog";
    }

    private FloatingActionButton fab;
    private StopwatchModel mStopwatchModel;
    private TextView mStopwatchView;
    private Runnable mUpdateStopwatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences settings = getSharedPreferences( getResources().getString(R.string.app_name), Context.MODE_PRIVATE );

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mStopwatchModel.isRunning())
                    mStopwatchModel.stop();
                else
                    mStopwatchModel.start();
            }
        });

        mStopwatchView = (TextView) findViewById(R.id.textViewStopWatch);

        mStopwatchModel = new StopwatchModel( settings.getInt("hours", 0),
                settings.getInt("minutes", 0),
                settings.getInt("seconds", 0),
                settings.getInt("tenthOfASecond", 0) );
        mStopwatchModel.addObserver(this);
        mUpdateStopwatch = new Runnable() {
            @Override
            public void run() {
                mStopwatchView.setText(mStopwatchModel.toString());

                if (mStopwatchModel.isRunning()) {
                    fab.setImageResource(android.R.drawable.ic_media_pause);
                } else {
                    fab.setImageResource(android.R.drawable.ic_media_play);
                }

            }
        };

        mStopwatchView.setText( mStopwatchModel.toString() );

    }

    // Remember the user's settings for H:M:S:T
    @Override
    protected void onStop() {
        super.onStop();

        // We need an Editor object to make preference changes.
        SharedPreferences settings = getSharedPreferences( getResources().getString(R.string.app_name), Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = settings.edit();

        editor.putInt( "hours",   mStopwatchModel.getmHours() );
        editor.putInt( "minutes", mStopwatchModel.getmMinutes() );
        editor.putInt( "seconds",  mStopwatchModel.getmSeconds() );
        editor.putInt( "tenthOfASecond", mStopwatchModel.getmTenthOfASecond() );

        // Commit the edits!
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            DialogFragment newFragment = new AboutDialogFragment();
            newFragment.show(getFragmentManager(), ABOUT_DIALOG_TAG);
            return true;
        }

        if (id == R.id.action_reset) {
            mStopwatchModel.reset();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void update(Observable observable, Object data) {
        runOnUiThread(mUpdateStopwatch);
    }
}
