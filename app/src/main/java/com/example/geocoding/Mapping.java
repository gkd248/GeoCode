package com.example.geocoding;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static android.widget.Toast.makeText;

// Nikhil driving
public class Mapping extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapping);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
//        mapCoordinates();
    }

    private static final String LAT = "lat";
    private static final String LONG = "long";

    private double latitude = 0;
    private double longitude = 0;

    // Nikhil driving
    public void getWeather() throws ExecutionException, InterruptedException, JSONException {
        // this should create map with pin based off of API call with address

        // Dark Sky
        // "https://api.darksky.net/forecast/79afd9f33655764c1f2bb465b2413dc4/"+latitude+","+longitude"

        //Some url endpoint that you may have
        String myUrl = "https://api.darksky.net/forecast/79afd9f33655764c1f2bb465b2413dc4/"+latitude+","+longitude;
        //String to place our result in
        String result;
        //Instantiate new instance of our class
        HttpGetRequest getRequest = new HttpGetRequest();
        //Perform the doInBackground method, passing in our url, result has string of API return
        result = getRequest.execute(myUrl).get();

//        System.out.println(result);

        JSONObject myResponse = new JSONObject(result.toString());

        String temperature = myResponse.getJSONObject("currently").getString("temperature");
        String humid = myResponse.getJSONObject("currently").getString("humidity");
        String wind = myResponse.getJSONObject("currently").getString("windSpeed");
        String precip = myResponse.getJSONObject("currently").getString("precipProbability");

        TextView tempBox = (TextView)findViewById(R.id.temp_val);
        tempBox.setText(temperature+" *F");

        tempBox = (TextView)findViewById(R.id.humidity_val);
        tempBox.setText(humid+" mph");

        tempBox = (TextView)findViewById(R.id.wind_val);
        tempBox.setText(wind+" mph");

        tempBox = (TextView)findViewById(R.id.precip_val);
        tempBox.setText(precip+" %");


    }


    // Gabriel driving
    // Include the OnCreate() method here too, as described above.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Get the coordinates from the intent extras
        latitude = getIntent().getDoubleExtra(LAT, 0);
        longitude = getIntent().getDoubleExtra(LONG, 0);

        Toast myToast = makeText(this, Double.toString(latitude)+","+Double.toString(longitude),
                Toast.LENGTH_SHORT);
        myToast.show();

        // Add a marker based off user input,
        // and move the map's camera to the same location.
        LatLng location = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(location)
                .title("Mapping"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(latitude, longitude), 12));
//        googleMap.setZoom(12);

        try{
            getWeather();
        }
        catch (Exception e){
            
        }
    }

    // Gabriel driving
    public class HttpGetRequest extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params){

            String result = "";
            try {
                URL url = new URL("https://api.darksky.net/forecast/79afd9f33655764c1f2bb465b2413dc4/" + latitude + "," + longitude);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");

                int responseCode = con.getResponseCode();

                if (responseCode == 200) {

                    //Connect to our url
                    con.connect();

                    // building JSON response
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer content = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    in.close();
                    con.disconnect();
//                    JSONObject myResponse = new JSONObject(content.toString());

                    result = content.toString();

                    //String status = myResponse.getString("status");

                    //Look for info we need to display on map

                }
            }
            catch (IOException e){
//                Toast connection = makeText(this, "Error connecting",
//                        Toast.LENGTH_SHORT);
//                connection.show();
            }
            catch(Exception e){
//                e.printStackTrace();
//                Toast connection = makeText(this, "Other error",
//                        Toast.LENGTH_SHORT);
//                connection.show();
            }
            return result;

        }
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
        }
    }
}
