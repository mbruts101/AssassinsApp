package chapman.edu.serversideapp;

/**
 * Created by abigailatchison on 5/4/17.
 */
import android.widget.TextView;

import java.net.Socket;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Scanner;
import java.util.ArrayList;

public class ClientHandler implements Runnable
{
    private Socket connectionSock = null;
    private Player connectionPlayer = null;
    private ArrayList<Player> playerList;
    private Game gameState;

    MainActivity activity;
    String message;
    TextView textView;

    ClientHandler(Socket sock, ArrayList<Player> List, Game gameState, MainActivity activity, String msg, TextView tv, Player player)
    {
        this.connectionSock = sock;
        this.connectionPlayer = player;
        this.playerList = List;	// Keep reference to master list
        this.gameState = gameState;
        this.activity = activity;
        message = msg;
        textView = tv;

        gameState.assignTargets();
    }

    public void run()
    {
        // Get data from a client and send it to everyone else
        try
        {
            message += "Connection made with socket " + connectionSock; //make a connection to each client
            BufferedReader clientInput = new BufferedReader(
                    new InputStreamReader(connectionSock.getInputStream()));
            
            changeText();

            while (true)
            {

                // Get data sent from a client
                String clientText = clientInput.readLine(); //reads in what the client inputs
                if (clientText != null)
                {
                    message += "Received: " + clientText;

                    changeText();

                    if(clientText.contains("KILL"))
                    {
                        gameState.removePlayer(connectionPlayer.getTarget(),connectionPlayer);


                        //notify player eliminated that they suck

                        DataOutputStream eliminatedPlayerStream = new DataOutputStream(connectionPlayer.getTarget().getmSocket().getOutputStream());
                        eliminatedPlayerStream.writeBytes("You are *clap clap* cut off aka eliminated \n");
                    }
                    else if(clientText.contains("Location"))
                    {
                        //location update
                        DataOutputStream outputStream = new DataOutputStream(connectionPlayer.getTarget().getmSocket().getOutputStream());
                        outputStream.writeBytes(clientText + "\n");
                    }
                }
                else
                {
                    // Connection was lost
                    System.out.println("Closing connection for socket " + connectionSock);
                    // Remove from arraylist
                    playerList.remove(connectionSock);
                    connectionSock.close();
                    break;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.toString());
            // Remove from arraylist
            playerList.remove(connectionSock);
        }
    }

    private int[] parseStringArray(String[] numberStrs) //takes the string and parses it and puts it into an array
    {
        int[] numbers = new int[numberStrs.length];
        for(int i = 0;i < numberStrs.length;i++)
        {
            // Note that this is assuming valid input
            // If you want to check then add a try/catch
            // and another index for the numbers if to continue adding the others
            numbers[i] = Integer.parseInt(numberStrs[i]);
        }
        numbers[0] = numbers[0];
        return numbers;
    }

    private void changeText()
    {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(message);
            }
        });
    }
}