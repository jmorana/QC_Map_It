package com.example.josh.qcmapit;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Hashtable;

/**
 * Created by Josh on 11/17/14.
 */
public class MapPane {
    public GoogleMap mMap;
    public Hashtable <String, LatLng> loc;
    private Marker destinationMarker;
    private Marker startLocationMarker;
    public MapPane (GoogleMap map) {
        loc = new Hashtable<String, LatLng>();
        loc.put("Alumni Hall", new LatLng(40.736774, -73.817755));
        loc.put("Campbell Dome", new LatLng(40.736203, -73.818373));
        loc.put("Colden Auditorium", new LatLng(40.738195, -73.815905));
        loc.put("Colwin Hall", new LatLng(40.734977, -73.817559));
        loc.put("Dining Hall", new LatLng(40.737315, -73.817514));
        loc.put("Kiely Hall", new LatLng(40.736051, -73.816087));
        loc.put("Music Building", new LatLng(40.737994, -73.817074));
        loc.put("Science Building", new LatLng(40.734986, -73.820367));
        loc.put("Klapper Hall", new LatLng(40.736156, -73.817320));
        loc.put("Powdermaker Hall", new LatLng(40.736328, -73.819048));
        loc.put("Remsen Hall", new LatLng(40.734686, -73.819155));
        loc.put("Rosenthal Library", new LatLng(40.736487, -73.819944));
        loc.put("The Summit", new LatLng(40.737170, -73.819805));
        loc.put("Jefferson Hall", new LatLng(40.735154, -73.815975));
        loc.put("Razran Hall", new LatLng(40.734487, -73.818077));
        loc.put("Delany Hall", new LatLng(40.734911, -73.816890));
        loc.put("Student Union", new LatLng(40.734375, -73.815999));
        loc.put("FitzGerald Gym", new LatLng(40.738252, -73.819529));
        loc.put("King Hall", new LatLng(40.736984, -73.815281));
        loc.put("Rathaus Hall", new LatLng(40.737383, -73.816504));
        loc.put("Honors Hall", new LatLng(40.734610, -73.818521));
        loc.put("Frese Hall", new LatLng(40.735700, -73.817362));
        loc.put("Indoor Tennis Center", new LatLng(40.738277, -73.820548));
        loc.put("Track and Soccar Fields", new LatLng(40.736330, -73.822174));

        this.mMap = map;
        setUpMap();

    }
    private void setUpMap() {
        final LatLng QCCenter = new LatLng(40.736600, -73.819800);
        //TODO Figure out why using bounds causes a runtime error
        //final LatLng QCSWCorner = new LatLng(40.739809, -73.824877);
        //final LatLng QCNECorner = new LatLng(40.733786, -73.814867);

        final CameraPosition queensCollege = new CameraPosition(QCCenter, 16, 0, 95);
        //final LatLngBounds QCBounds = new LatLngBounds(QCNECorner, QCSWCorner);

        //CameraUpdate cam1 = CameraUpdateFactory.newLatLngBounds(QCBounds, 0);
        CameraUpdate cam2 = CameraUpdateFactory.newCameraPosition(queensCollege);

        mMap.moveCamera(cam2);

        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setMyLocationEnabled(true);
    }
    public void setDestinationMarker(String location) {
        removeDestinationMarker();
        if (loc.containsKey(location)){
            this.destinationMarker = mMap.addMarker(new MarkerOptions().position(loc.get(location)));
        }
    }
    public void removeDestinationMarker () {
        if (this.destinationMarker != null) {
            this.destinationMarker.remove();
            this.destinationMarker = null;
        }
    }
    public void setStartLocationMarker (String location) {
        removeStartLocationMarker();
        if (loc.containsKey(location)){
            this.startLocationMarker = mMap.addMarker(new MarkerOptions().position(loc.get(location)));
        }
    }
    public void removeStartLocationMarker () {
        if (this.startLocationMarker != null) {
            this.startLocationMarker.remove();
            this.startLocationMarker = null;
        }
    }
}
