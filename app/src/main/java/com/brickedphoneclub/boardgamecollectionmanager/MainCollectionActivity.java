package com.brickedphoneclub.boardgamecollectionmanager;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;


public class MainCollectionActivity extends ListActivity {

    public String[] activeSortOptions = new String[3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_collection);
        BoardGameManager bgm = BoardGameManager.getInstance(this);
        // initialize the list view
        //Collections.sort(bgm.getBgList(), new ListComparator());
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
        else if(id == R.id.action_sort){
            Bundle sortBundle = new Bundle();
            sortBundle.putStringArray("asoptions",activeSortOptions);
            Log.i("Sort Options Active:", activeSortOptions[0] + "/" + activeSortOptions[1] + "/" + activeSortOptions[2]);

            Intent sortIntent = new Intent(this, SortActivity.class);
            sortIntent.putExtras(sortBundle);
            startActivityForResult(sortIntent, 1);
            return true;
        }
        else if(id == R.id.action_random){
            Intent intent = new Intent(this, RandomGameActivity.class);
            startActivity(intent);
            return true;
        }else if(id == R.id.action_filter){
            Intent intent = new Intent(this, FilterActivity.class);
            startActivity(intent);
            return true;
        }else if(id == R.id.action_search){
            return true;
        }else if(id == R.id.action_refresh){
            return true;
        }else if(id == R.id.action_record){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        BoardGame bg = (BoardGame)getListAdapter().getItem(position);
        //Toast.makeText(this, "Clicked " + contact.getName() + " (" + id + ")", Toast.LENGTH_LONG).show();

        //GG: Store data to be passed into next activity into a bundle
        //See: http://www.101apps.co.za/articles/passing-data-between-activities.html
        Bundle bgDetail = new Bundle();
        bgDetail.putLong("id", bg.getObjectId());
        Log.i("BG ID:", "Id is: " + bg.getObjectId());

        //GG: Create Intent to switch to the contact detail screen. Clicking on the contact switches to the next screen.
        //See here: http://developer.android.com/training/basics/firstapp/starting-activity.html
        Intent intent = new Intent(this, BoardGameDetailActivity.class);
        intent.putExtras(bgDetail);
        startActivity(intent);
    }


   class GameAdapter extends ArrayAdapter<BoardGame> {

        public GameAdapter(Context context, int resource, ArrayList<BoardGame> objects) {
            super(context, resource, objects);
        }

       @Override
       public int getCount() {
           //Log.e("List Size", String.valueOf(super.getCount()));
           return super.getCount();
       }

       @Override
       public BoardGame getItem(int position) {
           return super.getItem(position);
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
            yearView.setText(bGame.getYearPublished());

            ImageView imgView_game = (ImageView)view.findViewById(R.id.img_game);

            AsyncTask<String, Void, Bitmap> thumbNail = new DownloadImageTask(imgView_game).execute("http://" + bGame.getThumbnail());

            return view;
        }

   }

  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                activeSortOptions = data.getStringArrayExtra("soptions");
                Log.i("Sort Options Passed:", activeSortOptions[0] + "/" + activeSortOptions[1] + "/" + activeSortOptions[2]);
                BoardGameManager bgmSort = BoardGameManager.getInstance(this);
                Collections.sort(bgmSort.getBgList(), new ListComparator(activeSortOptions[0].trim()));
                Collections.sort(bgmSort.getBgList(), new ListComparator(activeSortOptions[1].trim()));
                Collections.sort(bgmSort.getBgList(), new ListComparator(activeSortOptions[2].trim()));
                setListAdapter(new GameAdapter(this, R.layout.game_item, bgmSort.getBgList()));
            }
        }
    }


}

