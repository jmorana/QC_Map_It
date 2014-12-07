package com.example.josh.qcmapit;

import android.util.FloatMath;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Josh on 11/17/14.
 * Controls the actual map.
 */

public class MapPane {
    public GoogleMap mMap;
    public Hashtable <String, LatLng []> locationCoordinates;
    public Hashtable <String, LatLng> nodeCoordinates;
    private ArrayList <Marker> destinationMarker = new ArrayList<Marker>();
    private ArrayList <Marker> startLocationMarker = new ArrayList<Marker>();
    /**
     *
     * @param map Actual google map
     */
    public MapPane (GoogleMap map) {
        this.mMap = map;
        setLocationHashtable();
        setNodeHashtable();
        setUpMap();
        System.err.println("ZXCV "+ distanceBetweenCoord(nodeCoordinates.get("librarysouthdoor"), nodeCoordinates.get("qcfront")));

    }

    /*
    Code taken from http://www.androidsnippets.com/distance-between-two-gps-coordinates-in-meter
    and then modified to suit this app.
     */
    //TODO Make this fully functional
    private static double distanceBetweenCoord(LatLng a, LatLng b) {
        float lat_a = (float) a.latitude;
        float lng_a = (float) a.longitude;
        float lat_b = (float) b.latitude;
        float lng_b = (float) b.longitude;

        float pk = (float) (180/3.14169);
        float a1 = lat_a / pk;
        float a2 = lng_a / pk;
        float b1 = lat_b / pk;
        float b2 = lng_b / pk;

        float t1 = FloatMath.cos(a1)*FloatMath.cos(a2)*FloatMath.cos(b1)*FloatMath.cos(b2);
        float t2 = FloatMath.cos(a1)*FloatMath.sin(a2)*FloatMath.cos(b1)*FloatMath.sin(b2);
        float t3 = FloatMath.sin(a1)*FloatMath.sin(b1);

        return Math.acos(t1 + t2 + t3);
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
    }
    public void removeDestinationMarker () {
        for (Marker marker : this.destinationMarker) {
            marker.remove();
        }
        this.destinationMarker = new ArrayList<Marker>();
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
    }
    public void removeStartLocationMarker () {
        for (Marker marker : this.startLocationMarker) {
            marker.remove();
        }
        this.startLocationMarker = new ArrayList<Marker>();
    }

    private void setNodeHashtable() {
        nodeCoordinates = new Hashtable<String, LatLng>();
        nodeCoordinates.put("gate2", new LatLng(40.734294, -73.819834));
        nodeCoordinates.put("SBSEDOOR", new LatLng(40.734696, -73.819894));
        nodeCoordinates.put("SBNDOOR", new LatLng(40.735243, -73.820060));
        nodeCoordinates.put("SBSE_N1", new LatLng(40.734675, -73.819726));
        nodeCoordinates.put("SB_N2", new LatLng(40.735396, -73.819568));
        nodeCoordinates.put("SB_N3", new LatLng(40.735446, -73.819955));
        nodeCoordinates.put("fountain", new LatLng(40.735696, -73.819937));
        nodeCoordinates.put("librarysouthdoor", new LatLng(40.736002, -73.819826));
        nodeCoordinates.put("fountainleft", new LatLng(40.735871, -73.819535));
        nodeCoordinates.put("powderdoor", new LatLng(40.735888, -73.819047));
        nodeCoordinates.put("kyfront", new LatLng(40.735828, -73.815693));
        nodeCoordinates.put("kyfrontright", new LatLng(40.735581, -73.815792));
        nodeCoordinates.put("kyne", new LatLng(40.736331, -73.815801));
        nodeCoordinates.put("kynw", new LatLng(40.736397, -73.816285));
        nodeCoordinates.put("kgfront", new LatLng(40.736824, -73.815294));
        nodeCoordinates.put("mufront", new LatLng(40.737616, -73.816873));
        nodeCoordinates.put("aefront", new LatLng(40.736820, -73.817735));
        nodeCoordinates.put("dhfront1", new LatLng(40.736967, -73.817604));
        nodeCoordinates.put("dhfront2", new LatLng(40.736924, -73.817136));
        nodeCoordinates.put("dhside", new LatLng(40.737364, -73.816963));
        nodeCoordinates.put("phfronteast", new LatLng(40.735842, -73.818066));
        nodeCoordinates.put("phback1", new LatLng(40.736051, -73.818635));
        nodeCoordinates.put("phback2", new LatLng(40.736464, -73.818530));
        nodeCoordinates.put("kpfront", new LatLng(40.736507, -73.817189));
        nodeCoordinates.put("rafront", new LatLng(40.737260, -73.816713));
        nodeCoordinates.put("gbfront", new LatLng(40.736979, -73.815826));
        nodeCoordinates.put("muside", new LatLng(40.738063, -73.816622));
        nodeCoordinates.put("cafront", new LatLng(40.738274, -73.815977));
        nodeCoordinates.put("fgfront", new LatLng(40.737735, -73.819385));
        nodeCoordinates.put("fgside", new LatLng(40.738157, -73.819005));
        nodeCoordinates.put("gate3", new LatLng(40.738500, -73.818456));
        nodeCoordinates.put("summitfront", new LatLng(40.737122, -73.819719));
        nodeCoordinates.put("summitside", new LatLng(40.737522, -73.819654));
        nodeCoordinates.put("roside", new LatLng(40.736110, -73.819616));
        nodeCoordinates.put("pwside", new LatLng(40.736073, -73.819387));
        nodeCoordinates.put("jhfront", new LatLng(40.735143, -73.815879));
        nodeCoordinates.put("jhback", new LatLng(40.735304, -73.816197));
        nodeCoordinates.put("kyback", new LatLng(40.735684, -73.816636));
        nodeCoordinates.put("kyside", new LatLng(40.735544, -73.816325));
        nodeCoordinates.put("qcfront", new LatLng(40.737102, -73.814856));
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
