package com.example.Readmill;

import android.app.Activity;
import android.os.Bundle;

public class ReaderActivity extends Activity {

    private UserPreferences userPreferences;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        userPreferences = new UserPreferences(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        userPreferences.save(this);
    }
}
