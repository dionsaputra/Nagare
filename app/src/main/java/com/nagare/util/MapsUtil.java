package com.nagare.util;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.stats.internal.G;
import com.nagare.R;

public class MapsUtil{
    public static void changeCamera(GoogleMap map, CameraPosition position, int duration) {
        map.animateCamera(CameraUpdateFactory.newCameraPosition(position), Math.max(duration, 1),
                new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() { }
            @Override
            public void onCancel() { }
        });
    }
}
