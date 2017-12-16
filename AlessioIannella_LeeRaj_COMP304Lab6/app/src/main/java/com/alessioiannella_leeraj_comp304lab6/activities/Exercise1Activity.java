package com.alessioiannella_leeraj_comp304lab6.activities;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alessioiannella_leeraj_comp304lab6.R;
import com.alessioiannella_leeraj_comp304lab6.managers.NetworkManager;
import com.alessioiannella_leeraj_comp304lab6.providers.ApiProvider;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Exercise1Activity extends AppCompatActivity {

    private ConstraintLayout constraintLayoutExercise1Loading;

    private EditText editTextExercise1StartAddress;
    private EditText editTextExercise1EndAddress;

    private TextView textViewErrorStartAddress;
    private TextView textViewErrorEndAddress;
    private TextView textViewDirections;
    private TextView textViewErrorDirections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise1);

        constraintLayoutExercise1Loading = (ConstraintLayout) findViewById(R.id.constraintLayoutExercise1Loading);

        editTextExercise1StartAddress = (EditText) findViewById(R.id.editTextExercise1StartAddress);
        editTextExercise1EndAddress = (EditText) findViewById(R.id.editTextExercise1EndAddress);

        textViewErrorStartAddress = (TextView) findViewById(R.id.textViewErrorStartAddress);
        textViewErrorEndAddress = (TextView) findViewById(R.id.textViewErrorEndAddress);
        textViewDirections = (TextView) findViewById(R.id.textViewDirections);
        textViewErrorDirections = (TextView) findViewById(R.id.textViewErrorDirections);

        hideViews();
    }

    public void handleOnClickGetDirections(View view) {

        hideViews();

        boolean error = false;

        if (editTextExercise1StartAddress.getText().toString().isEmpty()){
            textViewErrorStartAddress.setVisibility(View.VISIBLE);
            error = true;
        }
        if (editTextExercise1EndAddress.getText().toString().isEmpty()){
            textViewErrorEndAddress.setVisibility(View.VISIBLE);
            error = true;
        }

        if (!error){
            constraintLayoutExercise1Loading.setVisibility(View.VISIBLE);

            String startAddress = editTextExercise1StartAddress.getText().toString();
            String endAddress = editTextExercise1EndAddress.getText().toString();

            List<String> addressList = new ArrayList<>();
            addressList.add(startAddress);
            addressList.add(endAddress);

            String urlString = "http://maps.googleapis.com/maps/api/directions/json?";


            try{
                List<Address> addresses = new Geocoder(this, Locale.getDefault()).getFromLocationName(startAddress, 5);

                if (addresses.size() > 0){
                    String origineLatitude = URLEncoder.encode(Double.toString(addresses.get(0).getLatitude()), "UTF-8");
                    String origineLongitude = URLEncoder.encode(Double.toString(addresses.get(0).getLongitude()), "UTF-8");

                    addresses = new Geocoder(this, Locale.getDefault()).getFromLocationName(endAddress, 5);

                    if (addresses.size() > 0){
                        String destinationLatitude = URLEncoder.encode(Double.toString(addresses.get(0).getLatitude()), "UTF-8");
                        String destinationLongitude = URLEncoder.encode(Double.toString(addresses.get(0).getLongitude()), "UTF-8");

                        String origine = "origin=" + origineLatitude + "," + origineLongitude;
                        String destination = "destination=" + destinationLatitude + "," + destinationLongitude;
                        //
                        String sensorValue = URLEncoder.encode("false", "UTF-8");
                        String sensor = "sensor=" + sensorValue;

                        Date date = new Date();
                        long l = date.getTime() / 1000;
                        System.out.println("Time: " +String.valueOf(l));
                        String departureTimeValue = URLEncoder.encode(String.valueOf(l), "UTF-8");
                        System.out.println(departureTimeValue);
                        String departureTime = "departure_time=" + departureTimeValue;
                        //
                        String modeValue = URLEncoder.encode("transit", "UTF-8");
                        String mode = "mode=" + modeValue;

                        //create the url
                        urlString = urlString +origine+"&"+destination+"&"+sensor+"&"+departureTime+"&"+mode;
                        System.out.println(urlString);
                        Log.d("url",urlString);
                        new DirectionsTask().execute(urlString);
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
                textViewErrorDirections.setVisibility(View.VISIBLE);
            }
        }

    }

    private void hideViews() {

        constraintLayoutExercise1Loading.setVisibility(View.GONE);
        textViewErrorStartAddress.setVisibility(View.GONE);
        textViewErrorEndAddress.setVisibility(View.GONE);
        textViewErrorDirections.setVisibility(View.GONE);
    }

    private class DirectionsTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            NetworkManager networkManager = new NetworkManager();
            return networkManager.getDirections(strings[0]);

        }

        @Override
        protected void onPostExecute(String s) {

            hideViews();

            if (s == null || s.isEmpty()){
                textViewErrorDirections.setVisibility(View.VISIBLE);
            }
            else{
                textViewDirections.setText(s);

            }

        }
    }
}
