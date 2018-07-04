package com.example.vinay.gpslogger;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    MyLocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup Activity:
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener(this);
        MainActivityButtonSetup.setup(this);

        // Toggle Start Button:
        ((Button) findViewById(R.id.button)).performClick();

        // Manually Request Location Update:
        requestLocation();


    }

    public void requestLocation() {
        try {
            Location locationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location locationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            TextView textView = (TextView) findViewById(R.id.textView);

            if ((locationGps == null) && (locationNetwork == null)) {
                textView.append("\nLast Location Unknown");
            } else if ((locationGps != null) && (locationNetwork == null)) {
                textView.append("\nGPS Location: " + locationGps.toString());
                textView.append("\nNetwork Location: Unknown");
            } else if ((locationGps == null) && (locationNetwork != null)) {
                textView.append("\nGPS Location: Unknown");
                textView.append("\nNetwork Location: " +  locationNetwork.toString());
            } else if ((locationGps != null) && (locationNetwork != null)) {
                textView.append("\nGPS Location: " + locationGps.toString());
                textView.append("\nNetwork Location: "  + locationNetwork.toString());
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

}
