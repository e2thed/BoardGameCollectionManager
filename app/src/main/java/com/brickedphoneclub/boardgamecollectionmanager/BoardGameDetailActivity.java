package com.brickedphoneclub.boardgamecollectionmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
        /*((TextView) findViewById(R.id.lbl_detailValueGameName)).setText(game.getName());
        ((TextView) findViewById(R.id.lbl_detailValueGameName)).setText(game.getName());
        ((TextView) findViewById(R.id.lbl_detailValueGameName)).setText(game.getName());
        ((TextView) findViewById(R.id.lbl_detailValueGameName)).setText(game.getName());
        ((TextView) findViewById(R.id.lbl_detailValueGameName)).setText(game.getName());
        ((TextView) findViewById(R.id.lbl_detailValueGameName)).setText(game.getName());
        ((TextView) findViewById(R.id.lbl_detailValueGameName)).setText(game.getName());
        ((TextView) findViewById(R.id.lbl_detailValueGameName)).setText(game.getName());
        ((TextView) findViewById(R.id.contact_details_last_name_field)).setText(contact.getLastName());
        ((TextView) findViewById(R.id.contact_details_title_field)).setText(contact.getContactTitle());
        ((TextView) findViewById(R.id.contact_details_phone_field)).setText(contact.getPhoneType());
        ((TextView) findViewById(R.id.contact_details_phone_field2)).setText(contact.getPhoneNumber());
        ((TextView) findViewById(R.id.contact_details_email_field)).setText(contact.getEmailType());
        ((TextView) findViewById(R.id.contact_details_email_field2)).setText(contact.getEmailAdd());
        ((TextView) findViewById(R.id.contact_details_social_field)).setText(contact.getSocialType());
        ((TextView) findViewById(R.id.contact_details_social_field2)).setText(contact.getSocial());

        Log.i("ContactDetails", "ID: " + contactId +
                " First Name: " + contact.getFirstName() +
                " Last Name: " + contact.getLastName() +
                " Title: " + contact.getContactTitle() +
                " Phone: " + contact.getPhoneNumber());
        */
    }

}
