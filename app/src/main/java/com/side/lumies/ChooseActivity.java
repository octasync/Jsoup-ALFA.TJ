package com.side.lumies;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        Intent intent = new Intent(ChooseActivity.this, MainActivity.class);
        startActivity(intent);
    }


    public void goToMix(){



    }

}
