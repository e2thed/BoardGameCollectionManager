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
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainCollectionActivity extends ListActivity {

    public static String[] activeSortOptions = new String[3];
    BoardGameFilter bgfilterchk = BoardGameFilter.getInstance(this);

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
        final ImageButton cancelGamName = (ImageButton) findViewById(R.id.imgbtn_CollectionCancelGamName);
        final TextView lblFilterGamName = (TextView) findViewById(R.id.lbl_CollectionFilterGamName);



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

        disableLabelAndButton(lblFilterGamName, cancelGamName);
        cancelGamName.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("COLLECTION", "Disable game search.");
                filter.setGamName("");
                disableLabelAndButton(lblFilterGamName, cancelGamName);
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
        } else {
            ImageButton btn = (ImageButton) findViewById(R.id.imgbtn_CollectionCancelPlayers);
            TextView lbl = (TextView) findViewById(R.id.lbl_CollectionFilterPlayers);
            disableLabelAndButton(lbl, btn);
        }
        if(!filter.getPlayTime().equals("")) {
            ImageButton btn = (ImageButton) findViewById(R.id.imgbtn_CollectionCancelTime);
            TextView lbl = (TextView) findViewById(R.id.lbl_CollectionFilterTime);
            enableLabelAndButton(lbl, btn);
            lbl.setText(filter.getPlayTime() + " minutes");
        } else {
            ImageButton btn = (ImageButton) findViewById(R.id.imgbtn_CollectionCancelTime);
            TextView lbl = (TextView) findViewById(R.id.lbl_CollectionFilterTime);
            disableLabelAndButton(lbl, btn);
        }
        if(!filter.getAgeGroup().equals("")) {
            ImageButton btn = (ImageButton) findViewById(R.id.imgbtn_CollectionCancelAge);
            TextView lbl = (TextView) findViewById(R.id.lbl_CollectionFilterAge);
            enableLabelAndButton(lbl, btn);
            lbl.setText(filter.getAgeGroup() + " age");
        } else {
            ImageButton btn = (ImageButton) findViewById(R.id.imgbtn_CollectionCancelAge);
            TextView lbl = (TextView) findViewById(R.id.lbl_CollectionFilterAge);
            disableLabelAndButton(lbl, btn);
        }
        if(!filter.getCategory().equals("")) {
            ImageButton btn = (ImageButton) findViewById(R.id.imgbtn_CollectionCancelCategory);
            TextView lbl = (TextView) findViewById(R.id.lbl_CollectionFilterCategory);
            enableLabelAndButton(lbl, btn);
            lbl.setText(filter.getCategory());
        } else {
            ImageButton btn = (ImageButton) findViewById(R.id.imgbtn_CollectionCancelCategory);
            TextView lbl = (TextView) findViewById(R.id.lbl_CollectionFilterCategory);
            disableLabelAndButton(lbl, btn);
        }
        if(!filter.getMechanic().equals("")) {
            ImageButton btn = (ImageButton) findViewById(R.id.imgbtn_CollectionCancelMechanic);
            TextView lbl = (TextView) findViewById(R.id.lbl_CollectionFilterMechanic);
            enableLabelAndButton(lbl, btn);
            lbl.setText(filter.getMechanic());
        } else {
            ImageButton btn = (ImageButton) findViewById(R.id.imgbtn_CollectionCancelMechanic);
            TextView lbl = (TextView) findViewById(R.id.lbl_CollectionFilterMechanic);
            disableLabelAndButton(lbl, btn);
        }
        if(!filter.getRating().equals("")) {
            ImageButton btn = (ImageButton) findViewById(R.id.imgbtn_CollectionCancelRating);
            TextView lbl = (TextView) findViewById(R.id.lbl_CollectionFilterRating);
            enableLabelAndButton(lbl, btn);
            lbl.setText(filter.getRating() + " rating");
        } else {
            ImageButton btn = (ImageButton) findViewById(R.id.imgbtn_CollectionCancelRating);
            TextView lbl = (TextView) findViewById(R.id.lbl_CollectionFilterRating);
            disableLabelAndButton(lbl, btn);
        }
        if(!filter.getGamName().equals("")) {
            ImageButton btn = (ImageButton) findViewById(R.id.imgbtn_CollectionCancelGamName);
            TextView lbl = (TextView) findViewById(R.id.lbl_CollectionFilterGamName);
            enableLabelAndButton(lbl, btn);
            lbl.setText("Search : " + filter.getGamName());
        } else {
            ImageButton btn = (ImageButton) findViewById(R.id.imgbtn_CollectionCancelGamName);
            TextView lbl = (TextView) findViewById(R.id.lbl_CollectionFilterGamName);
            disableLabelAndButton(lbl, btn);
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
            ArrayList<BoardGame> rList;
            BoardGameFilter filter = BoardGameFilter.getInstance(this);
            BoardGameManager bgm = BoardGameManager.getInstance(this);
            if(filter.checkActiveFilter() == true) {
                rList = filter.getFilterList();
            } else {
                rList = bgm.getBgList();
            }
            final Random randomGen = new Random();
            int randomNum = randomGen.nextInt(rList.size());
            Log.i("COLLECTION RANDOM", "Random num is: " + (randomNum) + " List size:" + rList.size());
            BoardGame game = rList.get(randomNum);

            Bundle bgDetail = new Bundle();
            bgDetail.putLong("id", game.getObjectId());
            Intent intent = new Intent(this, BoardGameDetailActivity.class);
            intent.putExtras(bgDetail);
            startActivity(intent);
            return true;
        }else if(id == R.id.action_filter){
            Intent intent = new Intent(this, FilterActivity.class);
            startActivity(intent);
            return true;
        }else if(id == R.id.action_search){
            Intent searchIntent = new Intent(this, SearchActivity.class);
            startActivity(searchIntent);
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

            if(bgfilterchk.checkActiveFilter() == true) {
                if (!bgfilterchk.getNumPlayers().equals("")) {
                    yearView.setText(BG.getMinPlayers() + " - " + BG.getMaxPlayers() + " players");
                }

                if (!bgfilterchk.getPlayTime().equals("")) {
                    if (yearView.getText().length() == 0) {
                        yearView.setText(BG.getMaxPlayTime() + " min");
                    } else {
                        yearView.setText(yearView.getText().toString() + ", " + BG.getMaxPlayTime() + " min");
                    }
                }

                if (!bgfilterchk.getAgeGroup().equals("")) {
                    if (yearView.getText().length() == 0) {
                        yearView.setText(BG.getAgeGroupToString());
                    } else {
                        yearView.setText(yearView.getText().toString() + ", " + BG.getAgeGroupToString());
                    }
                }

                if (!bgfilterchk.getRating().equals("")) {
                    if (yearView.getText().length() == 0) {
                        yearView.setText(BG.getRating() + " rating");
                    } else {
                        yearView.setText(yearView.getText().toString() + ", " + BG.getRating() + " rating");
                    }
                }
            }


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

    }


}

