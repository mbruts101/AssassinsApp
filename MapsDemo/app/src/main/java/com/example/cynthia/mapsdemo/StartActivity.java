package com.example.cynthia.mapsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by User on 5/5/2017.
 */

public class StartActivity extends AppCompatActivity implements View.OnClickListener{
    public Button startButt;
    public Button aboutButt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        startButt = (Button) findViewById(R.id.startButton);
        aboutButt = (Button) findViewById(R.id.aboutButton);

        startButt.setOnClickListener(this);
        aboutButt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startButton:
                Intent gameIntent = new Intent(this, MainActivity.class);
                startActivity(gameIntent);
                break;
            case R.id.aboutButton:
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                break;
        }
    }
}
