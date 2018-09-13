package com.nagare.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nagare.DetailFasilitasActivity;
import com.nagare.MainActivity;
import com.nagare.R;
import com.nagare.base.BaseMainFragment;
import com.nagare.util.MapsUtil;
import com.nagare.util.PermissionUtil;
import com.nagare.util.ViewUtil;

public class MapsFragment extends BaseMainFragment  implements
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int LOC_PERMISSION_REQUEST = 1;
    private boolean permissionDenied = false;

    private ImageView selectedFasilitasImage;
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
        selectedFasilitasImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewUtil.startNewActivity(getContext(), DetailFasilitasActivity.class,
                        selectedFasilitasImage, R.string.tn_selected_fasilitas);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setBuildingsEnabled(true);
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);
        map.setOnMapLongClickListener(this);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            PermissionUtil.requestPermission((AppCompatActivity) getActivity(), LOC_PERMISSION_REQUEST, Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (map != null) {
            map.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        LatLng latLng = new LatLng(map.getMyLocation().getLatitude(), map.getMyLocation().getLongitude());
        CameraPosition position = new CameraPosition.Builder().target(latLng).zoom(14).bearing(0).tilt(90).build();
        MapsUtil.changeCamera(map, position,5000);
        return true;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
    }

    @Override
    public void onResume() {
        super.onResume();
        if (permissionDenied) {
            PermissionUtil.PermissionDeniedDialog.newInstance(true).show(getActivity().getSupportFragmentManager(),"dialog");
            permissionDenied = false;
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

    @Override
    public void onMapLongClick(final LatLng latLng) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        final View inflator = getActivity().getLayoutInflater().inflate(R.layout.dialog_fasilitas, null);
        final EditText name = inflator.findViewById(R.id.et_nama_fasilitas);
        EditText desc = getActivity().findViewById(R.id.et_deskripsi_fasilitas);
        alertDialogBuilder.setTitle(R.string.fasilitas)
                .setView(inflator)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        map.addMarker(new MarkerOptions().position(latLng).title(name.getText().toString()));
                    }
                })
                .setNegativeButton("Cancel", null);


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
