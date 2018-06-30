package com.example.cordova.activity;

import android.os.Bundle;

import com.example.cordova.R;
import com.example.cordova.fragment.MainFragment;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.root_layout, new MainFragment()).commit();

    }


}
