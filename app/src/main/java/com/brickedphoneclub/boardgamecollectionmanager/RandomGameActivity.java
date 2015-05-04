package com.brickedphoneclub.boardgamecollectionmanager;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import java.util.Random;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class RandomGameActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_game);

        final Random myRandom = new Random(10);

        Button buttonGenerate = (Button)findViewById(R.id.button1);
        final TextView textGenerateNumber = (TextView)findViewById(R.id.text4);

        buttonGenerate.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
               Log.i("info for random", "click Random");
               
                // TODO Auto-generated method stub

                textGenerateNumber.setText(String.valueOf(myRandom.nextInt(4)+1));


            }});

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_random_game, menu);
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







