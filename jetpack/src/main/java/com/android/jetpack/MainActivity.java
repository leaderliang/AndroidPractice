package com.android.jetpack;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private LiveDataTimerViewModel mLiveDataTimerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLiveDataTimerViewModel = ViewModelProviders.of(this).get(LiveDataTimerViewModel.class);
        subscribe();
    }

    private void subscribe() {
        final Observer<Long> elapsedTimeObserver = new Observer<Long>() {
            @Override
            public void onChanged(@Nullable final Long aLong) {
                String newText = getResources().getString(R.string.second, aLong);
                ((TextView) findViewById(R.id.tv_time)).setText(newText);
                Log.d("MainActivity", "Updating timer:" + aLong);
            }
        };
        // androidx.lifecycle:lifecycle-extensions:2.0.0@aar
        mLiveDataTimerViewModel.getElapsedTime().observe(this, elapsedTimeObserver);
    }
}