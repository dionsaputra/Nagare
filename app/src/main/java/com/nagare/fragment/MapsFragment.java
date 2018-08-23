package com.nagare.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nagare.R;
import com.nagare.util.ViewUtil;

public class MapsFragment extends Fragment implements OnMapReadyCallback{

    private View view;

    /*** Google Maps Component ***/
    private GoogleMap kelilingMap;
    private MapView mapView;

    public MapsFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_maps, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = view.findViewById(R.id.maps);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        kelilingMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        kelilingMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        kelilingMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_maps, menu);

        MenuItem item =  menu.findItem(R.id.maps_spinner);
        android.widget.Spinner mapsSpinner = (android.widget.Spinner) android.support.v4.view.MenuItemCompat.getActionView(item);

        ArrayAdapter mapsSpinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.maps_options, android.R.layout.simple_spinner_item);
        mapsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mapsSpinner.setAdapter(mapsSpinnerAdapter);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setCustomView(mapsSpinner);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.keliling_action) {
            showKeliling();
        } else {
            showLapor();
        }
        return true;
    }

    private void showKeliling() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.keliling));
    }

    private void showLapor() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.lapor));

    }
}
