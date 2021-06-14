package com.example.appsfinalproject.fragments.owner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appsfinalproject.R;
import com.example.appsfinalproject.model.Local;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.List;


public class ViewLocalsInMapsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private FirebaseFirestore db;

    public static ViewLocalsInMapsFragment newInstance() {
        ViewLocalsInMapsFragment fragment = new ViewLocalsInMapsFragment();
        fragment.db = FirebaseFirestore.getInstance();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_locals_in_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng cali = new LatLng(3.4, -76.5);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cali, 13));

        db.collection("local").get().addOnSuccessListener(
                command -> {
                    Log.e(">>>", "Trajo los locales para verlos en el mapa");
                    List<Local> locals = command.toObjects(Local.class);
                    Geocoder geocoder = new Geocoder(getContext());
                    for(Local local : locals) {
                        try {
                            Address address = geocoder.getFromLocationName(local.getDireccion(), 1).get(0);
                            LatLng pos = new LatLng(address.getLatitude(), address.getLongitude());
                            googleMap.addMarker(new MarkerOptions().position(pos).title(local.getNombreLocal()).snippet(local.getDireccion()));
                        } catch (IOException e) {
                            Log.e(">>>", "Error al convertir la direccion en un Latlng: " + e.getMessage());
                        }
                    }
                }
        );
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}