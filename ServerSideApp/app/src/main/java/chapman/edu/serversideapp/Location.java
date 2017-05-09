package chapman.edu.serversideapp;

/**
 * Created by DCole on 5/8/17.
 */

import java.util.*;
import java.io.*;

public class Location
{
    private double mLongitude;
    private double mLatitude;

    public Location()
    {
        mLongitude = 0;
        mLatitude = 0;
    }

    public Location(double longitude, double latitude)
    {
        mLongitude = longitude;
        mLatitude = latitude;
    }

    void setLong(double newLong)
    {
        mLongitude = newLong;
    }

    void setLat(double newLat)
    {
        mLatitude = newLat;
    }

    double getLongitude()
    {
        return mLongitude;
    }

    double getLatitude()
    {
        return mLatitude;
    }
}
