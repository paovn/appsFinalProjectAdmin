package com.example.appsfinalproject.fragments.owner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.appsfinalproject.R;
import com.example.appsfinalproject.activities.LocalActivityOwner;
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


public class ViewLocalsInMapsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, View.OnClickListener {
    private FirebaseFirestore db;
    private Button goToLocalBtn;
    private Local selectedLocal;

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
        goToLocalBtn = view.findViewById(R.id.go_to_local_from_map_btn);
        goToLocalBtn.setOnClickListener(this);
        goToLocalBtn.setVisibility(View.INVISIBLE);
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
        googleMap.setOnMarkerClickListener(this);
        db.collection("local").get().addOnSuccessListener(
                command -> {
                    Log.e(">>>", "Trajo los locales para verlos en el mapa");
                    List<Local> locals = command.toObjects(Local.class);
                    for(Local local : locals) {
                        LatLng pos = new LatLng(local.getLat(), local.getLng());
                        googleMap.addMarker(new MarkerOptions().position(pos).title(local.getNombreLocal()).snippet(local.getDireccion()));
                    }
                }
        );
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        goToLocalBtn.setVisibility(View.VISIBLE);
        LatLng pos = marker.getPosition();
        Log.e(">>>", pos.toString());
        db.collection("local").whereEqualTo("lat", pos.latitude).whereEqualTo("lng", pos.longitude).get()
                .addOnSuccessListener(
                        command -> {
                            selectedLocal = command.getDocuments().get(0).toObject(Local.class);
                        }
                ).addOnFailureListener(
                        command -> {
                            Log.e(">>>", "No pudo traer el local basado en la direccion");
                        }
        );
        return false;
    }

    @Override
    public void onClick(View v) {
        if(selectedLocal != null) {
            goToLocalInventory(selectedLocal.getId());
        }
    }

    public void goToLocalInventory(String localId) {
        Intent i = new Intent(getContext(), LocalActivityOwner.class);
        i.putExtra("localId", localId);
        startActivity(i);
    }
}