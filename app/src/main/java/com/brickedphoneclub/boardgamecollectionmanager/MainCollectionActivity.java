package com.brickedphoneclub.boardgamecollectionmanager;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        final BoardGameFilter filter = BoardGameFilter.getInstance(this);
        // initialize the list view
        setListAdapter(new GameAdapter(this, R.layout.game_item, bgm.getBgList()));
        //GAG: On startup disable the filter layout. No filters should be enabled when the app starts.
        LinearLayout filterLayout = (LinearLayout)findViewById(R.id.lnl_CollectionFilters);
        filterLayout.setVisibility(View.GONE);

        final ImageButton cancelPlayers = (ImageButton) findViewById(R.id.imgbtn_CollectionCancelPlayers);
        final TextView lblFilterPlayers = (TextView) findViewById(R.id.lbl_CollectionFilterPlayers);
        final ImageButton cancelTime = (ImageButton) findViewById(R.id.imgbtn_CollectionCancelTime);
        final TextView lblFilterTime = (TextView) findViewById(R.id.lbl_CollectionFilterTime);
        final ImageButton cancelAge = (ImageButton) findViewById(R.id.imgbtn_CollectionCancelAge);
        final TextView lblFilterAge = (TextView) findViewById(R.id.lbl_CollectionFilterAge);
        final ImageButton cancelMechanic = (ImageButton) findViewById(R.id.imgbtn_CollectionCancelMechanic);
        final TextView lblFilterMechanic = (TextView) findViewById(R.id.lbl_CollectionFilterMechanic);
        final ImageButton cancelCategory = (ImageButton) findViewById(R.id.imgbtn_CollectionCancelCategory);
        final TextView lblFilterCategory = (TextView) findViewById(R.id.lbl_CollectionFilterCategory);
        final ImageButton cancelRating = (ImageButton) findViewById(R.id.imgbtn_CollectionCancelRating);
        final TextView lblFilterRating = (TextView) findViewById(R.id.lbl_CollectionFilterRating);

        disableLabelAndButton(lblFilterPlayers, cancelPlayers);
        cancelPlayers.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("COLLECTION", "Disable player filter.");
                filter.setNumPlayers("");
                disableLabelAndButton(lblFilterPlayers, cancelPlayers);
                reloadList();
            }
        });


        disableLabelAndButton(lblFilterTime, cancelTime);
        cancelTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("COLLECTION", "Disable play time filter.");
                filter.setPlayTime("");
                disableLabelAndButton(lblFilterTime, cancelTime);
                reloadList();
            }
        });

        disableLabelAndButton(lblFilterAge, cancelAge);
        cancelAge.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("COLLECTION", "Disable age filter.");
                filter.setAgeGroup("");
                disableLabelAndButton(lblFilterAge, cancelAge);
                reloadList();
            }
        });

        disableLabelAndButton(lblFilterMechanic, cancelMechanic);
        cancelMechanic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("COLLECTION", "Disable mechanic filter.");
                filter.setMechanic("");
                disableLabelAndButton(lblFilterMechanic, cancelMechanic);
                reloadList();
            }
        });

        disableLabelAndButton(lblFilterCategory, cancelCategory);
        cancelCategory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("COLLECTION", "Disable category filter.");
                filter.setCategory("");
                disableLabelAndButton(lblFilterCategory, cancelCategory);
                reloadList();
            }
        });

        disableLabelAndButton(lblFilterRating, cancelRating);
        cancelRating.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("COLLECTION", "Disable rating filter.");
                filter.setRating("");
                disableLabelAndButton(lblFilterRating, cancelRating);
                reloadList();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadList();

    }

    public void reloadList() {
        BoardGameManager bgm = BoardGameManager.getInstance(this);
        BoardGameFilter filter = BoardGameFilter.getInstance(this);

        Log.d("ON RESUME", "The number of boardgames in the collection is "+bgm.getCollectionSize());
        //Check to see if any filters are active and if so filter the list.
        if(filter.checkActiveFilter() == true) {
            Log.i("RESUME FILTER TRUE", filter.toString());
            //GAG: Make the Linear Layout visible.
            LinearLayout filterLayout = (LinearLayout)findViewById(R.id.lnl_CollectionFilters);
            filterLayout.setVisibility(View.VISIBLE);
            //GAG: Enable the labels and buttons for any active filters
            checkFilterButtons(filter);
            GameAdapter filterAdapter = new GameAdapter(this,R.layout.game_item, filter.applyFilters());
            filterAdapter.notifyDataSetChanged();
            setListAdapter(filterAdapter);

        } else {
            GameAdapter ga = new GameAdapter(this,R.layout.game_item, bgm.getBgList());
            ga.notifyDataSetChanged();
            setListAdapter(ga);
            //GAG: If no filters are active than make the linear layout gone, doesn't take up any space.
            LinearLayout filterLayout = (LinearLayout)findViewById(R.id.lnl_CollectionFilters);
            filterLayout.setVisibility(View.GONE);
        }
    }


    private void disableLabelAndButton(TextView label, ImageButton imgBtn) {
        imgBtn.setVisibility(View.GONE);
        imgBtn.setEnabled(false);
        imgBtn.setClickable(false);
        label.setVisibility(View.GONE);
        label.setEnabled(false);
        label.setText("");
    }

    private void enableLabelAndButton(TextView label, ImageButton imgBtn) {
        imgBtn.setVisibility(View.VISIBLE);
        imgBtn.setEnabled(true);
        imgBtn.setClickable(true);
        label.setVisibility(View.VISIBLE);
        label.setEnabled(true);
    }

    private void checkFilterButtons(BoardGameFilter filter) {
        if(!filter.getNumPlayers().equals("")) {
            ImageButton cancelPlayers = (ImageButton) findViewById(R.id.imgbtn_CollectionCancelPlayers);
            TextView lblFilterPlayers = (TextView) findViewById(R.id.lbl_CollectionFilterPlayers);
            enableLabelAndButton(lblFilterPlayers, cancelPlayers);
            lblFilterPlayers.setText(filter.getNumPlayers() + " players");
        }
        if(!filter.getPlayTime().equals("")) {
            ImageButton btn = (ImageButton) findViewById(R.id.imgbtn_CollectionCancelTime);
            TextView lbl = (TextView) findViewById(R.id.lbl_CollectionFilterTime);
            enableLabelAndButton(lbl, btn);
            lbl.setText(filter.getPlayTime() + " minutes");
        }
        if(!filter.getAgeGroup().equals("")) {
            ImageButton btn = (ImageButton) findViewById(R.id.imgbtn_CollectionCancelAge);
            TextView lbl = (TextView) findViewById(R.id.lbl_CollectionFilterAge);
            enableLabelAndButton(lbl, btn);
            lbl.setText(filter.getAgeGroup() + " age");
        }
        if(!filter.getCategory().equals("")) {
            ImageButton btn = (ImageButton) findViewById(R.id.imgbtn_CollectionCancelCategory);
            TextView lbl = (TextView) findViewById(R.id.lbl_CollectionFilterCategory);
            enableLabelAndButton(lbl, btn);
            lbl.setText(filter.getCategory());
        }
        if(!filter.getMechanic().equals("")) {
            ImageButton btn = (ImageButton) findViewById(R.id.imgbtn_CollectionCancelMechanic);
            TextView lbl = (TextView) findViewById(R.id.lbl_CollectionFilterMechanic);
            enableLabelAndButton(lbl, btn);
            lbl.setText(filter.getMechanic());
        }
        if(!filter.getRating().equals("")) {
            ImageButton btn = (ImageButton) findViewById(R.id.imgbtn_CollectionCancelRating);
            TextView lbl = (TextView) findViewById(R.id.lbl_CollectionFilterRating);
            enableLabelAndButton(lbl, btn);
            lbl.setText(filter.getRating() + " rating");
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

            BoardGameManager bgm = BoardGameManager.getInstance(this);

            Bundle randomBundle = new Bundle();
            randomBundle.putInt("CollectionSize", bgm.getCollectionSize());
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
                    Log.e("FileHandler", "Taking too long to download file " + te.getMessage(), te);
                } catch (InterruptedException ie) {
                    Log.e("FileHandler", "Taking too long to download file " + ie.getMessage(), ie);
                } catch (ExecutionException ee) {
                    Log.e("FileHandler", "Taking too long to download file " + ee.getMessage(), ee);
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

