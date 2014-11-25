package com.example.josh.qcmapit;

import android.view.View;
import android.widget.SearchView;

/**
 * Created by Josh on 11/17/14.
 * Search bar handler
 */
public class SearchBar {
    public SearchView searchView;
    public MainActivity mainActivity;
    private String markerSet;

    /**
     *
     * @param searchView Search Bar Object
     * @param mainActivity Start point for the app
     * @param markerSet ID to know which search bar we're using
     */
    public SearchBar (SearchView searchView, MainActivity mainActivity, String markerSet) {
        this.markerSet = markerSet;
        this.searchView = searchView;
        this.mainActivity = mainActivity;
        addListeners();

    }

    public void addListeners () {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /**
             *
             * @param query What was entered into the search bar
             * @return Has a return. No idea what it does. Weird.
             */
            @Override
            public boolean onQueryTextSubmit(String query) {
                for (String element : SearchSuggestionList.buildingList) {
                    if (element.equals(query)) {
                        mainActivity.searchSuggestionList.listView.setVisibility(View.INVISIBLE);
                        mainActivity.mapHolder.setVisibility(View.VISIBLE);
                        mainActivity.imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                        if (markerSet.toLowerCase().equals("destination")){

                            mainActivity.mapPane.setDestinationMarker(query);
                        } else if (markerSet.toLowerCase().equals("startlocation")) {

                            mainActivity.mapPane.setStartLocationMarker(query);
                        }
                    }
                }
                return false;
            }

            /**
             *
             * @param newText what was entered into the search bar
             * @return Has a return. No idea what it does. Weird.
             */
            @Override
            public boolean onQueryTextChange(String newText) {
                mainActivity.searchSuggestionList.currentSearchView = searchView;
                if (newText.length() == 0) {
                    if (markerSet.toLowerCase().equals("destination")){
                        mainActivity.mapPane.removeDestinationMarker();
                    } else if (markerSet.toLowerCase().equals("startlocation")) {
                        mainActivity.mapPane.removeStartLocationMarker();
                    }
                    mainActivity.searchSuggestionList.listView.setVisibility(View.INVISIBLE);
                    mainActivity.mapHolder.setVisibility(View.VISIBLE);
                    return false;
                }
                mainActivity.mapHolder.setVisibility(View.INVISIBLE);
                mainActivity.searchSuggestionList.listView.setVisibility(View.VISIBLE);
                mainActivity.searchSuggestionList.setBuildingsThatContain(newText);
                return false;
            }
        });
    }
}
