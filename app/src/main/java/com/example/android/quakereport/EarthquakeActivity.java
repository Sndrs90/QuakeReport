/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

/*        // Create a fake list of earthquake locations.
        ArrayList<Earthquake> earthquakes = new ArrayList<Earthquake>();
        earthquakes.add(new Earthquake(2.4, "San Francisco", "Feb 2, 2016"));
        earthquakes.add(new Earthquake(2.4, "London", "Feb 2, 2016"));
        earthquakes.add(new Earthquake(2.4, "Tokyo", "Feb 2, 2016"));
        earthquakes.add(new Earthquake(2.4, "Mexico City", "Feb 2, 2016"));
        earthquakes.add(new Earthquake(2.4, "Moscow", "Feb 2, 2016"));
        earthquakes.add(new Earthquake(2.4, "Rio de Janeiro", "Feb 2, 2016"));
        earthquakes.add(new Earthquake(2.4, "Paris", "Feb 2, 2016"));*/

        // Create a fake list of earthquakes.
        final ArrayList<Earthquake> earthquakes = QueryUtils.extractEarthquakes();

        //Create an adapter and make link with ListView to show ArrayList earthquakes on screen
        EarthquakeAdapter adapter = new EarthquakeAdapter(this, earthquakes);
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        earthquakeListView.setAdapter(adapter);

        // Set a click listener to open browser with url when the list item is clicked on
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the Earthquake object at the given position the user clicked on
                Earthquake currentEarthquake = earthquakes.get(position);

                // create and send an implicit intent that contains the website URL
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(currentEarthquake.getUrl())));
            }
        });
    }
}
