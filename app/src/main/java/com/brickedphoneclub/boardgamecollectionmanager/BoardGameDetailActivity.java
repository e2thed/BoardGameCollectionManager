package com.brickedphoneclub.boardgamecollectionmanager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


public class BoardGameDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_game_detail);

        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        if (!(extrasBundle == null) && !(extrasBundle.isEmpty())) {
            loadGame(extrasBundle.getLong("id"));
        } else {
            Log.e("NO GAME ID", "Error the game id wasn't found in the bundle.");
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_board_game_detail, menu);
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
        } else if(id == R.id.action_random){
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
            loadGame(game.getObjectId());
        }

        return super.onOptionsItemSelected(item);
    }


    private void loadGame(long id){
        BoardGameManager bgm = BoardGameManager.getInstance(this);
        BoardGame game = bgm.getBoardGameById(id);
        BoardGameAndView container = new BoardGameAndView();
        container.BG = game;
        container.task = "image";

        Bitmap image = game.getLargeImage();
        if (image == null) {    //If we haven't cached the large coverart image yet
            DownloadImageTask loader = new DownloadImageTask();     //Asyncronously download the large coverart image
            loader.execute(container);

            //((ImageView) findViewById(R.id.img_detailImage)).setImageBitmap(game.getThumbnail());

            /*
            try {

                loader.get(3000, TimeUnit.MILLISECONDS);

            } catch (TimeoutException te) {
                Log.e("FileHandler", "Taking too long to download file", te);
            } catch (InterruptedException ie) {
                Log.e("FileHandler", "Taking too long to download file", ie);
            } catch (ExecutionException ee) {
                Log.e("FileHandler", "Taking too long to download file", ee);
            }
            */

        }

        ((TextView) findViewById(R.id.lbl_detailValueGameName)).setText(game.getName());
        ((TextView) findViewById(R.id.lbl_detailValueYear)).setText(game.getYearPublished());
        ((TextView) findViewById(R.id.lbl_detailValueRating)).setText(game.getRatingToString());
        ((TextView) findViewById(R.id.lbl_detailValueNumPlayers)).setText(game.getPlayerRange());
        ((TextView) findViewById(R.id.lbl_detailValuePlayTime)).setText(game.getPlayTimeRangeToString());
        ((TextView) findViewById(R.id.lbl_detailValueAgeGroup)).setText(game.getAgeGroupToString());
        ((TextView) findViewById(R.id.lbl_detailValueCategory)).setText(game.getCategoryToString());
        ((TextView) findViewById(R.id.lbl_detailValueMechanic)).setText(game.getMechanicsToString());

        if (image == null)      //if the large image isn't downloaded yet, then use the thumbnail
            ((ImageView) findViewById(R.id.img_detailImage)).setImageBitmap(game.getThumbnail());
        else
            ((ImageView) findViewById(R.id.img_detailImage)).setImageBitmap(image);



        Log.i("DETAIL SCREEN", "Details of game loaded for ID:" + game.getObjectId());
        Log.i("DETAIL SCREEN", "Rating:" + game.getRating());
    }

}
