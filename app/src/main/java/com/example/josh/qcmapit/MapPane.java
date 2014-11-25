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
 * Controls the actual map.
 */

public class MapPane {
    public GoogleMap mMap;
    public Hashtable <String, LatLng> loc;
    private Marker destinationMarker;
    private Marker startLocationMarker;

    /**
     *
     * @param map Actual google map
     */
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
        loc.put("Track and Soccar Fields", new LatLng(40.736330, -73.822174));
        loc.put("Kissena Hall", new LatLng(40.737373, -73.814277));
        loc.put("Outdoor Tennis Court", new LatLng(40.737556, -73.821684));
        loc.put("Softball Field", new LatLng(40.738174, -73.821292));
        loc.put("Queens Hall", new LatLng(40.737130, -73.824450));
        loc.put("LeFrak Concert Hall", new LatLng(40.737888, -73.817216));
        loc.put("Goldstein Theatre", new LatLng(40.737920, -73.815226));
        loc.put("Gertz Center", new LatLng(40.737359, -73.815875));
        loc.put("G Building", new LatLng(40.737071, -73.815776));
        loc.put("Main Entrance", new LatLng(40.737119, -73.814823));
        loc.put("Main Exit", new LatLng(40.735965, -73.814823));
        loc.put("I Building", new LatLng(40.737296, -73.818409));
        loc.put("Alumni Plaza", new LatLng(40.735086, -73.815403));
        loc.put("Continuing Education 1", new LatLng(40.734510, -73.816706));
        loc.put("Continuing Education 2", new LatLng(40.734266, -73.816886));
        loc.put("Gate 3", new LatLng(40.738549, -73.818423));
        loc.put("Gate 2", new LatLng(40.734240, -73.819759));
        loc.put("Gate 1", new LatLng(40.734049, -73.814947));
        loc.put("Cooperman Plaza", new LatLng(40.735660, -73.819665));
        loc.put("WWII Memorial Plaza", new LatLng(40.735272, -73.816620));
        loc.put("Bookstore", new LatLng(40.734232, -73.815966));
        loc.put("Public Safety", new LatLng(40.736152, -73.814842));
        loc.put("Welcome Center", new LatLng(40.735121, -73.815995));
        loc.put("One Stop Center", new LatLng(40.737342, -73.817455));
        loc.put("Quad", new LatLng(40.735573, -73.818608));
        loc.put("Lacrosse Field", new LatLng(40.737632, -73.822248));
        loc.put("Baseball Field", new LatLng(40.738944, -73.822462));
        loc.put("Gate 6", new LatLng(40.735493, -73.821666));
        loc.put("Gate 9", new LatLng(40.736355, -73.824927));
        loc.put("Parking Lot 15N", new LatLng(40.738974, -73.817124));
        loc.put("Parking Lot 15S", new LatLng(40.738702, -73.817717));
        loc.put("Parking Lot 3", new LatLng(40.738214, -73.818055));
        loc.put("Parking Lot 4", new LatLng(40.737539, -73.817583));
        loc.put("Parking Lot 11", new LatLng(40.736620, -73.816371));
        loc.put("Parking Lot 7", new LatLng(40.734489, -73.817352));
        loc.put("Parking Lot 2", new LatLng(40.734357, -73.819152));
        loc.put("Parking Lot 17", new LatLng(40.738027, -73.820070));
        loc.put("Parking Lot 16", new LatLng(40.737226, -73.814181));
        loc.put("Parking Lot 12", new LatLng(40.736163, -73.815432));
        loc.put("Parking Lot 10", new LatLng(40.737254, -73.815164));
        loc.put("Parking Lot 9", new LatLng(40.736631, -73.824337));
        loc.put("Parking Lot 5", new LatLng(40.737205, -73.820725));
        //2 parking 14
        loc.put("Parking Lot 14", new LatLng(40.736189, -73.821047));
        loc.put("Parking Lot 14", new LatLng(40.735778, -73.820747));
        //2 parking 6
        loc.put("Parking Lot 6", new LatLng(40.735888, -73.821262));
        loc.put("Parking Lot 6", new LatLng(40.734880, -73.821022));
        //2 parking 13
        loc.put("Parking Lot 13", new LatLng(40.737666, -73.818815));
        loc.put("Parking Lot 13", new LatLng(40.737115, -73.818756));
        //2 parking 1
        loc.put("Parking Lot 1", new LatLng(40.737115, -73.818756));
        loc.put("Parking Lot 1", new LatLng(40.734762, -73.816253));


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

    /**
     *
     * @param location loc hashtable key
     */
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

    /**
     *
     * @param location loc hashtable key
     */
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
