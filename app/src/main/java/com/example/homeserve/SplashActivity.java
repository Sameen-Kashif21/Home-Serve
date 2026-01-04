package com.example.homeserve;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    // How long the splash screen will stay (2 seconds)
    private static final int SPLASH_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Connect this Activity (Java) with its XML design
        setContentView(R.layout.activity_splash);

        // Wait 2 seconds, then go to Login screen
        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            // Intent = "go from this screen to another screen"
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);

            // Close Splash so user can't come back using back button
            finish();

        }, SPLASH_TIME);
    }
}
