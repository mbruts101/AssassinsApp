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
        strikeButt = (Button) findViewById(R.id.killButton);

        Client myClient = new Client("172.90.101.88", 8080,MainActivity.this, strikeButt);
        myClient.execute();

    }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.killButton){

            }
    }
}
