package com.brickedphoneclub.boardgamecollectionmanager;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class SortActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        //Spinner for by name sort options
        Spinner spinner_name = (Spinner) findViewById(R.id.txt_sortbyname);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_name = ArrayAdapter.createFromResource(this,
                R.array.alpha_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter_name.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_name.setAdapter(adapter_name);

        //Spinner for by rating sort options
        Spinner spinner_rating = (Spinner) findViewById(R.id.txt_sortbyrating);
        ArrayAdapter<CharSequence> adapter_rating = ArrayAdapter.createFromResource(this,
                R.array.rating_array, android.R.layout.simple_spinner_item);
        adapter_rating.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_rating.setAdapter(adapter_rating);

        //Spinner for by year published sort options
        Spinner spinner_yrpub = (Spinner) findViewById(R.id.txt_sortbyyear);
        ArrayAdapter<CharSequence> adapter_yrpub = ArrayAdapter.createFromResource(this,
                R.array.year_array, android.R.layout.simple_spinner_item);
        adapter_yrpub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_yrpub.setAdapter(adapter_yrpub);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sort, menu);
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

/*

package com.brickedphoneclub.boardgamecollectionmanager;

import java.util.Comparator;

public class ListComparator implements Comparator<BoardGame> {
    @Override
    public int compare(BoardGame lhs, BoardGame rhs) {
        return lhs.getName().compareTo(rhs.getName());
        //return 0;
    }
}

 */