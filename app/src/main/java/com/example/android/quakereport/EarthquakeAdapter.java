package com.example.android.quakereport;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.drawable.GradientDrawable;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, earthquakes);
    }
    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value.
     */
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }
    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    /**
     * Return the first part of String place with location offset (i.e. "74kn NW of")
     * or "Near the" if String place doesn't contain offset
     */
    private String formatLocationOffset(String place) {
        if (place.contains("km") && place.contains("of")) {
            int i = place.indexOf("of");
            int endFirst = i + 2;
            return place.substring(0, endFirst);
        } else {
            return getContext().getString(R.string.near_the);
        }
    }

    /**
     * Return the second part of String place with primary location (i.e. "Rumoi, Japan")
     * or String place if it doesn't contain location offset
     */
    private String formatPrimaryLocation(String place) {
        if (place.contains("km") && place.contains("of")) {
            int i = place.indexOf("of");
            int startSecond = i + 3;
            int endSecond = place.length();
            return place.substring(startSecond, endSecond);
        } else {
            return place;
        }
    }
    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch(magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the Earthquake object located at this position in the list
        Earthquake currentEarthquake = getItem(position);

        // Find the magnitude TextView in the list_item.xml layout with the ID version_name
        TextView magnitudeView = (TextView) listItemView.findViewById(R.id.magnitude);

        // Format the magnitude to show 1 decimal place
        String formattedMagnitude = formatMagnitude(currentEarthquake.getMagnitude());
        // Display the magnitude of the current earthquake in that TextView
        magnitudeView.setText(formattedMagnitude);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        // Find the location offset TextView in the list_item.xml layout with the ID version_number
        // and set first part of place on this TextView
        TextView locationOffsetTextView = (TextView) listItemView.findViewById(R.id.location_offset_text);
        String firstPart = formatLocationOffset(currentEarthquake.getPlace());
        locationOffsetTextView.setText(firstPart);

        // Find the primary location TextView in the list_item.xml layout with the ID version_number
        // and set second part of place on this TextView
        TextView primaryLocationTextView = (TextView) listItemView.findViewById(R.id.primary_location_text);
        String secondPart = formatPrimaryLocation(currentEarthquake.getPlace());
        primaryLocationTextView.setText(secondPart);

        // Find the date TextView in the list_item.xml layout with the ID image
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_text);
        Date dateObject = new Date(currentEarthquake.getDate());
        String dateToDisplay = formatDate(dateObject);
        dateTextView.setText(dateToDisplay);

        // Find the ImageView in the list_item.xml layout with the ID image
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time_text);
        String timeToDisplay = formatTime(dateObject);
        timeTextView.setText(timeToDisplay);

        // Return the whole list item layout (containing 2 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
