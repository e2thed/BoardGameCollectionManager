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
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class MainCollectionActivity extends ListActivity {

    public static String[] activeSortOptions = new String[3];
    public String activeSearch = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_collection);
        BoardGameManager bgm = BoardGameManager.getInstance(this);
        // initialize the list view
        setListAdapter(new GameAdapter(this, R.layout.game_item, bgm.getBgList()));

    }

    @Override
    public void onResume() {
        super.onResume();
        BoardGameManager bgm = BoardGameManager.getInstance(this);
        BoardGameFilter filter = BoardGameFilter.getInstance(this);

        Log.d("ON RESUME", "The number of boardgames in the collection is "+bgm.getCollectionSize());
        //Check to see if any filters are active and if so filter the list.
        if(filter.checkActiveFilter() == true) {
            Log.i("RESUME FILTER TRUE", filter.toString());
            GameAdapter filterAdapter = new GameAdapter(this,R.layout.game_item, filter.applyFilters());
            filterAdapter.notifyDataSetChanged();
            setListAdapter(filterAdapter);
        } else {
            GameAdapter ga = new GameAdapter(this,R.layout.game_item, bgm.getBgList());
            ga.notifyDataSetChanged();
            setListAdapter(ga);
        }
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
            Bundle randomBundle = new Bundle();
            randomBundle.putInt("CollectionSize", 5);
            Intent randomIntent = new Intent(this, RandomGameActivity.class);
            randomIntent.putExtras(randomBundle);
            startActivityForResult(randomIntent, 3);
            return true;
        }else if(id == R.id.action_filter){
            Intent intent = new Intent(this, FilterActivity.class);
            startActivity(intent);
            return true;
        }else if(id == R.id.action_search){
            Intent searchIntent = new Intent(this, SearchActivity.class);
            startActivityForResult(searchIntent, 2);
            return true;
        }else if(id == R.id.action_refresh){
            this.onResume();
            return true;
        }else if(id == R.id.action_record){
            Intent recordIntent = new Intent(this, MapsActivity.class);
            startActivity(recordIntent);
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
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        @Override
        public android.widget.Filter getFilter() {
            return super.getFilter();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if(convertView == null) {
                view = getLayoutInflater().inflate(R.layout.game_item, parent, false);
            } else {
                view = convertView;
            }
            BoardGame BG = getItem(position);

            TextView nameView = (TextView)view.findViewById(R.id.lbl_gamename);
            nameView.setText(BG.getName());

            TextView yearView = (TextView)view.findViewById(R.id.lbl_yrpub);
            yearView.setText(BG.getYearPublished());

            Bitmap thumbnail = BG.getThumbnail();
            if (thumbnail != null) {
                ImageView imgView_game = (ImageView)view.findViewById(R.id.img_game);
                imgView_game.setImageBitmap(thumbnail);
            }
            else {
                BoardGameAndView container = new BoardGameAndView();
                container.BG = BG;
                container.view = view;
                container.task = "getThumbnail";

                DownloadImageTask loader = new DownloadImageTask();
                loader.execute(container);

                try {

                    loader.get(3000, TimeUnit.MILLISECONDS);

                } catch (TimeoutException te) {
                    Log.e("FileHandler", "Taking too long to download file", te);
                } catch (InterruptedException ie) {
                    Log.e("FileHandler", "Taking too long to download file", ie);
                } catch (ExecutionException ee) {
                    Log.e("FileHandler", "Taking too long to download file", ee);
                }

                if (BG.getThumbnail() != null) {
                    Log.d("whateves", "Refreshing thumbnail bitmap in view");

                    ImageView imgView_game = (ImageView)view.findViewById(R.id.img_game);
                    imgView_game.setImageBitmap(BG.getThumbnail());

                    /*RefreshImageTask refresh = new RefreshImageTask();
                    refresh.execute(container);
                    */
                }

            }

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
                GameAdapter newGA = new GameAdapter(this, R.layout.game_item, bgmSort.getBgList());
                setListAdapter(newGA);
                newGA.notifyDataSetChanged();

            }
        }
      else if (requestCode == 2){
            if(resultCode == RESULT_OK){
                activeSearch = data.getStringExtra("searchthis").trim();
                Log.i("Active Search String:", activeSearch);
                BoardGameManager bgmSearch = BoardGameManager.getInstance(this);
                GameAdapter searchGA = new GameAdapter(this, R.layout.game_item, bgmSearch.getBgList());
                searchGA.getFilter().filter(activeSearch);
                setListAdapter(searchGA);

            }
        }
    }


}

