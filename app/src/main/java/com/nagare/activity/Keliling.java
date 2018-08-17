package com.nagare.activity;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nagare.R;
import com.nagare.util.MapsUtil;

public class Keliling extends FragmentActivity implements OnMapReadyCallback {

    private Context context = this;
    private GoogleMap kelilingMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keliling);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fr_keliling);

        MapsUtil.showMaps(context, mapFragment);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        kelilingMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        kelilingMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        kelilingMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
