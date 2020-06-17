package com.side.lumies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.side.lumies.Intro.IntroActivity;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1500;


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        SharedPreferences prefs3 = getSharedPreferences("IntroCheck", MODE_PRIVATE);
        String timeDiff = prefs3.getString("Check", "false");

        if (timeDiff.equals("true")) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }
            }, SPLASH_DISPLAY_LENGTH);

        } else {

            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();

            if (bundle != null) {

                String mealId = bundle.getString("getChoose");
                if (mealId != null && mealId.equals("true")) {

                    SharedPreferences.Editor editor = getSharedPreferences("IntroCheck", MODE_PRIVATE).edit();
                    editor.putString("Check", "true");
                    editor.apply();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                            SplashActivity.this.startActivity(mainIntent);
                            SplashActivity.this.finish();
                        }
                    }, SPLASH_DISPLAY_LENGTH);
                } else if (mealId != null && mealId.equals("false")) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                            SplashActivity.this.startActivity(mainIntent);
                            SplashActivity.this.finish();
                        }
                    }, SPLASH_DISPLAY_LENGTH);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent mainIntent = new Intent(SplashActivity.this, IntroActivity.class);
                            SplashActivity.this.startActivity(mainIntent);
                            SplashActivity.this.finish();
                        }
                    }, SPLASH_DISPLAY_LENGTH);

                }

            } else {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent mainIntent = new Intent(SplashActivity.this, IntroActivity.class);
                        SplashActivity.this.startActivity(mainIntent);
                        SplashActivity.this.finish();
                    }
                }, SPLASH_DISPLAY_LENGTH);
            }
        }
    }
}
