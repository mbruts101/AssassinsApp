package chapman.edu.serversideapp;

/**
 * Created by abigailatchison on 4/30/17.
 */
import android.os.AsyncTask;
import android.widget.TextView;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;


public class Server extends AsyncTask<String, String, String>
{
    // Maintain list of all client sockets for broadcast and the game board
    private ArrayList<Socket> socketList;
    private GameBoard game;
    String IPAddress;
    int port;

    MainActivity activity;
    TextView textView;

    String message = "";

    public Server(MainActivity activity, TextView tv)
    {
        socketList = new ArrayList<Socket>();
        game = new GameBoard(); //initializes the game board
        IPAddress = getIpAddress();
        port = 8080;
        this.activity = activity;
        textView = tv;
    }

    protected String doInBackground(String... params)
    {
        getConnection();
        return message;
    }

    private void getConnection()
    {
        // Wait for a connection from the client
        try
        {
            message += "Waiting for client connections on port " + port + " at IP " + IPAddress;
            ServerSocket serverSock = new ServerSocket(port);
            changeText();
            // This is an infinite loop, the user will have to shut it down
            // using control-c
            while (true)
            {
                Socket connectionSock = serverSock.accept();
                // Add this socket to the list
                socketList.add(connectionSock);
                // Send to ClientHandler the socket and arraylist of all sockets and the board
                ClientHandler handler = new ClientHandler(connectionSock, this.socketList, this.game, activity, message, textView);
                Thread theThread = new Thread(handler);
                theThread.start();
            }
            // Will never get here, but if the above loop is given
            // an exit condition then we'll go ahead and close the socket
            //serverSock.close();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress
                            .nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += "Server running at : "
                                + inetAddress.getHostAddress();
                    }
                }
            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }
        return ip;
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