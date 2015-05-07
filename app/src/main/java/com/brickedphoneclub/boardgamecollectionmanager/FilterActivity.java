package com.brickedphoneclub.boardgamecollectionmanager;

import android.app.Activity;
import android.content.res.Resources;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

public class FilterActivity extends Activity {

    private Spinner spnNumPlayers, spnPlayTime, spnAgeGroup, spnMechanic, spnCategory, spnRating;
    private BoardGameFilter filter = BoardGameFilter.getInstance(this);
    private ImageButton playersClear, timeClear, ageClear, mechanicClear, categoryClear, ratingClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        spnNumPlayers = (Spinner) findViewById(R.id.spn_filterNumPlayers);
        spnPlayTime = (Spinner) findViewById(R.id.spn_filterPlayTime);
        spnAgeGroup = (Spinner) findViewById(R.id.spn_filterAgeGroup);
        spnMechanic = (Spinner) findViewById(R.id.spn_filterMechanic);
        spnCategory = (Spinner) findViewById(R.id.spn_filterCategory);
        spnRating = (Spinner) findViewById(R.id.spn_filterRating);

        Resources res = getResources();
        BoardGameManager bgm = BoardGameManager.getInstance(this);

        ArrayList<String> players = new ArrayList<>(Arrays.asList(res.getStringArray(R.array.filter_player_count_array)));
        initSpinner(spnNumPlayers, players);

        ArrayList<String> time = new ArrayList<>(Arrays.asList(res.getStringArray(R.array.filter_play_time_array)));
        initSpinner(spnPlayTime, time);

        ArrayList<String> age = new ArrayList<>(Arrays.asList(res.getStringArray(R.array.filter_age_group_array)));
        initSpinner(spnAgeGroup, age);

        //ArrayList<String> cat = new ArrayList<>(Arrays.asList(res.getStringArray(R.array.filter_category_array)));
        initSpinner(spnCategory, bgm.getUniqueCategories());

        //ArrayList<String> mech = new ArrayList<>(Arrays.asList(res.getStringArray(R.array.filter_mechanic_array)));
        initSpinner(spnMechanic, bgm.getUniqueMechanics());

        ArrayList<String> rating = new ArrayList<>(Arrays.asList(res.getStringArray(R.array.filter_rating_array)));
        initSpinner(spnRating, rating);

