package com.example.josh.qcmapit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

public class MainActivity extends Activity {

    public MapPane mapPane;
    public SearchBar destinationSearchBar;
    public SearchBar startLocationSearchBar;
    public SearchSuggestionList searchSuggestionList;
    public FrameLayout mapHolder;
    public InputMethodManager imm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpIfNeeded();
        mapHolder = ((FrameLayout) findViewById(R.id.map_holder));
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    }
    public void setUpIfNeeded () {
        setUpSearchIfNeeded();
        setUpMapIfNeeded();
        setUpSeachSuggestionListIfNeeded();
    }
    public void setUpMapIfNeeded () {
        // Do a null check to confirm that we have not already instantiated the map.
        GoogleMap map;
        if (mapPane == null || mapPane.mMap == null) {
            map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (map != null) {
                // The Map is verified. It is now safe to manipulate the map.
                mapPane = new MapPane(map);
            }
        }
    }

    public void setUpSearchIfNeeded () {
        SearchView searchView;
        if (destinationSearchBar == null || destinationSearchBar.searchView == null) {
            searchView = ((SearchView) findViewById(R.id.destinationSearchBar));
            if (searchView != null) {
                destinationSearchBar = new SearchBar(searchView, this);
            }
        }
        if (startLocationSearchBar == null || startLocationSearchBar.searchView == null) {
            searchView = ((SearchView) findViewById(R.id.startLocationSearchBar));
            if (searchView != null) {
                startLocationSearchBar = new SearchBar(searchView, this);
            }
        }
    }

    public void setUpSeachSuggestionListIfNeeded () {
        ListView listView;
        if (searchSuggestionList == null || searchSuggestionList.listView == null) {
            listView = ((ListView) findViewById(R.id.searchSuggestionListView));
            if (listView != null) {
                searchSuggestionList = new SearchSuggestionList(listView, this);
            }
        }
    }

}
