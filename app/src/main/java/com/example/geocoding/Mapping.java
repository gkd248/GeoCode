package com.example.geocoding;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.widget.Toast.makeText;

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

    private static final String LONG = "long";
    private static final String LAT = "lat";
    public void mapCoordinates(){
        // this should create map with pin based off of API call with address

        // Get the count from the intent extras
        double longitude = getIntent().getDoubleExtra(LONG, 0);
        double latitude = getIntent().getDoubleExtra(LAT, 0);

        // Dark Sky
        // "https://api.darksky.net/forecast/79afd9f33655764c1f2bb465b2413dc4/"+latitude+","+longitude"

    }

    // Include the OnCreate() method here too, as described above.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Get the coordinates from the intent extras
        double longitude = getIntent().getDoubleExtra(LONG, 0);
        double latitude = getIntent().getDoubleExtra(LAT, 0);

        Toast myToast = makeText(this, Double.toString(latitude)+","+Double.toString(longitude),
                Toast.LENGTH_SHORT);
        myToast.show();

        // Add a marker based off user input,
        // and move the map's camera to the same location.
        LatLng location = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(location)
                .title("Mapping"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));

        // Dark Sky
//        try{
//            URL url = new URL("https://api.darksky.net/forecast/79afd9f33655764c1f2bb465b2413dc4/"+latitude+","+longitude");
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
//           //Look for info we need to display on map
//
//        }
//            catch (IOException e){
//                Toast connection = makeText(this, "Error connecting",
//                        Toast.LENGTH_SHORT);
//                connection.show();
//            }
//            catch (JSONException e){
//            Toast connection = makeText(this, "JSON Error",
//                    Toast.LENGTH_SHORT);
//            connection.show();
//        }
    }
}
