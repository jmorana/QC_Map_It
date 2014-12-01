package com.example.josh.qcmapit;

import android.graphics.Color;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Josh on 11/17/14.
 * Controls the actual map.
 */

public class MapPane {
    public GoogleMap mMap;
    private Polyline path;
    public Hashtable <String, LatLng []> locationCoordinates;
    private ArrayList <Marker> destinationMarker = new ArrayList<Marker>();
    private ArrayList <Marker> startLocationMarker = new ArrayList<Marker>();
    /**
     *
     * @param map Actual google map
     */
    public MapPane (GoogleMap map) {
        System.err.println("ZXCV Test Message");
        this.mMap = map;
        setLocationHashtable();
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
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setMyLocationEnabled(true);
    }

    /**
     *
     * @param location locationCoordinates hashtable key
     */
    public void setDestinationMarker(String location) {
        removeDestinationMarker();
        if (locationCoordinates.containsKey(location)){
            LatLng locations [] = locationCoordinates.get(location);
            for (LatLng coord : locations) {
                this.destinationMarker.add(mMap.addMarker(new MarkerOptions().position(coord)));
            }
        }
        setPathIfAppropriate();
    }
    public void removeDestinationMarker () {
        for (Marker marker : this.destinationMarker) {
            marker.remove();
        }
        this.destinationMarker = new ArrayList<Marker>();
        removePath();
    }

