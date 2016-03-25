package com.example.jude.popularmovies;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Jude on 3/16/2016.
 */

public class SettingsActivity extends Activity
        {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
