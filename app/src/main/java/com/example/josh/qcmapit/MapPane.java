package com.example.josh.qcmapit;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

/**
 * Created by Josh on 11/17/14.
 */
public class MapPane {
    public GoogleMap mMap;

    public MapPane (GoogleMap map) {
        this.mMap = map;
        setUpMap();

    }
    private void setUpMap() {
        final LatLng QCCenter = new LatLng(40.736600, -73.819800);
        final LatLng QCSWCorner = new LatLng(40.739809, -73.824877);
        final LatLng QCNECorner = new LatLng(40.733786, -73.814867);

        final CameraPosition queensCollege = new CameraPosition(QCCenter, 16, 0, 95);
        final LatLngBounds QCBounds = new LatLngBounds(QCNECorner, QCSWCorner);

        CameraUpdate cam1 = CameraUpdateFactory.newLatLngBounds(QCBounds, 0);
        CameraUpdate cam2 = CameraUpdateFactory.newCameraPosition(queensCollege);

        mMap.moveCamera(cam2);

        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setMyLocationEnabled(true);
    }
}