    /**
     *
     * @param location locationCoordinates hashtable key
     */
    public void setStartLocationMarker (String location) {
        removeStartLocationMarker();
        if (locationCoordinates.containsKey(location)){
            LatLng locations [] = locationCoordinates.get(location);
            for (LatLng coord : locations) {
                this.startLocationMarker.add(mMap.addMarker(new MarkerOptions()
                        .position(coord)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                ));
            }
        }
        setPathIfAppropriate();
    }
    public void removeStartLocationMarker () {
        for (Marker marker : this.startLocationMarker) {
            marker.remove();
        }
        this.startLocationMarker = new ArrayList<Marker>();
        removePath();
    }
    public void setPathIfAppropriate () {
        System.err.println("ZXCV Entered function");
        if (destinationMarker.size() > 0 && startLocationMarker.size() > 0) {
            System.err.println("ZXCV Broke through");
            setPath();
        }
    }
    public void setPath () {
        LatLng loc1 = destinationMarker.get(0).getPosition();
        LatLng loc2 = startLocationMarker.get(0).getPosition();
        this.path = mMap.addPolyline(new PolylineOptions()
                .add(loc1, loc2)
                .width(5)
                .color(Color.BLUE));
    }
    public void removePath () {
        if (path != null) {
            this.path.remove();
        }
    }
    private void setLocationHashtable() {
        locationCoordinates = new Hashtable<String, LatLng []>();
        locationCoordinates.put("Alumni Hall", new LatLng[]{
                new LatLng(40.736774, -73.817755)
        });
        locationCoordinates.put("Campbell Dome", new LatLng[]{
                new LatLng(40.736203, -73.818373)
        });
        locationCoordinates.put("Colden Auditorium", new LatLng[]{
                new LatLng(40.738195, -73.815905)
        });
        locationCoordinates.put("Colwin Hall", new LatLng[]{
                new LatLng(40.734977, -73.817559)
        });
        locationCoordinates.put("Dining Hall", new LatLng[]{
                new LatLng(40.737315, -73.817514)
        });
        locationCoordinates.put("Kiely Hall", new LatLng[]{
                new LatLng(40.736051, -73.816087)
        });
        locationCoordinates.put("Music Building", new LatLng[]{
                new LatLng(40.737994, -73.817074)
        });
        locationCoordinates.put("Science Building", new LatLng[]{
                new LatLng(40.734986, -73.820367)
        });
        locationCoordinates.put("Klapper Hall", new LatLng[]{
                new LatLng(40.736156, -73.817320)
        });
        locationCoordinates.put("Powdermaker Hall", new LatLng[]{
                new LatLng(40.736328, -73.819048)
        });
        locationCoordinates.put("Remsen Hall", new LatLng[]{
                new LatLng(40.734686, -73.819155)
        });
        locationCoordinates.put("Rosenthal Library", new LatLng[]{
                new LatLng(40.736487, -73.819944)
        });
        locationCoordinates.put("The Summit", new LatLng[]{
                new LatLng(40.737170, -73.819805)
        });
        locationCoordinates.put("Jefferson Hall", new LatLng[]{
                new LatLng(40.735154, -73.815975)
        });
        locationCoordinates.put("Razran Hall", new LatLng[]{
                new LatLng(40.734487, -73.818077)
        });
        locationCoordinates.put("Delany Hall", new LatLng[]{
                new LatLng(40.734911, -73.816890)
        });
        locationCoordinates.put("Student Union", new LatLng[]{
                new LatLng(40.734375, -73.815999)
        });
        locationCoordinates.put("FitzGerald Gym", new LatLng[]{
                new LatLng(40.738252, -73.819529)
        });
        locationCoordinates.put("King Hall", new LatLng[]{
                new LatLng(40.736984, -73.815281)
        });
        locationCoordinates.put("Rathaus Hall", new LatLng[]{
                new LatLng(40.737383, -73.816504)
        });
        locationCoordinates.put("Honors Hall", new LatLng[]{
                new LatLng(40.734610, -73.818521)
        });
        locationCoordinates.put("Frese Hall", new LatLng[]{
                new LatLng(40.735700, -73.817362)
        });
        locationCoordinates.put("Indoor Tennis Center", new LatLng[]{
                new LatLng(40.738277, -73.820548)
        });
        locationCoordinates.put("Track and Soccar Fields", new LatLng[]{
                new LatLng(40.736330, -73.822174)
        });
        locationCoordinates.put("Track and Soccar Fields", new LatLng[]{
                new LatLng(40.736330, -73.822174)
        });
        locationCoordinates.put("Kissena Hall", new LatLng[]{
                new LatLng(40.737373, -73.814277)
        });
        locationCoordinates.put("Outdoor Tennis Court", new LatLng[]{
                new LatLng(40.737556, -73.821684)
        });
        locationCoordinates.put("Softball Field", new LatLng[]{
                new LatLng(40.738174, -73.821292)
        });
        locationCoordinates.put("Queens Hall", new LatLng[]{
                new LatLng(40.737130, -73.824450)
        });
        locationCoordinates.put("LeFrak Concert Hall", new LatLng[]{
                new LatLng(40.737888, -73.817216)
        });
        locationCoordinates.put("Goldstein Theatre", new LatLng[]{
                new LatLng(40.737920, -73.815226)
        });
        locationCoordinates.put("Gertz Center", new LatLng[]{
                new LatLng(40.737359, -73.815875)
        });
        locationCoordinates.put("G Building", new LatLng[]{
                new LatLng(40.737071, -73.815776)
        });
        locationCoordinates.put("Main Entrance", new LatLng[]{
                new LatLng(40.737119, -73.814823)
        });
        locationCoordinates.put("Main Exit", new LatLng[]{
                new LatLng(40.735965, -73.814823)
        });
        locationCoordinates.put("I Building", new LatLng[]{
                new LatLng(40.737296, -73.818409)
        });
        locationCoordinates.put("Alumni Plaza", new LatLng[]{
                new LatLng(40.735086, -73.815403)
        });
        locationCoordinates.put("Continuing Education 1", new LatLng[]{
                new LatLng(40.734510, -73.816706)
        });
        locationCoordinates.put("Continuing Education 2", new LatLng[]{
                new LatLng(40.734266, -73.816886)
        });
        locationCoordinates.put("Gate 3", new LatLng[]{
                new LatLng(40.738549, -73.818423)
        });
        locationCoordinates.put("Gate 2", new LatLng[]{
                new LatLng(40.734240, -73.819759)
        });
        locationCoordinates.put("Gate 1", new LatLng[]{
                new LatLng(40.734049, -73.814947)
        });
        locationCoordinates.put("Cooperman Plaza", new LatLng[]{
                new LatLng(40.735660, -73.819665)
        });
        locationCoordinates.put("WWII Memorial Plaza", new LatLng[]{
                new LatLng(40.735272, -73.816620)
        });
        locationCoordinates.put("Bookstore", new LatLng[]{
                new LatLng(40.734232, -73.815966)
        });
        locationCoordinates.put("Public Safety", new LatLng[]{
                new LatLng(40.736152, -73.814842)
        });
        locationCoordinates.put("Welcome Center", new LatLng[]{
                new LatLng(40.735121, -73.815995)
        });
        locationCoordinates.put("One Stop Center", new LatLng[]{
                new LatLng(40.737342, -73.817455)
        });
        locationCoordinates.put("Quad", new LatLng[]{
                new LatLng(40.735573, -73.818608)
        });
        locationCoordinates.put("Lacrosse Field", new LatLng[]{
                new LatLng(40.737632, -73.822248)
        });
        locationCoordinates.put("Baseball Field", new LatLng[]{
                new LatLng(40.738944, -73.822462)
        });
        locationCoordinates.put("Gate 6", new LatLng[]{
                new LatLng(40.735493, -73.821666)
        });
        locationCoordinates.put("Gate 9", new LatLng[]{
                new LatLng(40.736355, -73.824927)
        });
        locationCoordinates.put("Parking Lot 15N", new LatLng[]{
                new LatLng(40.738974, -73.817124)
        });
        locationCoordinates.put("Parking Lot 15S", new LatLng[]{
                new LatLng(40.738702, -73.817717)
        });
        locationCoordinates.put("Parking Lot 3", new LatLng[]{
                new LatLng(40.738214, -73.818055)
        });
        locationCoordinates.put("Parking Lot 4", new LatLng[]{
                new LatLng(40.737539, -73.817583)
        });
        locationCoordinates.put("Parking Lot 11", new LatLng[]{
                new LatLng(40.736620, -73.816371)
        });
        locationCoordinates.put("Parking Lot 7", new LatLng[]{
                new LatLng(40.734489, -73.817352)
        });
        locationCoordinates.put("Parking Lot 2", new LatLng[]{
                new LatLng(40.734357, -73.819152)
        });
        locationCoordinates.put("Parking Lot 17", new LatLng[]{
                new LatLng(40.738027, -73.820070)
        });
        locationCoordinates.put("Parking Lot 16", new LatLng[]{
                new LatLng(40.737226, -73.814181)
        });
        locationCoordinates.put("Parking Lot 12", new LatLng[]{
                new LatLng(40.736163, -73.815432)
        });
        locationCoordinates.put("Parking Lot 10", new LatLng[]{
                new LatLng(40.737254, -73.815164)
        });
        locationCoordinates.put("Parking Lot 9", new LatLng[]{
                new LatLng(40.736631, -73.824337)
        });
        locationCoordinates.put("Parking Lot 5", new LatLng[]{
                new LatLng(40.737205, -73.820725)
        });
        locationCoordinates.put("Parking Lot 14", new LatLng[]{
                new LatLng(40.736189, -73.821047),
                new LatLng(40.735778, -73.820747)
        });

        locationCoordinates.put("Parking Lot 6", new LatLng[]{
                new LatLng(40.735888, -73.821262),
                new LatLng(40.734880, -73.821022)
        });
        locationCoordinates.put("Parking Lot 13", new LatLng[]{
                new LatLng(40.737666, -73.818815),
                new LatLng(40.737115, -73.818756)
        });
        locationCoordinates.put("Parking Lot 1", new LatLng[]{
                new LatLng(40.737115, -73.818756),
                new LatLng(40.734762, -73.816253)
        });
    }
}
