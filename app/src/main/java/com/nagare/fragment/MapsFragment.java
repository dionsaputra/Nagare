package com.nagare.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
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
import com.nagare.DetailFasilitasActivity;
import com.nagare.R;
import com.nagare.base.BaseMainFragment;
import com.nagare.model.Lokasi;
import com.nagare.model.User;
import com.nagare.util.DataUtil;
import com.nagare.util.MapsUtil;
import com.nagare.util.PermissionUtil;
import com.nagare.util.ViewUtil;

import java.util.HashMap;
import java.util.Map;

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
    private boolean isKeliling = true;


    private ImageView selectedLokasiImage;
    private TextView lokasiName, lokasiOwner;
    public static boolean mapReady = false;
    private GoogleMap map;
    private GoogleApiClient googleApiClient;
    private LocationManager locationManager;

    private Map<Marker,Lokasi> markerMap = new HashMap<>();
    private int REQUEST_LOCATION = 199;

    public MapsFragment() {
        super();
        layoutResId = R.layout.fragment_maps;
    }

    @Override
    protected void initComponent() {
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        selectedLokasiImage = rootView.findViewById(R.id.iv_selected_lokasi);
        lokasiName = rootView.findViewById(R.id.tv_lokasi_name);
        lokasiOwner = rootView.findViewById(R.id.tv_lokasi_owner);

        ViewUtil.loadImage(getContext(), selectedLokasiImage, R.drawable.itb);
    }

    @Override
    protected void setupComponent() {
        selectedLokasiImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewUtil.startNewActivity(getContext(), DetailFasilitasActivity.class, selectedLokasiImage, R.string.tn_selected_lokasi);
            }
        });
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
        LocationManager lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        if (gps_enabled) {
            if (map.getMyLocation() != null) {
                LatLng latLng = new LatLng(map.getMyLocation().getLatitude(), map.getMyLocation().getLongitude());
                CameraPosition position = new CameraPosition.Builder().target(latLng).zoom(14).bearing(0).tilt(0).build();
                MapsUtil.changeCamera(map, position, 1000);
                return true;
            }
        }
        return false;
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
        ImageView selectedImage = rootView.findViewById(R.id.iv_selected_lokasi);
        LinearLayout selectedLayout = rootView.findViewById(R.id.ll_selected_lokasi);
        TextView emptyView = rootView.findViewById(R.id.empty_selected_lokasi);
        selectedImage.setVisibility(View.VISIBLE);
        selectedLayout.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);

        marker.showInfoWindow();
        final Lokasi lokasi = markerMap.get(marker);
        lokasiName.setText(lokasi.getName());

        Query query = DataUtil.dbUser.child(lokasi.getUserKey());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lokasiOwner.setText(dataSnapshot.getValue(User.class).getName());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        return true;
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
        final EditText desc = inflator.findViewById(R.id.et_deskripsi_fasilitas);

        alertDialogBuilder.setView(inflator)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Lokasi lokasi = new Lokasi(name.getText().toString(), desc.getText().toString(), latLng);
                        addLokasi(lokasi);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showPictureDialog();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void setMapListener() {
        map.setBuildingsEnabled(true);
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);
        map.setOnMapLongClickListener(this);
        map.setOnMarkerClickListener(this);
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            PermissionUtil.requestPermission((AppCompatActivity) getActivity(), LOC_PERMISSION_REQUEST,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (map != null) {
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

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getContext());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallery();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private int GALLERY = 1, CAMERA = 2;

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    public void loadLokasi() {
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
    }

    public boolean isKeliling() {
        return isKeliling;
    }

    public void setKeliling(boolean keliling) {
        this.isKeliling = keliling;
    }


}
