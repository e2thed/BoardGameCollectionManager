package com.brickedphoneclub.boardgamecollectionmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


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
        }

        return super.onOptionsItemSelected(item);
    }


    private void loadGame(long id){
        BoardGameManager bgm = BoardGameManager.getInstance(this);
        BoardGame game = bgm.getBoardGameById(id);

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
