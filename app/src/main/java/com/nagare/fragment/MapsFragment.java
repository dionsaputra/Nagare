package com.nagare.fragment;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nagare.DetailFasilitasActivity;
import com.nagare.R;
import com.nagare.base.BaseMainFragment;
import com.nagare.util.ViewUtil;

public class MapsFragment extends BaseMainFragment implements OnMapReadyCallback{

    private MapView mapView;
    private ImageView selectedFasilitasImage;

    /*** Google Maps Component ***/
    private GoogleMap kelilingMap;

    public MapsFragment() {
        super();
        layoutResId = R.layout.fragment_maps;
    }

    @Override
    protected void initComponent() {
        mapView = rootView.findViewById(R.id.maps);
        selectedFasilitasImage = rootView.findViewById(R.id.iv_selected_fasilitas);
        ViewUtil.loadImage(getContext(), selectedFasilitasImage, R.drawable.itb);
    }

    @Override
    protected void setupComponent() {
        setupMapView();
        setupSelectedFasilitasImage();
    }

    private void setupMapView() {
        if (mapView == null) return;
        mapView.onCreate(null);
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    private void setupSelectedFasilitasImage() {
        selectedFasilitasImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewUtil.startNewActivity(getContext(), DetailFasilitasActivity.class, selectedFasilitasImage, R.string.tn_selected_fasilitas);
            }
        });
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

    private void showKeliling() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.keliling));
    }

    private void showLapor() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.lapor));

    }
}
