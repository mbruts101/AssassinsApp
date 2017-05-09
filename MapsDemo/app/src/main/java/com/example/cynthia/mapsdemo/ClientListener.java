package com.example.cynthia.mapsdemo;

/**
 * Created by abigailatchison on 5/8/17.
 */

import android.widget.Toast;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ClientListener implements Runnable
{
    private Socket connectionSock = null;
    MainActivity activity;
    String output;

    ClientListener(Socket sock, MainActivity activity, String text)
    {
        this.connectionSock = sock;
        this.activity = activity;
        output = text;
    }

    public void run()
    {
        // Wait for data from the server.  If received, output it.
        try
        {
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(connectionSock.getInputStream()));
            while (true)
            {
                // Get data sent from the server
                String serverText = serverInput.readLine();
                if (serverInput != null)
                {
                    output = serverText;

                    if(serverText.contains("win")) //check if the message from the server contains the word "win"
                    {
                        output = "Closing connection for socket " + connectionSock; //if so then close the client socket and leave the game
                        connectionSock.close();
                        break;
                    }
                }
                else
                {
                    // Connection was lost
                    output = "Closing connection for socket " + connectionSock;
                    connectionSock.close();
                    break;
                }

                changeText();
            }
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.toString());
        }
    }

    private void changeText()
    {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity,output,Toast.LENGTH_LONG).show();
            }
        });
    }
}
