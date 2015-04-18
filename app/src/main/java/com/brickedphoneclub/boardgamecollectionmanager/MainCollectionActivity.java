package com.brickedphoneclub.boardgamecollectionmanager;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.net.URI;
import java.util.ArrayList;


public class MainCollectionActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_collection);
        BoardGameManager bgm = BoardGameManager.getInstance(this);
        // initialize the list view
        setListAdapter(new GameAdapter(this, R.layout.game_item, bgm.getBgList()));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_collection, menu);
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

   class GameAdapter extends ArrayAdapter<BoardGame> {

        public GameAdapter(Context context, int resource, ArrayList<BoardGame> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if(convertView == null) {
                view = getLayoutInflater().inflate(R.layout.game_item, parent, false);
            } else {
                view = convertView;
            }
            BoardGame bGame = getItem(position);
            TextView nameView = (TextView)view.findViewById(R.id.lbl_gamename);
            TextView yearView = (TextView)view.findViewById(R.id.lbl_yrpub);
            ImageView imgView = (ImageView)view.findViewById(R.id.img_game);


            nameView.setText(bGame.getName());
            yearView.setText(bGame.getYearpublished());
            Uri imgURI = Uri.parse("cf.geekdo-images.com/images/pic1104600_t.jpg");
            imgView.setImageURI(imgURI);
            return view;
        }
    }


}
