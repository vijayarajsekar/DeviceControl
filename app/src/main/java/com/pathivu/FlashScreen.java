package com.pathivu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.pathivu.Preferences.Preferences;

public class FlashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);

        if (!new Preferences(this).getTutorialStatus()) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    new Preferences(FlashScreen.this).setTutorialStatus(true);
                    startActivity(new Intent(FlashScreen.this, HomeScreen.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    FlashScreen.this.finish();
                }

            }, 3000);

        } else {
            startActivity(new Intent(FlashScreen.this, HomeScreen.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            this.finish();
        }
    }
}
