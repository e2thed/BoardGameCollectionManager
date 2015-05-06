package com.brickedphoneclub.boardgamecollectionmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final FileHandler fh = FileHandler.getInstance(this);

        setContentView(R.layout.activity_login);
        BoardGameManager bgm = BoardGameManager.getInstance(this);

        ((ImageView) findViewById(R.id.img_TitleImage)).setImageResource(R.drawable.bgcm_title);

        final Button btn_Login = (Button) findViewById(R.id.btn_LoginSubmit);
        btn_Login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Grab username from form and print it for debugging.
                final EditText editUserName = (EditText) findViewById(R.id.login_name_field);
                String textUserName = editUserName.getText().toString();
                Log.d("USERNAME", "User is:" + textUserName);

                fh.setUser(textUserName);

                Log.i("Info", "Switching activity to MainCollectionActivity");
                Intent myIntent=new Intent(LoginActivity.this, MainCollectionActivity.class );
                startActivity(myIntent);

                LoginActivity.this.finish();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
}
