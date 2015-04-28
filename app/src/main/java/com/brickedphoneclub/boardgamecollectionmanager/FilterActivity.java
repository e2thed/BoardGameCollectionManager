package com.brickedphoneclub.boardgamecollectionmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.HashMap;
import java.util.Map;



public class FilterActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private Map filters = new HashMap();
    private static String filterNumOfPlayers, filterPlayTime, filterAgeGroup, filterMechanic, filterCategory, filterRating;
    private Spinner spnNumPlayers, spnPlayTime, spnAgeGroup, spnMechanic, spnCategory, spnRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        BoardGameManager bgm = BoardGameManager.getInstance(this);
        bgm.getNumOfPlayerFilterOptions();

        spnNumPlayers = (Spinner) findViewById(R.id.spn_filterNumPlayers);
        spnPlayTime = (Spinner) findViewById(R.id.spn_filterPlayTime);
        spnAgeGroup = (Spinner) findViewById(R.id.spn_filterAgeGroup);
        spnMechanic = (Spinner) findViewById(R.id.spn_filterMechanic);
        spnCategory = (Spinner) findViewById(R.id.spn_filterCategory);
        spnRating = (Spinner) findViewById(R.id.spn_filterRating);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.filter_player_count_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spnNumPlayers.setAdapter(adapter);
        // set value for name spinner
        spnNumPlayers.setSelection(((ArrayAdapter<String>) spnNumPlayers.getAdapter()).getPosition(filterNumOfPlayers));
        // listener for change in selection
        spnNumPlayers.setOnItemSelectedListener(this);

        //Play Time
        ArrayAdapter<CharSequence> aPlay = ArrayAdapter.createFromResource(this, R.array.filter_play_time_array, android.R.layout.simple_spinner_item);
        aPlay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPlayTime.setAdapter(aPlay);
        spnPlayTime.setSelection(((ArrayAdapter<String>) spnPlayTime.getAdapter()).getPosition(filterPlayTime));
        spnPlayTime.setOnItemSelectedListener(this);

        //Age Group
        ArrayAdapter<CharSequence> aAge = ArrayAdapter.createFromResource(this, R.array.filter_age_group_array, android.R.layout.simple_spinner_item);
        aAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAgeGroup.setAdapter(aAge);
        spnAgeGroup.setSelection(((ArrayAdapter<String>) spnAgeGroup.getAdapter()).getPosition(filterAgeGroup));
        spnAgeGroup.setOnItemSelectedListener(this);

        //Category
        ArrayAdapter<CharSequence> aCat = ArrayAdapter.createFromResource(this, R.array.filter_category_array, android.R.layout.simple_spinner_item);
        aCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategory.setAdapter(aCat);
        spnCategory.setSelection(((ArrayAdapter<String>) spnCategory.getAdapter()).getPosition(filterCategory));
        spnCategory.setOnItemSelectedListener(this);

        //Mechanic
        ArrayAdapter<CharSequence> aMec = ArrayAdapter.createFromResource(this, R.array.filter_mechanic_array, android.R.layout.simple_spinner_item);
        aMec.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMechanic.setAdapter(aMec);
        spnMechanic.setSelection(((ArrayAdapter<String>) spnMechanic.getAdapter()).getPosition(filterMechanic));
        spnMechanic.setOnItemSelectedListener(this);

        //Rating
        ArrayAdapter<CharSequence> aRate = ArrayAdapter.createFromResource(this, R.array.filter_rating_array, android.R.layout.simple_spinner_item);
        aRate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnRating.setAdapter(aRate);
        spnRating.setSelection(((ArrayAdapter<String>) spnRating.getAdapter()).getPosition(filterRating));
        spnRating.setOnItemSelectedListener(this);

        /*private void setSpinner(Spinner s, String str, ArrayAdapter<CharSequence> adapter) {
            s.setAdapter(adapter);
            s.setSelection(((ArrayAdapter<String>) s.getAdapter()).getPosition(str));
            s.setOnItemSelectedListener(this);
        }*/

        //Cancel button, e.g. cancel out of any new applied filters.
        final Button btn_cancel = (Button) findViewById(R.id.btn_filterCancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        //Apply button to apply any selected filters to the list.
        final Button btn_apply = (Button) findViewById(R.id.btn_filterApply);
        btn_apply.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO: do filter apply actions here, and remove finish if necessary
                finish();
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        parent.getItemAtPosition(pos);
        filterNumOfPlayers = spnNumPlayers.getSelectedItem().toString();
        Log.i("SPINNER", "Num of Players: " + filterNumOfPlayers);
        filterPlayTime = spnPlayTime.getSelectedItem().toString();
        Log.i("SPINNER", "Play Time: " + filterPlayTime);
        filterAgeGroup = spnAgeGroup.getSelectedItem().toString();
        Log.i("SPINNER", "Age Group: " + filterAgeGroup);
        filterCategory = spnCategory.getSelectedItem().toString();
        Log.i("SPINNER", "Category: " + filterCategory);
        filterMechanic = spnMechanic.getSelectedItem().toString();
        Log.i("SPINNER", "Mechanic: " + filterMechanic);
        filterRating = spnRating.getSelectedItem().toString();
        Log.i("SPINNER", "Rating: " + filterRating);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
