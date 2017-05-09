package chapman.edu.serversideapp;

/**
 * Created by DCole on 5/8/17.
 */
import java.net.Socket;
import java.util.*;
import java.io.*;

public class Player
{
    private String mId;
    private double mLongitude;
    private double mLatitude;
    private String mAddress;
    private Location mLocation;
    private Player mTarget;
    private Socket mSocket;

    public Player(Socket socket)
    {
        this.mSocket = socket;
    }

    public void updateLocation(double longitude, double latitude)
    {
        mLocation.setLong(longitude);
        mLocation.setLat(latitude);
    }

    public Socket getmSocket()
    {
        return mSocket;
    }

    public String getId()
    {
        return mId;
    }

    public Player getTarget()
    {
        return mTarget;
    }

    public void setTarget(Player target)
    {
        mTarget = target;
    }

    public String getAddress()
    {
        return mAddress;
    }

    public void setAddress(String address)
    {
        mAddress = address;
    }

    public Location getLocation()
    {
        return mLocation;
    }

    public void setLocation(Location l)
    {
        mLocation = l;
    }

    public void killTarget()
    {
        System.out.println(this.getTarget().getId() + "was killed by" + this.getId());
        Player p = this.getTarget().getTarget();
        mTarget = p;
    }
}