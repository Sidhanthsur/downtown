package in.co.mkube.downtown;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class GroundOverlayActivity extends FragmentActivity
        implements  OnMapReadyCallback {
    private static final int TRANSPARENCY_MAX = 100;

    private final List<BitmapDescriptor> mImages = new ArrayList<BitmapDescriptor>();
    LatLngBounds newarkBounds = new LatLngBounds(
            new LatLng(13.004484, 80.220226 ),       // South west corner
            new LatLng(13.015322, 80.241773));

    GoogleMap googleMap;
    
    public String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ground_overlay);
        Intent intent=getIntent();
        s=intent.getStringExtra("card num");
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
         googleMap = mapFragment.getMap();

           }

    @Override
    public void onMapReady(GoogleMap map) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(13.012071, 80.235851), 16));
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
      if(s.equals("one")) map.addMarker(new MarkerOptions().position(new LatLng(13.011222, 80.236031)).title("EG 30"));
          else if (s.equals("two")) map.addMarker(new MarkerOptions().position(new LatLng(13.012239, 80.236829)).title("Main Gallery"));
          else if  (s.equals("three"))  map.addMarker(new MarkerOptions().position(new LatLng(13.012239, 80.236829)).title("Henry Maudslay Hall"));
            else
          map.addMarker(new MarkerOptions().position(new LatLng(13.012239, 80.236829)).title("Henry Maudslay Hall"));

        map.addMarker(new MarkerOptions().position(new LatLng(13.011139, 80.236381)).title("Food Court"));


        mImages.clear();
        mImages.add(BitmapDescriptorFactory.fromResource(R.drawable.map));
        map.setMyLocationEnabled(true);
        mGroundOverlay = map.addGroundOverlay(new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.map))
                .positionFromBounds(newarkBounds));
        map.getUiSettings().setZoomControlsEnabled(true);

        // Override the default content description on the view, for accessibility mode.
        // Ideally this string would be localised.

    }



}