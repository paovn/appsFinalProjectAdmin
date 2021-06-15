package com.example.appsfinalproject.activities.commons;

import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;

import com.example.appsfinalproject.R;
import com.example.appsfinalproject.databinding.ActivityChooseDirectionMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class ChooseDirectionMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ActivityChooseDirectionMapsBinding binding;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChooseDirectionMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Cali and move the camera
        LatLng cali = new LatLng(3.4, -76.5);
        marker = mMap.addMarker(new MarkerOptions().position(cali).title("Marker in Cali"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cali, 10));

        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        marker.setPosition(latLng);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.e(">>>", "onMarkerClick");
        LatLng latLng = marker.getPosition();
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            String add = addresses.get(0).getAddressLine(0)+"\n";
            Intent data = new Intent();
            data.putExtra("address", add);
            data.putExtra("lat", latLng.latitude);
            data.putExtra("lng", latLng.longitude);
            setResult(Activity.RESULT_OK, data);
            Log.e(">>>", "El resultado fue seteado y va a la actividad que lo ha llamado");
            finish();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}