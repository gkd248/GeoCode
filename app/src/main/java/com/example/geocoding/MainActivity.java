package com.example.geocoding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {

    private static final String YOUR_API_KEY = "AIzaSyARRJsBkisGqJ5_1Vo2QB_Pk2mIMYQVZlw";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private static final String LONG = "long";
    private static final String LAT = "lat";
    public void mapMe(View view) {
        // Create an Intent to start the second activity
        Intent mappingIntent = new Intent(this, Mapping.class);

        // Get the text view that shows the count.
        EditText mapText = (EditText) findViewById(R.id.editText);

        // Get the value of the text view.
        String address = mapText.getText().toString();

        // Checking address is read in
        // Toast myToast = Toast.makeText(this, message, duration);
        Toast myToast = makeText(this, address,
                Toast.LENGTH_SHORT);
        myToast.show();

        // Call API to get coordinates
        // API key: AIzaSyARRJsBkisGqJ5_1Vo2QB_Pk2mIMYQVZlw
        // https://maps.googleapis.com/maps/api/geocode/json?address=+"address"+&key=YOUR_API_KEY;

        try{

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            Address location = addresses.get(0);
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();

            // Add the count to the extras for the Intent.
            mappingIntent.putExtra(LONG, longitude);
            mappingIntent.putExtra(LAT, latitude);

            // Start the new activity.
            startActivity(mappingIntent);
        }
        catch (IOException e){
            Toast connection = makeText(this, "Error connecting",
                    Toast.LENGTH_SHORT);
            connection.show();
        }

//        try{
//            URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address="+address+"&key="+YOUR_API_KEY);
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("GET");
//
//            int responseCode = con.getResponseCode();
//
//            if(responseCode == 200){
//
//                // building JSON response
//                BufferedReader in = new BufferedReader(
//                        new InputStreamReader(con.getInputStream()));
//                String inputLine;
//                StringBuffer content = new StringBuffer();
//                while ((inputLine = in.readLine()) != null) {
//                    content.append(inputLine);
//                }
//                in.close();
//                con.disconnect();
//            }
//
//            JSONObject myResponse = new JSONObject(content.toString());
//            String status = myResponse.getString("status");
//
//            if(status.equals("OK")){
//
//                // Start the new activity.
//                startActivity(mappingIntent);
//            }
//            else{
//
//                Toast errorAlert = makeText(this, "Geocoding attempt failed.",
//                        Toast.LENGTH_SHORT);
//                errorAlert.show();
//            }
//
//            //Call API to get extra info based off coordinates
//
//        }

//        catch (JSONException e){
//            Toast connection = makeText(this, "JSON Error",
//                    Toast.LENGTH_SHORT);
//            connection.show();
//        }


    }
}


