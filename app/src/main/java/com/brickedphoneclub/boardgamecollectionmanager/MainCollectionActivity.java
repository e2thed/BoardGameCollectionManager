package com.brickedphoneclub.boardgamecollectionmanager;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
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
            nameView.setText(bGame.getName());

            TextView yearView = (TextView)view.findViewById(R.id.lbl_yrpub);
            yearView.setText(bGame.getYearpublished());

            ImageView imgView_game = (ImageView)view.findViewById(R.id.img_game);

            AsyncTask<String, Void, Bitmap> thumbNail = new DownloadImageTask(imgView_game).execute("http://" + bGame.getThumbnail());

            return view;
        }
    }


}

