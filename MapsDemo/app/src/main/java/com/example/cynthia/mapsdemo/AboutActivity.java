package com.example.cynthia.mapsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by User on 5/5/2017.
 */

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_about);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backButton){
            Intent menuIntent = new Intent(this, StartActivity.class);
            startActivity(menuIntent);
        }
    }
}
