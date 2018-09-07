package com.nagare.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.nagare.DetailFasilitasActivity;
import com.nagare.R;
import com.nagare.base.BaseMainFragment;
import com.nagare.util.PermissionUtil;
import com.nagare.util.ViewUtil;

public class MapsFragment extends BaseMainFragment  implements
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int LOC_PERMISSION_REQUEST = 1;
    private boolean permissionDenied = false;

    private ImageView selectedFasilitasImage;

    /*** Google Maps Component ***/
    private GoogleMap map;

    public MapsFragment() {
        super();
        layoutResId = R.layout.fragment_maps;
    }

    @Override
    protected void initComponent() {
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        selectedFasilitasImage = rootView.findViewById(R.id.iv_selected_fasilitas);
        ViewUtil.loadImage(getContext(), selectedFasilitasImage, R.drawable.itb);
    }

    @Override
    protected void setupComponent() {
        setupSelectedFasilitasImage();
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
        map = googleMap;
        map.setBuildingsEnabled(true);
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);
        enableMyLocation();
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            PermissionUtil.requestPermission((AppCompatActivity) getActivity(), LOC_PERMISSION_REQUEST,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (map != null) {
            map.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(getContext(), "Location Button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(getContext(), "Location: " + location, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (permissionDenied) {
            showMissingRequirePermissionError();
            permissionDenied = false;
        }
    }

    private void showMissingRequirePermissionError() {
        PermissionUtil.PermissionDeniedDialog.newInstance(true).show(getActivity().getSupportFragmentManager(),"dialog");
    }


}
