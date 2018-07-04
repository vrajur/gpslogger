package com.example.vinay.gpslogger;

import android.app.Activity;
import android.location.LocationManager;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by vinay on 7/2/18.
 */

public class MainActivityButtonSetup {


    static MyLocationListener listener;

    static public void setup(final MainActivity activity) {
        // Get textview:
        final TextView textView = (TextView) activity.findViewById(R.id.textView);
        textView.setText("GpsLogger Application Initialized");
        textView.setMovementMethod(new ScrollingMovementMethod());

        // Get buttons:
        final Button startButton = (Button) activity.findViewById(R.id.button);
        final Button stopButton = (Button) activity.findViewById(R.id.button2);

        // Set Callbacks:
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.append("\nStart button clicked!");
                try {
                    activity.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, activity.locationListener);
                    startButton.setBackgroundColor(0xFF00FF00);
                    stopButton.setBackgroundColor(0xFFAAAAAA);

                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.append("\nStop button clicked!");
                activity.locationManager.removeUpdates(activity.locationListener);
                startButton.setBackgroundColor(0xFFAAAAAA);
                stopButton.setBackgroundColor(0xFFFF0000);
            }
        });
        Button clearLogButton = (Button) activity.findViewById(R.id.button3);
        clearLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity.getApplicationContext(), "Cleared Log", Toast.LENGTH_SHORT).show();
                textView.setText("");
            }
        });
        ImageButton gpsLocationButton = (ImageButton) activity.findViewById(R.id.imageButton);
        gpsLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity.getApplicationContext(), "Location Requested", Toast.LENGTH_SHORT).show();
                activity.requestLocation();
            }
        });

        // Check permissions:
        if (PermissionsChecker.checkLocationPermissions(activity)) {
            textView.append("\nLocation Permissions Enabled");
        } else {
            textView.append("\nLocation Permissions Disabled");
            textView.append("\nRequesting Location Permissions...........");
            PermissionsChecker.requestLocationPermissions(activity);

            if (PermissionsChecker.checkLocationPermissions(activity)) {
                textView.append("\nLocation Permissions Granted!");
            } else {
                textView.append("\nLocation Permissions Denied...please enable under settings");
                activity.finish();
            }
        }
    }

}
