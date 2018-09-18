package com.nagare.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nagare.DetailFasilitasActivity;
import com.nagare.R;
import com.nagare.base.BaseMainFragment;
import com.nagare.model.Fasilitas;
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
        final EditText desc = inflator.findViewById(R.id.et_deskripsi_fasilitas);

        alertDialogBuilder.setTitle(R.string.fasilitas)
                .setView(inflator)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Fasilitas fasilitas = new Fasilitas(
                                name.getText().toString(),
                                desc.getText().toString(),
                                "Dion",
                                latLng);
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

    private void addFasilitas(Fasilitas fasilitas) {
        DatabaseReference dbFasilitas = FirebaseDatabase.getInstance().getReference("fasilitas");
        String key = dbFasilitas.push().getKey();
        dbFasilitas.child(key).setValue(fasilitas);
        map.addMarker(new MarkerOptions().position(fasilitas.getPosition()).title(fasilitas.getTitle()));
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

}
