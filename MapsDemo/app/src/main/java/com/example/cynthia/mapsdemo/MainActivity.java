package com.example.cynthia.mapsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String response = " ";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Client myClient = new Client("10.0.2.15", 8080, response);
        myClient.execute();

        Toast.makeText(this,response, Toast.LENGTH_LONG).show();

    }
}
