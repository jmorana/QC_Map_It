package com.example.josh.qcmapit;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Josh on 11/17/14.
 */
public class SearchSuggestionList {
    public static final String[] buildingList = new String[] {
            "Alumni Hall", "Baseball Field", "Cafeteria", "Campbell Dome", "Honors Hall", "Jefferson Hall", "Kiely Hall", "King Hall", "Kissena Hall", "Klapper Hall", "Queens Hall", "Razran Hall", "Rosenthal Library", "Science Building", "Student Union", "The Summit", "Track and Soccer Fields"
    };

    public MainActivity mainActivity;
    public ListView listView;
    private String previousWord = "";
    private String [] previousResult = buildingList;
    public SearchSuggestionList (ListView listView, final MainActivity mainActivity) {

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
                System.err.println("ABCDE ItemClick " + position);
                mainActivity.searchBar.searchView.setQuery(getElementByPosition(position), true);
            }
        });
    }

    public void setBuildingsThatContain (String string) {
        String [] valuesArray = getBuildingsThatContain(string);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(mainActivity,
                android.R.layout.simple_list_item_1, android.R.id.text1, valuesArray);


        listView.setAdapter(adapter);
    }

    public String [] getBuildingsThatContain (String word) {
        String wordLowerCase = word.toLowerCase();
        String [] array;

        if (wordLowerCase.startsWith(previousWord)) {
            array = previousResult;
        } else {
            array = buildingList;
        }

        previousWord = wordLowerCase;

        if (word.length() == 0) {
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

    public String getElementByPosition (int position) {
        if (position >= this.previousResult.length) {
            System.err.println("Bad position in getItemByPosition()");
            return "";
        }
        return this.previousResult[position];
    }

}
