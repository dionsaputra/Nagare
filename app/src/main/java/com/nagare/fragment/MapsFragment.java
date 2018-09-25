package com.nagare.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.util.DbUtils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nagare.R;
import com.nagare.base.BaseMainFragment;
import com.nagare.model.Kelurahan;
import com.nagare.model.Lokasi;
import com.nagare.model.User;
import com.nagare.util.DataUtil;
import com.nagare.util.MapsUtil;
import com.nagare.util.PermissionUtil;
import com.nagare.util.ViewUtil;

import java.util.HashMap;
import java.util.Map;

public class MapsFragment extends BaseMainFragment implements GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnMarkerDragListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int LOC_PERMISSION_REQUEST = 1;
    private boolean permissionDenied = false;
    private boolean isKeliling = true;

    public static boolean mapReady = false;
    private GoogleMap map;

    private Map<Marker, Lokasi> markerMap = new HashMap<>();

    public MapsFragment() {
        super();
        layoutResId = R.layout.fragment_maps;
    }

    @Override
    protected void initComponent() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void setupComponent() {
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        setMapListener();
        checkLocationPermission();
        mapReady = true;
        loadLokasi();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        if (!isMapReady()) {
            Toast.makeText(getContext(), "Maps isn't ready. Please check your location service", Toast.LENGTH_SHORT).show();
            return false;
        }

        LocationManager lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        if (gps_enabled) {
            if (map.getMyLocation() != null) {
                LatLng latLng = new LatLng(map.getMyLocation().getLatitude(), map.getMyLocation().getLongitude());
                CameraPosition position = new CameraPosition.Builder().target(latLng).zoom(17).bearing(0).tilt(90).build();
                MapsUtil.changeCamera(map, position, 1000);
                return true;
            } else {
//                Toast.makeText(getContext(), "Failed Location Null", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getContext(), "Failed GPS_NOT_ENABLED", Toast.LENGTH_SHORT).show();

        }
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        if (!isMapReady()) {
            Toast.makeText(getContext(), "Maps isn't ready. Please check your location service", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isMapReady()) {
            Toast.makeText(getContext(), "Maps isn't ready. Please check your location service", Toast.LENGTH_SHORT).show();
            return;
        }
        if (permissionDenied) {
            PermissionUtil.PermissionDeniedDialog.newInstance(true).show(getActivity().getSupportFragmentManager(), "dialog");
            permissionDenied = false;
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (!isMapReady()) {
            Toast.makeText(getContext(), "Maps isn't ready. Please check your location service", Toast.LENGTH_SHORT).show();
            return false;
        }
        marker.showInfoWindow();
        return true;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        if (!isMapReady()) {
            Toast.makeText(getContext(), "Maps isn't ready. Please check your location service", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        if (!isMapReady()) {
            Toast.makeText(getContext(), "Maps isn't ready. Please check your location service", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        if (!isMapReady()) {
            Toast.makeText(getContext(), "Maps isn't ready. Please check your location service", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void onMapLongClick(final LatLng latLng) {
        if (!isMapReady()) {
            Toast.makeText(getContext(), "Maps isn't ready. Please check your location service", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        final View inflator = getActivity().getLayoutInflater().inflate(R.layout.dialog_fasilitas, null);
        TextView titleDialog = inflator.findViewById(R.id.dialog_fasilitas_title);
        if (!isKeliling) {
            titleDialog.setText("Lapor");
        }
        ImageView imageDialog = inflator.findViewById(R.id.dialog_fasilitas_image);
        if (!isKeliling) {
            imageDialog.setImageDrawable(getResources().getDrawable(R.drawable.lapor));
        }

        final EditText name = inflator.findViewById(R.id.et_nama_fasilitas);
        final EditText desc = inflator.findViewById(R.id.et_deskripsi_fasilitas);

        alertDialogBuilder.setView(inflator).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Lokasi lokasi = new Lokasi(name.getText().toString(), desc.getText().toString(), latLng);
                addLokasi(lokasi);
            }
        }).setNegativeButton("Cancel", null);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public boolean isMapReady() {
        return (mapReady && map != null);
    }

    private void setMapListener() {
        map.setBuildingsEnabled(true);
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);
        map.setOnMapLongClickListener(this);
        map.setOnMarkerClickListener(this);
        map.setOnInfoWindowClickListener(this);
    }

    public void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            PermissionUtil.requestPermission((AppCompatActivity) getActivity(), LOC_PERMISSION_REQUEST, Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else {
            map.setMyLocationEnabled(true);
        }
    }

    private void addLokasi(Lokasi lokasi) {
        String key = isKeliling ? DataUtil.dbFasilitas.push().getKey() : DataUtil.dbLapor.push().getKey();
        lokasi.setKey(key);
        lokasi.setUserKey(DataUtil.USER_KEY);

        if (isKeliling) {
            DataUtil.dbFasilitas.child(key).setValue(lokasi);
        } else {
            DataUtil.dbLapor.child(key).setValue(lokasi);
        }
    }

    public void loadLokasi() {
        if (!isMapReady()) {
            Toast.makeText(getContext(), "Maps isn't ready. Please check your location service", Toast.LENGTH_SHORT).show();
            return;
        }

        map.clear();
        markerMap.clear();

        ValueEventListener lokasiListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Lokasi lokasi = ds.getValue(Lokasi.class);
                    LatLng position = new LatLng(lokasi.getLatitude(), lokasi.getLongitude());
                    Marker marker = map.addMarker(new MarkerOptions()
                            .position(position)
                            .title(lokasi.getName()));

                    markerMap.put(marker, lokasi);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        if (isKeliling) {
            DataUtil.dbFasilitas.addValueEventListener(lokasiListener);
        } else {
            DataUtil.dbLapor.addValueEventListener(lokasiListener);
        }
        onMyLocationButtonClick();
    }

    public boolean isKeliling() {
        return isKeliling;
    }

    public void setKeliling(boolean keliling) {
        this.isKeliling = keliling;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if (!isMapReady()) {
            Toast.makeText(getContext(), "Maps isn't ready. Please check your location service", Toast.LENGTH_SHORT).show();
            return;
        }

        LayoutInflater inflater = ((AppCompatActivity)getContext()).getLayoutInflater();
        View view = inflater.inflate(R.layout.detail_lokasi,null);
        ImageView imageView = view.findViewById(R.id.iv_selected_lokasi);
        TextView lokasiName = view.findViewById(R.id.tv_lokasi_name);
        final TextView ownerName = view.findViewById(R.id.tv_lokasi_owner);
        TextView description = view.findViewById(R.id.tv_lokasi_description);

        Lokasi lokasi = markerMap.get(marker);
        lokasiName.setText(lokasi.getName());

        Query query = DataUtil.dbUser.child(markerMap.get(marker).getUserKey());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ownerName.setText(dataSnapshot.getValue(User.class).getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        description.setText(lokasi.getDescription());

        ViewUtil.loadImage(getContext(),imageView,ViewUtil.getRandomPlaceHolder());

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.MyAlertDialogTheme);
        builder.setView(view)
                .setPositiveButton("OK",null);

        builder.create().show();
    }
}
