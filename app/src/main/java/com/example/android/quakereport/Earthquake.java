package com.example.android.quakereport;

public class Earthquake {
    private double mMagnitude;
    private String mCity;
    private long mDate;

    public Earthquake(double magnitude, String city, long date) {
        mMagnitude = magnitude;
        mCity = city;
        mDate = date;
    }

    public double getMagnitude() {
        return mMagnitude;
    }

    public String getCity() {
        return mCity;
    }

    public long getDate() {
        return mDate;
    }
}
