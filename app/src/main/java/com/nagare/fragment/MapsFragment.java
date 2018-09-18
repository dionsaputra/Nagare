package com.nagare.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nagare.DetailFasilitasActivity;
import com.nagare.R;
import com.nagare.base.BaseMainFragment;
import com.nagare.model.Fasilitas;
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

    private ImageView selectedFasilitasImage;
    private TextView fasilitasName, fasilitasOwner;

    private boolean isKeliling = true;

    private GoogleMap map;

    private Map<Marker,Fasilitas> markerMap = new HashMap<>();

    public MapsFragment() {
        super();
        layoutResId = R.layout.fragment_maps;
    }

    public boolean isKeliling() {
        return isKeliling;
    }

    public void setKeliling(boolean keliling) {
        isKeliling = keliling;
    }

    @Override
    protected void initComponent() {
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        selectedFasilitasImage = rootView.findViewById(R.id.iv_selected_fasilitas);
        fasilitasName = rootView.findViewById(R.id.tv_fasilitas_name);
        fasilitasOwner = rootView.findViewById(R.id.tv_fasilitas_owner);

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
        setMapListener();
        checkLocationPermission();
        loadFasilitas();
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
        marker.showInfoWindow();
        final Fasilitas fasilitas = markerMap.get(marker);
        fasilitasName.setText(fasilitas.getName());

        Query query = DataUtil.dbUser.child(fasilitas.getUserKey());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fasilitasOwner.setText(dataSnapshot.getValue(User.class).getName());
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
                        Fasilitas fasilitas = new Fasilitas(name.getText().toString(), desc.getText().toString(), latLng);
                        addFasilitas(fasilitas);
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

    private void loadFasilitas() {
        DataUtil.dbFasilitas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Fasilitas fasilitas = ds.getValue(Fasilitas.class);
                    LatLng position = new LatLng(fasilitas.getLatitude(),fasilitas.getLongitude());
                    Marker marker = map.addMarker(new MarkerOptions()
                            .position(position)
                            .title(fasilitas.getName()));

                    markerMap.put(marker, fasilitas);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void addFasilitas(Fasilitas fasilitas) {
        String key = DataUtil.dbFasilitas.push().getKey();
        fasilitas.setKey(key);
        fasilitas.setUserKey(DataUtil.USER_KEY);
        DataUtil.dbFasilitas.child(key).setValue(fasilitas);
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

    private void kelilingListener() {

    }
}
