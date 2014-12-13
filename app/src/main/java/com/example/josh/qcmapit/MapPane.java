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
    public Hashtable <String, String[]> nodeConnections;
    public Hashtable <String, LatLng> nodeCoordinates;
    private ArrayList <Marker> destinationMarker = new ArrayList<Marker>();
    private ArrayList <Marker> startLocationMarker = new ArrayList<Marker>();

    /**
     *
     * @param map Actual google map
     */
    public MapPane (GoogleMap map, MainActivity mainActivity) {
        this.mMap = map;
        setLocationHashtable();
        setNodeHashtable();
        setUpMap();

    }

    private static double computeDistanceBetween(LatLng latLngA, LatLng latLngB) {
        float lat_a = (float) latLngA.latitude;
        float lng_a = (float) latLngA.longitude;
        float lat_b = (float) latLngB.latitude;
        float lng_b = (float) latLngB.longitude;

        double latARad = Math.toRadians(lat_a);
        double latBRad = Math.toRadians(lat_b);

        double deltaOne = Math.toRadians(lat_b - lat_a);
        double deltaTwo = Math.toRadians(lng_b - lng_a);

        double a = Math.sin(deltaOne/2) * Math.sin(deltaOne/2) +
                Math.cos(latARad) * Math.cos(latBRad) *
                Math.sin(deltaTwo) * Math.sin(deltaTwo);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return c;
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
        if (destinationMarker.size() > 0 && startLocationMarker.size() > 0) {
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

    private void setNodeHashtable() {
        nodeCoordinates = new Hashtable<String, LatLng>();
        nodeCoordinates.put("g2", new LatLng(40.734294, -73.819834));
        nodeCoordinates.put("sb1", new LatLng(40.734696, -73.819894));
        nodeCoordinates.put("sb2", new LatLng(40.735243, -73.820060));
        nodeCoordinates.put("sb0", new LatLng(40.734675, -73.819726));
        nodeCoordinates.put("q2", new LatLng(40.735396, -73.819568));
        nodeCoordinates.put("q0", new LatLng(40.735446, -73.819955));
        nodeCoordinates.put("q1", new LatLng(40.735696, -73.819937));
        nodeCoordinates.put("ro1", new LatLng(40.736002, -73.819826));
        nodeCoordinates.put("pw0", new LatLng(40.735888, -73.819047));
        nodeCoordinates.put("ky1", new LatLng(40.735828, -73.815693));
        nodeCoordinates.put("ky2", new LatLng(40.735581, -73.815792));
        nodeCoordinates.put("ky3", new LatLng(40.736331, -73.815801));
        nodeCoordinates.put("ky4", new LatLng(40.736397, -73.816285));
        nodeCoordinates.put("kg1", new LatLng(40.736824, -73.815294));
        nodeCoordinates.put("mu1", new LatLng(40.737616, -73.816873));
        nodeCoordinates.put("ae1", new LatLng(40.736820, -73.817735));
        nodeCoordinates.put("dh1", new LatLng(40.736967, -73.817604));
        nodeCoordinates.put("dh2", new LatLng(40.736924, -73.817136));
        nodeCoordinates.put("dh3", new LatLng(40.737364, -73.816963));
        nodeCoordinates.put("pw2", new LatLng(40.735842, -73.818066));
        nodeCoordinates.put("pw3", new LatLng(40.736051, -73.818635));
        nodeCoordinates.put("pw4", new LatLng(40.736464, -73.818530));
        nodeCoordinates.put("kp1", new LatLng(40.736507, -73.817189));
        nodeCoordinates.put("ra1", new LatLng(40.737260, -73.816713));
        nodeCoordinates.put("gb1", new LatLng(40.736979, -73.815826));
        nodeCoordinates.put("mu2", new LatLng(40.738063, -73.816622));
        nodeCoordinates.put("ca1", new LatLng(40.738274, -73.815977));
        nodeCoordinates.put("fg1", new LatLng(40.737735, -73.819385));
        nodeCoordinates.put("fg2", new LatLng(40.738157, -73.819005));
        nodeCoordinates.put("g3", new LatLng(40.738500, -73.818456));
        nodeCoordinates.put("su1", new LatLng(40.737122, -73.819719));
        nodeCoordinates.put("su2", new LatLng(40.737522, -73.819654));
        nodeCoordinates.put("ro2", new LatLng(40.736110, -73.819616));
        nodeCoordinates.put("pw1", new LatLng(40.736073, -73.819387));
        nodeCoordinates.put("jh1", new LatLng(40.735143, -73.815879));
        nodeCoordinates.put("jh2", new LatLng(40.735304, -73.816197));
        nodeCoordinates.put("ky5", new LatLng(40.735684, -73.816636));
        nodeCoordinates.put("ky6", new LatLng(40.735544, -73.816325));
        nodeCoordinates.put("kb5", new LatLng(40.737102, -73.814856));

        nodeCoordinates.put("q5", new LatLng(40.735347, -73.819132));
        nodeCoordinates.put("q7", new LatLng(40.735291, -73.818665));
        nodeCoordinates.put("q9", new LatLng(40.735177, -73.817839));
        nodeCoordinates.put("re1", new LatLng(40.735153, -73.818797));
        nodeCoordinates.put("q4", new LatLng(40.735629, -73.819335));
        nodeCoordinates.put("q6", new LatLng(40.735826, -73.819086));
        nodeCoordinates.put("q8", new LatLng(40.735686, -73.818099));
        nodeCoordinates.put("q10", new LatLng(40.735645, -73.817759));
        nodeCoordinates.put("q3", new LatLng(40.735868, -73.819559));
        nodeCoordinates.put("s1", new LatLng(40.738218, -73.818721));
        nodeCoordinates.put("s2", new LatLng(40.738056, -73.818821));
        nodeCoordinates.put("s3", new LatLng(40.737662, -73.819018));
        nodeCoordinates.put("s4", new LatLng(40.737334, -73.819168));
        nodeCoordinates.put("s5", new LatLng(40.737057, -73.819262));
        nodeCoordinates.put("s6", new LatLng(40.736781, -73.819282));
        nodeCoordinates.put("s7", new LatLng(40.736482, -73.819334));
        nodeCoordinates.put("s8", new LatLng(40.736112, -73.819442));
        nodeCoordinates.put("pw5", new LatLng(40.736460, -73.819097));
        nodeCoordinates.put("re2", new LatLng(40.734967, -73.819144));
        nodeCoordinates.put("re3", new LatLng(40.734855, -73.819160));
        nodeCoordinates.put("re4", new LatLng(40.734919, -73.819677));
        nodeCoordinates.put("q11", new LatLng(40.735088, -73.817197));
        nodeCoordinates.put("q12", new LatLng(40.735559, -73.817085));
        nodeCoordinates.put("q13", new LatLng(40.734978, -73.816453));
        nodeCoordinates.put("q14", new LatLng(40.735169, -73.816679));
        nodeCoordinates.put("q15", new LatLng(40.735513, -73.816719));
        nodeCoordinates.put("q16", new LatLng(40.735507, -73.816683));
        nodeCoordinates.put("q17", new LatLng(40.735475, -73.816417));
        nodeCoordinates.put("q18", new LatLng(40.735723, -73.816913));
        nodeCoordinates.put("r1", new LatLng(40.738044, -73.816378));
        nodeCoordinates.put("r2", new LatLng(40.737708, -73.816577));
        nodeCoordinates.put("r0", new LatLng(40.736904, -73.816183));
        nodeCoordinates.put("r3", new LatLng(40.737563, -73.816888));
        nodeCoordinates.put("r4", new LatLng(40.737578, -73.817390));
        nodeCoordinates.put("r5", new LatLng(40.737760, -73.817803));
        nodeCoordinates.put("r6", new LatLng(40.737939, -73.818219));
        nodeCoordinates.put("r7", new LatLng(40.738162, -73.818483));
        nodeCoordinates.put("r8", new LatLng(40.737378, -73.816855));
        nodeCoordinates.put("r9", new LatLng(40.737173, -73.816836));
        nodeCoordinates.put("c0", new LatLng(40.736724, -73.818190));
        nodeCoordinates.put("c1", new LatLng(40.736453, -73.816436));
        nodeCoordinates.put("c2", new LatLng(40.736891, -73.816670));
        nodeCoordinates.put("c3", new LatLng(40.736490, -73.816724));
        nodeCoordinates.put("c4", new LatLng(40.736588, -73.817729));
        nodeCoordinates.put("c5", new LatLng(40.736926, -73.816802));
        nodeCoordinates.put("c6", new LatLng(40.736556, -73.817187));
        nodeCoordinates.put("c7", new LatLng(40.736621, -73.817613));
        nodeCoordinates.put("c8", new LatLng(40.736878, -73.817607));
        nodeCoordinates.put("c9", new LatLng(40.736884, -73.818195));
        nodeCoordinates.put("r10", new LatLng(40.737218, -73.816691));
        nodeCoordinates.put("rr1", new LatLng(40.736667, -73.818417));
        nodeCoordinates.put("rr2", new LatLng(40.736763, -73.818400));
        nodeCoordinates.put("rr3", new LatLng(40.736552, -73.817894));
        nodeCoordinates.put("rr4", new LatLng(40.736296, -73.817772));
        nodeCoordinates.put("rr5", new LatLng(40.736313, -73.817704));
        nodeCoordinates.put("rr6", new LatLng(40.736067, -73.817758));
        nodeCoordinates.put("rr7", new LatLng(40.735859, -73.817730));
        nodeCoordinates.put("f1", new LatLng(40.736104, -73.815296));
        nodeCoordinates.put("f2", new LatLng(40.735977, -73.815086));
        nodeCoordinates.put("f3", new LatLng(40.736186, -73.815081));
        nodeCoordinates.put("f4", new LatLng(40.736365, -73.815459));
        nodeCoordinates.put("f5", new LatLng(40.736418, -73.815792));
        nodeCoordinates.put("f6", new LatLng(40.736839, -73.815699));
        nodeCoordinates.put("f7", new LatLng(40.736655, -73.815108));
        nodeCoordinates.put("kb6", new LatLng(40.738130, -73.815071));
        nodeCoordinates.put("gt1", new LatLng(40.738341, -73.815453));
        nodeCoordinates.put("gt2", new LatLng(40.738191, -73.815219));
        nodeCoordinates.put("gt3", new LatLng(40.737945, -73.815142));
        //joe


        nodeCoordinates.put("g1", new LatLng(40.734052, -73.814914));
        nodeCoordinates.put("kb1", new LatLng(40.734469, -73.814887));
        nodeCoordinates.put("kb2", new LatLng(40.735536, -73.814807));
        nodeCoordinates.put("kb3", new LatLng(40.735919, -73.814818));
        nodeCoordinates.put("kb4", new LatLng(40.735993, -73.814810));

        nodeCoordinates.put("ap1", new LatLng(40.735105, -73.815542));
        nodeCoordinates.put("ap2", new LatLng(40.735202, -73.815311));
        nodeCoordinates.put("ap3", new LatLng(40.735180, -73.815268));
        nodeCoordinates.put("ap4", new LatLng(40.734971, -73.815316));
        nodeCoordinates.put("ap5", new LatLng(40.734961, -73.815359));
        nodeCoordinates.put("ap6", new LatLng(40.735115, -73.815617));
        nodeCoordinates.put("fg3", new LatLng(40.737726, -73.819622));
        nodeCoordinates.put("fg4", new LatLng(40.737814, -73.820164));
        nodeCoordinates.put("itc1", new LatLng(40.738216, -73.820191));
        nodeCoordinates.put("itc2", new LatLng(40.738201, -73.820116));
        nodeCoordinates.put("itc3", new LatLng(40.737833, -73.820427));
        nodeCoordinates.put("tc1", new LatLng(40.737881, -73.821157));
        nodeCoordinates.put("tc2", new LatLng(40.737870, -73.821377));
        nodeCoordinates.put("tc3", new LatLng(40.737546, -73.821398));
        nodeCoordinates.put("s9", new LatLng(40.737163, -73.820577));
        nodeCoordinates.put("s10", new LatLng(40.736983, -73.820607));
        nodeCoordinates.put("s11", new LatLng(40.736106, -73.820827));
        nodeCoordinates.put("s12", new LatLng(40.735961, -73.820848));
        nodeCoordinates.put("ro3", new LatLng(40.736027, -73.820167));
        nodeCoordinates.put("s13", new LatLng(40.734565, -73.821157));
        nodeCoordinates.put("s14", new LatLng(40.735418, -73.820948));
        nodeCoordinates.put("x1", new LatLng(40.734443, -73.821203));
        nodeCoordinates.put("x2", new LatLng(40.734540, -73.821756));
        nodeCoordinates.put("x3", new LatLng(40.735561, -73.821678));
        nodeCoordinates.put("x4", new LatLng(40.735446, -73.822896));
        nodeCoordinates.put("x5", new LatLng(40.736380, -73.822767));
        nodeCoordinates.put("x6", new LatLng(40.736507, -73.824052));
        nodeCoordinates.put("x7", new LatLng(40.736577, -73.824551));
        nodeCoordinates.put("x8", new LatLng(40.736885, -73.824527));
        nodeCoordinates.put("s15", new LatLng(40.734451, -73.819731));
        nodeCoordinates.put("rz1", new LatLng(40.734794, -73.818036));
        nodeCoordinates.put("rz2", new LatLng(40.734788, -73.817923));
        nodeCoordinates.put("hh1", new LatLng(40.734823, -73.818518));
        nodeCoordinates.put("hh2", new LatLng(40.734809, -73.818325));
        nodeCoordinates.put("hh3", new LatLng(40.735079, -73.818255));
        nodeCoordinates.put("hh4", new LatLng(40.735073, -73.818121));
        nodeCoordinates.put("ch1", new LatLng(40.735108, -73.817526));
        nodeCoordinates.put("dh1", new LatLng(40.735025, -73.816855));
        nodeCoordinates.put("cc1", new LatLng(40.734604, -73.816630));
        nodeCoordinates.put("cc2", new LatLng(40.734647, -73.816973));
        nodeCoordinates.put("cc3", new LatLng(40.734161, -73.817086));
        nodeCoordinates.put("cc4", new LatLng(40.734122, -73.816710));
        nodeCoordinates.put("d1", new LatLng(40.735451, -73.816340));
        nodeCoordinates.put("d2", new LatLng(40.735432, -73.816182));
        nodeCoordinates.put("d3", new LatLng(40.735376, -73.815774));
        nodeCoordinates.put("d4", new LatLng(40.735147, -73.815817));
        nodeCoordinates.put("y1", new LatLng(40.734352, -73.815433));
        nodeCoordinates.put("y2", new LatLng(40.734894, -73.815817));



        nodeConnections = new Hashtable<String, String[]>();


        nodeConnections.put("g1", new String[]{
                "kb1",
                "y1"
        });
        nodeConnections.put("kb1", new String[]{
                "g1",
                "ap5",
                "ap4",
                "kb2"
        });
        nodeConnections.put("kb2", new String[]{
                "kb1",
                "kb3",
                "ap2",
                "ap3"
        });
        nodeConnections.put("kb3", new String[]{
                "kb2",
                "kb4",
                "f2"
        });
        nodeConnections.put("kb4", new String[]{
                "kb3",
                "kb5",
                "f3"
        });
        nodeConnections.put("ap1", new String[]{
                "ap6",
                "ap5",
                "ap2"
        });
        nodeConnections.put("ap2", new String[]{
                "ap3",
                "ap1"
        });
        nodeConnections.put("ap3", new String[]{
                "ap4",
                "ap2"
        });
        nodeConnections.put("ap4", new String[]{
                "ap5",
                "ap3"
        });
        nodeConnections.put("ap5", new String[]{
                "ap4",
                "ap1"
        });
        nodeConnections.put("ap6", new String[]{
                "d4",
                "y2",
                "ap1"
        });
        nodeConnections.put("fg3", new String[]{
                "fg1",
                "su2",
                "fg4"
        });
        nodeConnections.put("fg4", new String[]{
                "itc3",
                "itc2",
                "fg3"

        });
        nodeConnections.put("itc1", new String[]{
                "itc2"

        });
        nodeConnections.put("itc2", new String[]{
                "itc1",
                "fg4"
        });
        nodeConnections.put("itc3", new String[]{
                "fg4",
                "tc1",
                "s9"
        });
        nodeConnections.put("tc1", new String[]{
                "tc2",
                "itc3"
        });
        nodeConnections.put("tc2", new String[]{
                "tc3",
                "tc1"
        });
        nodeConnections.put("tc3", new String[]{
                "tc2"

        });
        nodeConnections.put("s9", new String[]{
                "s10",
                "itc3"
        });
        nodeConnections.put("s10", new String[]{
                "s6",
                "s9",
                "s11"
        });
        nodeConnections.put("s11", new String[]{
                "s12",
                "s10"
        });
        nodeConnections.put("s12", new String[]{
                "s11",
                "s14"
        });
        nodeConnections.put("s13", new String[]{
                "x1",
                "s14",
                "s15"
        });
        nodeConnections.put("ro3", new String[]{
                "s11",
                "ro1"
        });
        nodeConnections.put("s14", new String[]{
                "s12",
                "s13",
                "sb2"
        });
        nodeConnections.put("x1", new String[]{
                "s13",
                "x2"
        });
        nodeConnections.put("x2", new String[]{
                "x1",
                "x3"
        });
        nodeConnections.put("x3", new String[]{
                "x2",
                "x4"
        });
        nodeConnections.put("x4", new String[]{
                "x4",
                "x3"
        });
        nodeConnections.put("x5", new String[]{
                "x6",
                "x4"
        });
        nodeConnections.put("x6", new String[]{
                "x5",
                "x7"
        });
        nodeConnections.put("x7", new String[]{
                "x6",
                "x8"
        });
        nodeConnections.put("x8", new String[]{
                "x7"
        });
        nodeConnections.put("s15", new String[]{
                "s13",
                "g2",
                "sb0",
                "cc4"
        });
        nodeConnections.put("rz1", new String[]{
                "rz2"

        });
        nodeConnections.put("rz2", new String[]{
                "q9",
                "rz1"
        });
        nodeConnections.put("hh1", new String[]{
                "hh2"
        });
        nodeConnections.put("hh2", new String[]{
                "hh1",
                "hh3"
        });
        nodeConnections.put("hh3", new String[]{
                "hh4",
                "re1"
        });
        nodeConnections.put("hh4", new String[]{
                "hh3",
                "q9"
        });
        nodeConnections.put("ch1", new String[]{
                "q9",
                "q11"
        });
        nodeConnections.put("dh1", new String[]{
                "q11",
                "q13"
        });
        nodeConnections.put("cc1", new String[]{
                "q13",
                "cc2",
                "cc4"
        });
        nodeConnections.put("cc2", new String[]{
                "cc1",
                "cc3"
        });
        nodeConnections.put("cc3", new String[]{
                "cc2",
                "cc4",
                "s15"
        });
        nodeConnections.put("cc4", new String[]{
                "cc1",
                "cc3",
                "s15"
        });
        nodeConnections.put("d1", new String[]{
                "ky6",
                "d2",
                "q17"
        });
        nodeConnections.put("d2", new String[]{
                "q1",
                "d3",
                "jh2"
        });
        nodeConnections.put("d3", new String[]{
                "ky2",
                "d4",
                "d2",
                "f2"
        });
        nodeConnections.put("d4", new String[]{
                "ap6",
                "y2",
                "d3",
                "jh1"
        });
        nodeConnections.put("y1", new String[]{
                "g1",
                "y2"
        });
        nodeConnections.put("y2", new String[]{
                "y1",
                "ap6",
                "d4",
                "q13"
        });
        nodeConnections.put("q6", new String[]{
                "pw0",
                "q3",
                "q4",
                "q7",
                "q8"
        });
        nodeConnections.put("q7", new String[]{
                "re1",
                "q5",
                "q6",
                "q8",
                "q9"
        });
        nodeConnections.put("q8", new String[]{
                "pw2",
                "q10",
                "q7",
                "q6"
        });
        nodeConnections.put("q9", new String[]{
                "hh4",
                "ch1",
                "rz2",
                "q10",
                "q7"
        });
        nodeConnections.put("q10", new String[]{
                "fh1",
                "pw2",
                "q9",
                "q12",
                "q8"
        });
        nodeConnections.put("q11", new String[]{
                "ch1",
                "dh1",
                "q12"
        });
        nodeConnections.put("q12", new String[]{
                "q11",
                "q15",
                "q9",
                "q18",
                "q10",
                "fh1"
        });
        nodeConnections.put("q13", new String[]{
                "dh1",
                "q14",
                "q17",
                "cc1",
                "y2"
        });
        nodeConnections.put("q14", new String[]{
                "q13",
                "q12",
                "q16"
        });
        nodeConnections.put("q15", new String[]{
                "q12",
                "ky5",
                "q16"

        });
        nodeConnections.put("q16", new String[]{
                "q15",
                "q14",
                "q17"
        });
        nodeConnections.put("q17", new String[]{
                "q13",
                "q16",
                "d1"
        });
        nodeConnections.put("q18", new String[]{
                "q12",
                "ky5"
        });
        nodeConnections.put("gt1", new String[]{
                "gt2",
                "gt3",
                "ca1"
        });
        nodeConnections.put("gt2", new String[]{
                "gt1",
                "gt3",
                "kb6"
        });

        nodeConnections.put("gt3", new String[]{
                "gt2",
                "gt1"
        });

        nodeConnections.put("f1", new String[]{
                "ky1",
                "f2",
                "f3"
        });

        nodeConnections.put("f2", new String[]{
                "f1",
                "f3",
                "kb3",
                "d2"
        });

        nodeConnections.put("f3", new String[]{
                "kb4",
                "f4",
                "f6",
                "f1",
                "f2"
        });

        nodeConnections.put("f4", new String[]{
                "ky1",
                "f5",
                "f7",
                "f3",
                "kg1"

        });

        nodeConnections.put("f5", new String[]{
                "f4",
                "ky3",
                "c1",
                "f6"
        });

        nodeConnections.put("f6", new String[]{
                "f5",
                "r0",
                "f7",
                "f3",
                "gb1",
                "kg1"
        });

        nodeConnections.put("f7", new String[]{
                "kb5",
                "f4",
                "f3",
                "f6",
                "kg1"
        });

        nodeConnections.put("r0", new String[]{
                "ky4",
                "c2",
                "c5",
                "f6",
                "gb1"
        });

        nodeConnections.put("r1", new String[]{
                "r2",
                "mu2",
                "ca1"
        });

        nodeConnections.put("r2", new String[]{
                "r1",
                "r3"
        });
        nodeConnections.put("r3", new String[]{
                "mu1",
                "r4",
                "r8"
        });

        nodeConnections.put("r4", new String[]{
                "r3",
                "r5"
        });

        nodeConnections.put("r5", new String[]{
                "r4",
                "r6"
        });

        nodeConnections.put("r6", new String[]{
                "r5",
                "r7"
        });

        nodeConnections.put("r7", new String[]{
                "r6",
                "s1",
                "s2"
        });

        nodeConnections.put("r8", new String[]{
                "r3",
                "dh3",
                "r9"
        });

        nodeConnections.put("r9", new String[]{
                "r8",
                "r10"
        });

        nodeConnections.put("r10", new String[]{
                "r9",
                "ra1"
        });
        nodeConnections.put("c0", new String[]{
                "rr2",
                "rr3",
                "c9"
        });
        nodeConnections.put("c1", new String[]{
                "ky4",
                "c2",
                "c3",
                "ky5"

        });
        nodeConnections.put("c2", new String[]{
                "c1",
                "c5",
                "r0"
        });
        nodeConnections.put("c3", new String[]{
                "c5",
                "c1",
                "c6",
                "q18",
                "c8"
        });
        nodeConnections.put("c4", new String[]{
                "c7",
                "rr3",
                "rr5"
        });
        nodeConnections.put("c5", new String[]{
                "r9",
                "c2",
                "c3",
                "dh2",
                "c7",
                "r0"

        });
        nodeConnections.put("c6", new String[]{
                "kp1",
                "c7",
                "dh2",
                "c3"
        });
        nodeConnections.put("c7", new String[]{
                "c4",
                "c8",
                "c5",
                "c6"
        });
        nodeConnections.put("c8", new String[]{
                "dh1",
                "c7",
                "ae1",
                "c9",
                "dh2",
                "c3"
        });
        nodeConnections.put("c9", new String[]{
                "ae1",
                "c8",
                "dh1",
                "c0"
        });
        nodeConnections.put("rr1", new String[]{
                "rr2",
                "pw4",
                "s6"
        });
        nodeConnections.put("rr2", new String[]{
                "c0",
                "rr1"
        });
        nodeConnections.put("rr3", new String[]{
                "c4",
                "rr4",
                "c0",
                "pw4",
                "pw3"
        });
        nodeConnections.put("rr4", new String[]{
                "pw3",
                "pw4",
                "rr3",
                "rr6"
        });
        nodeConnections.put("rr5", new String[]{
                "c4",
                "rr6"
        });
        nodeConnections.put("rr6", new String[]{
                "rr4",
                "rr5",
                "rr7",
                "pw3"
        });
        nodeConnections.put("rr7", new String[]{
                "pw2",
                "rr6",
                "q10",
                "q8",
                "q18"
        });
        nodeConnections.put("q0", new String[]{
                "q1",
                "sb2",
                "q2"
        });
        nodeConnections.put("q1", new String[]{
                "q0",
                "ro1",
                "q2",
                "q3"
        });
        nodeConnections.put("q2", new String[]{
                "re4",
                "q0",
                "q5",
                "q4",
                "q3",
                "q1"
        });
        nodeConnections.put("q3", new String[]{
                "q4",
                "q2",
                "q6",
                "q1",
                "s8",
                "ro1"
        });
        nodeConnections.put("q4", new String[]{
                "q2",
                "q3",
                "q5",
                "q6"
        });
        nodeConnections.put("q5", new String[]{
                "q2",
                "q4",
                "q7"
        });

        nodeConnections.put("kb6", new String[]{
                "gt2",
                "kb5"
        });
        nodeConnections.put("kb5", new String[]{
                "kb6",
                "kb4",
                "f7"
        });
        nodeConnections.put("ky1", new String[]{
                "ky2",
                "f4",
                "f1"
        });
        nodeConnections.put("ky2", new String[]{
                "ky1",
                "d3",
                "f1"
        });
        nodeConnections.put("ky3", new String[]{
                "f5"

        });
        nodeConnections.put("ky4", new String[]{
                "r0",
                "c1"
        });
        nodeConnections.put("ky5", new String[]{
                "q15",
                "q18"
        });
        nodeConnections.put("ky6", new String[]{
                "d1"

        });
        nodeConnections.put("pw0", new String[]{
                "q6"

        });
        nodeConnections.put("pw1", new String[]{
                "s8"

        });
        nodeConnections.put("pw2", new String[]{
                "q8",
                "q10",
                "rr7"
        });
        nodeConnections.put("pw3", new String[]{
                "pw4",
                "rr6",
                "rr4"
        });
        nodeConnections.put("pw4", new String[]{
                "pw3",
                "rr1",
                "rr3",
                "rr4"
        });
        nodeConnections.put("pw5", new String[]{
                "s7"

        });
        nodeConnections.put("s1", new String[]{
                "g3",
                "s2",
                "r7",
                "fg2"
        });
        nodeConnections.put("s2", new String[]{
                "s1",
                "fg2",
                "s3",
                "r7"
        });
        nodeConnections.put("s3", new String[]{
                "s2",
                "s4",
                "fg1"
        });
        nodeConnections.put("s4", new String[]{
                "s3",
                "s5",
                "su3"
        });
        nodeConnections.put("s5", new String[]{
                "su1",
                "s4",
                "s6"
        });
        nodeConnections.put("s6", new String[]{
                "s5",
                "rr1",
                "s7",
                "s10"
        });
        nodeConnections.put("s7", new String[]{
                "pw5",
                "s6",
                "s8"
        });
        nodeConnections.put("s8", new String[]{
                "ro2",
                "pw1",
                "q3"
        });
        nodeConnections.put("re1", new String[]{
                "q7",
                "hh3"
        });
        nodeConnections.put("re2", new String[]{
                "re3"

        });
        nodeConnections.put("re3", new String[]{
                "re2",
                "re4"
        });
        nodeConnections.put("re4", new String[]{
                "sb0",
                "q2",
                "re3"
        });
        nodeConnections.put("g2", new String[]{
                "s15"

        });
        nodeConnections.put("g3", new String[]{
                "s1"

        });
        nodeConnections.put("jh1", new String[]{
                "d4"

        });
        nodeConnections.put("jh2", new String[]{
                "d2"

        });
        nodeConnections.put("ro1", new String[]{
                "q1",
                "ro2",
                "q3",
                "ro3"
        });
        nodeConnections.put("ro2", new String[]{
                "s8",
                "ro1",
                "q3",
                "q1"
        });
        nodeConnections.put("su1", new String[]{
                "s5",
                "su3"
        });
        nodeConnections.put("su2", new String[]{
                "su3",
                "fg3"
        });
        nodeConnections.put("fg1", new String[]{
                "fg3",
                "s3"
        });
        nodeConnections.put("fg2", new String[]{
                "s1",
                "s2"
        });
        nodeConnections.put("dh1", new String[]{
                "c8",
                "ae1",
                "c9"
        });
        nodeConnections.put("dh2", new String[]{
                "c6",
                "c8",
                "c5"
        });
        nodeConnections.put("dh3", new String[]{
                "r8"

        });
        nodeConnections.put("sb0", new String[]{
                "s15",
                "re4",
                "sb1"
        });
        nodeConnections.put("sb1", new String[]{
                "sb0"

        });
        nodeConnections.put("sb2", new String[]{
                "q0"

        });
        nodeConnections.put("kg1", new String[]{
                "f7",
                "f4",
                "f6"
        });
        nodeConnections.put("mu1", new String[]{
                "r3"

        });
        nodeConnections.put("mu2", new String[]{
                "r1"

        });
        nodeConnections.put("gb1", new String[]{
                "f6",
                "r0"
        });
        nodeConnections.put("ra1", new String[]{
                "r10"

        });
        nodeConnections.put("kp1", new String[]{
                "c6"

        });
        nodeConnections.put("ae1", new String[]{
                "dh1",
                "c8",
                "c9"
        });
        nodeConnections.put("su3", new String[]{
                "su1",
                "su2",
                "s4"
        });


        for (String key : nodeCoordinates.keySet()) {
            mMap.addMarker(new MarkerOptions().position(nodeCoordinates.get(key)));
        }
        for (String key : nodeConnections.keySet()) {
            for (String s : nodeConnections.get(key))
            mMap.addPolyline(new PolylineOptions()
                    .add(nodeCoordinates.get(key), nodeCoordinates.get(s))
                    .width(5)
                    .color(Color.GREEN));
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