        //Number of Players
        spnNumPlayers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ImageButton btn = (ImageButton) findViewById(R.id.imgbtn_fcPlayer);
                if(position != 0) {
                    filter.setNumPlayers(spnNumPlayers.getSelectedItem().toString());
                    Log.i("SPINNER", "Num of Players: " + filter.getNumPlayers() + " pos " + position);
                    btn.setVisibility(View.VISIBLE);
                } else {
                    filter.setNumPlayers("");
                    btn.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                filter.setNumPlayers("");
            }
        });

        //Play Time
        spnPlayTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ImageButton btn = (ImageButton) findViewById(R.id.imgbtn_fcTime);
                if(position != 0) {
                    filter.setPlayTime(spnPlayTime.getSelectedItem().toString());
                    Log.i("SPINNER", "Play Time: " + filter.getPlayTime());
                    btn.setVisibility(View.VISIBLE);
                } else {
                    filter.setPlayTime("");
                    btn.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                filter.setPlayTime("");
            }
        });

        //Age Group
        spnAgeGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ImageButton btn = (ImageButton) findViewById(R.id.imgbtn_fcAge);
                if(position != 0) {
                    filter.setAgeGroup(spnAgeGroup.getSelectedItem().toString());
                    Log.i("SPINNER", "Age Group: " + filter.getAgeGroup());
                    btn.setVisibility(View.VISIBLE);
                } else {
                    filter.setAgeGroup("");
                    btn.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                filter.setAgeGroup("");
            }
        });


        //Category
        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ImageButton btn = (ImageButton) findViewById(R.id.imgbtn_fcCategory);
                if(position != 0) {
                    filter.setCategory(spnCategory.getSelectedItem().toString());
                    Log.i("SPINNER", "Category: " + filter.getCategory());
                    btn.setVisibility(View.VISIBLE);
                } else {
                    filter.setCategory("");
                    btn.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                filter.setCategory("");
            }
        });


        //Mechanic
        spnMechanic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ImageButton btn = (ImageButton) findViewById(R.id.imgbtn_fcMechanic);
                if (position != 0) {
                    filter.setMechanic(spnMechanic.getSelectedItem().toString());
                    Log.i("SPINNER", "Mechanic: " + filter.getMechanic());
                    btn.setVisibility(View.VISIBLE);
                } else {
                    filter.setMechanic("");
                    btn.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                filter.setMechanic("");
            }
        });


        //Rating
        spnRating.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ImageButton btn = (ImageButton) findViewById(R.id.imgbtn_fcRating);
                if (position != 0) {
                    filter.setRating(spnRating.getSelectedItem().toString());
                    Log.i("SPINNER", "Rating: " + filter.getRating());
                    btn.setVisibility(View.VISIBLE);
                } else {
                    filter.setRating("");
                    btn.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                filter.setRating("");
            }
        });

        //Cancel button, e.g. cancel out of any new applied filters.
        final Button btn_cancel = (Button) findViewById(R.id.btn_filterCancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                filter.setNumPlayers("");
                playersClear.setVisibility(View.GONE);
                filter.setPlayTime("");
                timeClear.setVisibility(View.GONE);
                filter.setAgeGroup("");
                ageClear.setVisibility(View.GONE);
                filter.setMechanic("");
                mechanicClear.setVisibility(View.GONE);
                filter.setCategory("");
                categoryClear.setVisibility(View.GONE);
                filter.setRating("");
                ratingClear.setVisibility(View.GONE);
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

        playersClear = (ImageButton) findViewById(R.id.imgbtn_fcPlayer);
        playersClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                spnNumPlayers.setSelection(0);
                filter.setNumPlayers("");
                playersClear.setVisibility(View.GONE);
            }
        });

       timeClear = (ImageButton) findViewById(R.id.imgbtn_fcTime);
        timeClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                spnPlayTime.setSelection(0);
                filter.setPlayTime("");
                timeClear.setVisibility(View.GONE);
            }
        });

        ageClear = (ImageButton) findViewById(R.id.imgbtn_fcAge);
        ageClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                spnAgeGroup.setSelection(0);
                filter.setAgeGroup("");
                ageClear.setVisibility(View.GONE);
            }
        });

        mechanicClear = (ImageButton) findViewById(R.id.imgbtn_fcMechanic);
        mechanicClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                spnMechanic.setSelection(0);
                filter.setMechanic("");
                mechanicClear.setVisibility(View.GONE);
            }
        });

        categoryClear = (ImageButton) findViewById(R.id.imgbtn_fcCategory);
        categoryClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                spnCategory.setSelection(0);
                filter.setCategory("");
                categoryClear.setVisibility(View.GONE);
            }
        });

        ratingClear = (ImageButton) findViewById(R.id.imgbtn_fcRating);
        ratingClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                spnRating.setSelection(0);
                filter.setRating("");
                ratingClear.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("FILTER", "INSIDE ON RESUME **************************************");
        BoardGameFilter filter = BoardGameFilter.getInstance(this);

        //Player Count
        if(!filter.getNumPlayers().equals("")) {
            spnNumPlayers.setSelection(getIndex(spnNumPlayers, filter.getNumPlayers()));
            playersClear.setVisibility(View.VISIBLE);
        } else {
            spnNumPlayers.setSelection(0);
            playersClear.setVisibility(View.GONE);
        }
        //Play Time
        if(!filter.getPlayTime().equals("")) {
            spnPlayTime.setSelection(getIndex(spnPlayTime, filter.getPlayTime()));
            timeClear.setVisibility(View.VISIBLE);
        } else {
            spnPlayTime.setSelection(0);
            timeClear.setVisibility(View.GONE);
        }
        //Age Group
        if(!filter.getAgeGroup().equals("")) {
            spnAgeGroup.setSelection(getIndex(spnAgeGroup, filter.getAgeGroup()));
            ageClear.setVisibility(View.VISIBLE);
        } else {
            spnAgeGroup.setSelection(0);
            ageClear.setVisibility(View.GONE);
        }
        //Mechanic
        if(!filter.getMechanic().equals("")) {
            spnMechanic.setSelection(getIndex(spnMechanic, filter.getMechanic()));
            mechanicClear.setVisibility(View.VISIBLE);
        } else {
            spnMechanic.setSelection(0);
            mechanicClear.setVisibility(View.GONE);
        }
        //Category
        if(!filter.getCategory().equals("")) {
            spnCategory.setSelection(getIndex(spnCategory, filter.getCategory()));
            categoryClear.setVisibility(View.VISIBLE);
        } else {
            spnCategory.setSelection(0);
            categoryClear.setVisibility(View.GONE);
        }
        //Rating
        if(!filter.getRating().equals("")) {
            spnRating.setSelection(getIndex(spnRating, filter.getRating()));
            ratingClear.setVisibility(View.VISIBLE);
        } else {
            spnRating.setSelection(0);
            ratingClear.setVisibility(View.GONE);
        }
    }

    //Little function to get the index of the passed String for the spinner.
    private int getIndex(Spinner spinner, String myString){
        int index = 0;
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        Log.i("INDEX", "Index for: " + myString + " is " + index);
        return index;
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

    private void initSpinner(Spinner spin, ArrayList list) {
        //Create the adapter and pass it the list of items to populate
        ArrayAdapter<ArrayList> adapter = new ArrayAdapter<ArrayList>(this,android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        //Set the initial selection to the first element.
        spin.setSelection(0);
    }


}
