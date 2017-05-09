package com.example.cynthia.mapsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public Button strikeButt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String response = " ";
        setContentView(R.layout.activity_main);
        Client myClient = new Client("10.0.2.15", 8080, response);
        myClient.execute();
        strikeButt = (Button) findViewById(R.id.killButton);
        Toast.makeText(this,response, Toast.LENGTH_LONG).show();

    }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.killButton){

            }
    }
}
