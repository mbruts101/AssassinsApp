package com.example.cynthia.mapsdemo;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.net.Socket;
import java.io.DataOutputStream;
import java.io.IOException;

public class Client extends AsyncTask<String, String, String>
{

    String IPAddress;
    int portNum;
    Button sendInput;
    MainActivity activity;
    String outputTxt;

    boolean sendKill = false;

    DataOutputStream serverOutput;

    public Client(String IP, int port, MainActivity activity, Button btn)
    {

        IPAddress = IP;
        portNum = port;
        this.activity = activity;
        sendInput = btn;



    }

    protected String doInBackground(String... params)
    {
        runProgram();
        return outputTxt;
    }


    public void runProgram()
    {
        try
        {

            String hostname = IPAddress;
            int port = portNum;

            //InetAddress inetAddress = InetAddress.getByAddress(hostname.getBytes());

            outputTxt = "Connecting to server on port " + port;

            changeText();

            Socket connectionSock = new Socket(hostname, port);

            serverOutput = new DataOutputStream(connectionSock.getOutputStream());

            outputTxt = "Connection made.";

            // Start a thread to listen and display data sent by the server
            ClientListener listener = new ClientListener(connectionSock, activity, outputTxt);
            Thread theThread = new Thread(listener);
            theThread.start();

            serverOutput.writeBytes("Hello World");
            serverOutput.writeBytes("whats up \n");

            changeText();

            sendInput.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /// send message to server that this user made a kill
                    sendKill = true;

                }
            });

            while(true)
            {
                if(sendKill)
                {
                    try{
                        serverOutput.writeBytes("KILL \n");
                    }catch (IOException e)
                    {
                        outputTxt = e.getMessage();
                        changeText();
                    }
                    sendKill = false;
                }


            }

        }
        catch (IOException e)
        {
            outputTxt = e.getMessage();
            changeText();
        }
    }

    private void changeText()
    {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity,outputTxt,Toast.LENGTH_LONG).show();
            }
        });
    }


}

