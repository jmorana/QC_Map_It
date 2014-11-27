package com.example.josh.qcmapit;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

/**
 * Created by Josh on 11/17/14.
 * Handles the suggestion list
 */
public class SearchSuggestionList {
    public static final String[] buildingList = new String[] {
            "Alumni Hall",
            "Alumni Plaza",
            "Baseball Field",
            "Bookstore",
            "Campbell Dome",
            "Colden Auditorium",
            "Colwin Hall",
            "Continuing Education 1",
            "Continuing Education 2",
            "Cooperman Plaza",
            "Delany Hall",
            "Dining Hall",
            "FitzGerald Gym",
            "Frese Hall",
            "G Building",
            "Gate 1",
            "Gate 2",
            "Gate 3",
            "Gate 6",
            "Gate 9",
            "Gertz Center",
            "Goldstein Theatre",
            "Honors Hall",
            "I Building",
            "Indoor Tennis Center",
            "Indoor Tennis Center",
            "Jefferson Hall",
            "Kiely Hall",
            "King Hall",
            "Kissena Hall",
            "Klapper Hall",
            "Lacrosse Field",
            "LeFrak Concert Hall",
            "Main Entrance",
            "Main Exit",
            "Music Building",
            "One Stop Center",
            "Parking Lot 1",
            "Parking Lot 2",
            "Parking Lot 3",
            "Parking Lot 4",
            "Parking Lot 5",
            "Parking Lot 6",
            "Parking Lot 7",
            "Parking Lot 9",
            "Parking Lot 10",
            "Parking Lot 11",
            "Parking Lot 12",
            "Parking Lot 13",
            "Parking Lot 14",
            "Parking Lot 15N",
            "Parking Lot 15S",
            "Parking Lot 16",
            "Parking Lot 17",
            "Powdermaker Hall",
            "Public Safety",
            "Quad",
            "Queens Hall",
            "Rathaus Hall",
            "Razran Hall",
            "Remsen Hall",
            "Rosenthal Library",
            "Science Building",
            "Softball Field",
            "Student Union",
            "The Summit",
            "Track and Soccer Field",
            "WWII Memorial Plaza",
            "Welcome Center"
    };
    public MainActivity mainActivity;
    public ListView listView;
    private String previousWord = "";
    private String [] previousResult = buildingList;
    public SearchView currentSearchView;

    /**
     *
     * @param listView Actual search suggestion list
     * @param mainActivity App start point
     */
    public SearchSuggestionList (ListView listView, final MainActivity mainActivity) {
        this.currentSearchView = mainActivity.destinationSearchBar.searchView;
        this.listView = listView;
        this.mainActivity = mainActivity;
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                mainActivity,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                buildingList
        );

        listView.setAdapter(adapter);
        listView.setVisibility(View.INVISIBLE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentSearchView.setQuery(getElementByPosition(position), true);
            }
        });
    }

    /**
     *
     * @param string The search string to set suggestions
     */
    public void setBuildingsThatContain (String string) {
        String [] valuesArray = getBuildingsThatContain(string);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(mainActivity,
                android.R.layout.simple_list_item_1, android.R.id.text1, valuesArray);


        listView.setAdapter(adapter);
    }

    /**
     *
     * @param string The search string to generate suggestions
     * @return buildings that contain string
     */
    public String [] getBuildingsThatContain (String string) {
        String wordLowerCase = string.toLowerCase();
        String [] array;
        if (wordLowerCase.startsWith(previousWord)) {
            array = previousResult;
        } else {
            array = buildingList;
        }
        previousWord = wordLowerCase;

        if (string.length() == 0) {
            previousResult = buildingList;
            return buildingList;
        }
        ArrayList<String> values = new ArrayList<String>(array.length);
        for (String element : array) {
            if (element.toLowerCase().contains(wordLowerCase)) {
                values.add(element);
            }
        }
        String [] result = values.toArray(new String[values.size()]);
        previousResult = result;
        return result;
    }

    /**
     *
     * @param position of element on the last suggestion list position on the list
     * @return The element
     */
    public String getElementByPosition (int position) {
        if (position >= this.previousResult.length) {
            System.err.println("Bad position in getItemByPosition()");
            return "";
        }
        return this.previousResult[position];
    }

}
