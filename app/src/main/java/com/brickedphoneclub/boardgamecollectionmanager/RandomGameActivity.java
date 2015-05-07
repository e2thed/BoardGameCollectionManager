package com.brickedphoneclub.boardgamecollectionmanager;


import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;



public class RandomGameActivity extends Activity {

    public static int collectionSize, randomGeneratedNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_game);

        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        if (!(extrasBundle == null) && !(extrasBundle.isEmpty())) {
            loadGame(extrasBundle.getLong("id"));
        } else {
            Log.e("NO GAME ID", "Error the game id wasn't found in the bundle.");
        }

        Button buttonRecord = (Button)findViewById(R.id.btn_recordGamePlay);
        buttonRecord.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                Intent recordIntent = new Intent();
                startActivity(recordIntent);

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
        if (id == R.id.action_random) {

            BoardGameManager bgm = BoardGameManager.getInstance(this);
            BoardGameFilter bgfilter = BoardGameFilter.getInstance(this);
            //Bundle randomBundle = new Bundle();
            final Random myRandom = new Random();
            if(bgfilter.checkActiveFilter() == true){
                collectionSize = bgfilter.getFilterList().size();
                randomGeneratedNumber = myRandom.nextInt(collectionSize);
                randomGeneratedNumber = randomGeneratedNumber + 1;
                Log.i("RandomNum: ", " " + randomGeneratedNumber);
                BoardGame bg =  bgfilter.getFilterList().get(randomGeneratedNumber);
                loadGame(bg.getObjectId());
            }
            else{
                collectionSize =  bgm.getCollectionSize();
                randomGeneratedNumber = myRandom.nextInt(collectionSize);
                randomGeneratedNumber = randomGeneratedNumber + 1;
                Log.i("RandomNum: ", " " + randomGeneratedNumber);
                BoardGame bg = bgm.getBgList().get(randomGeneratedNumber);
                loadGame(bg.getObjectId());
            }



            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private void loadGame(long id){
        BoardGameManager bgm = BoardGameManager.getInstance(this);
        BoardGame game = bgm.getBoardGameById(id);
        BoardGameAndView container = new BoardGameAndView();
        container.BG = game;
        container.task = "image";

        Bitmap image = game.getImage();
        if (image == null) {    //If we haven't cached the large coverart image yet
            DownloadImageTask loader = new DownloadImageTask();     //Asyncronously download the large coverart image
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
        }

        ((TextView) findViewById(R.id.lbl_detailValueGameName)).setText(game.getName());
        ((TextView) findViewById(R.id.lbl_detailValueYear)).setText(game.getYearPublished());
        ((TextView) findViewById(R.id.lbl_detailValueRating)).setText(game.getRatingToString());
        ((TextView) findViewById(R.id.lbl_detailValueNumPlayers)).setText(game.getPlayerRange());
        ((TextView) findViewById(R.id.lbl_detailValuePlayTime)).setText(game.getPlayTimeRangeToString());
        ((TextView) findViewById(R.id.lbl_detailValueAgeGroup)).setText(game.getAgeGroupToString());
        ((TextView) findViewById(R.id.lbl_detailValueCategory)).setText(game.getCategoryToString());
        ((TextView) findViewById(R.id.lbl_detailValueMechanic)).setText(game.getMechanicsToString());
        ((ImageView) findViewById(R.id.img_detailImage)).setImageBitmap(game.getThumbnail());
        Log.i("DETAIL SCREEN", "Details of game loaded for ID:" + game.getObjectId());
        Log.i("DETAIL SCREEN", "Rating:" + game.getRating());
    }
        }







