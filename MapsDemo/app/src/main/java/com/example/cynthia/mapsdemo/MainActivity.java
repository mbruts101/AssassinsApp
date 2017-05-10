package com.example.cynthia.mapsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements MapFragment.onPlayerLocationChangedListener {
    public Button strikeButt;
    public double playerLat;
    public double playerLon;
    public double targetLat;
    public double targetLon;

    MapFragment mapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String response = " ";
        setContentView(R.layout.activity_main);
        strikeButt = (Button) findViewById(R.id.killButton);
        mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        Client myClient = new Client("172.90.101.88", 8080,MainActivity.this, strikeButt);
        myClient.execute();






    }

    @Override
    public void sendPlayerLocation(double lat, double lon)
    {
        playerLat = lat;
        playerLon = lon;
    }

    public void causeTargetUpdate(double lat, double lon)
    {
        mapFragment.updateTargetLoc(lat,lon);
    }

}
