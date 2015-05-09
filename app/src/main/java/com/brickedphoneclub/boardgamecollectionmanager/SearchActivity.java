package com.brickedphoneclub.boardgamecollectionmanager;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


public class SearchActivity extends Activity {

    private BoardGameFilter filter = BoardGameFilter.getInstance(this);
    public EditText txt_searchString;
    public ImageButton btn_delSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        txt_searchString = (EditText) findViewById(R.id.txt_searchByName);
        //txt_searchString.setImeOptions(EditorInfo.IME_ACTION_DONE);
        final Button buttonSearch = (Button) findViewById(R.id.btn_search);
        btn_delSearch = (ImageButton) findViewById(R.id.imgbtn_gamName);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String searchString = txt_searchString.getText().toString();

                if(!searchString.equals("")) {
                    filter.setGamName(searchString);
                    btn_delSearch.setVisibility(View.VISIBLE);
                }
                else {
                    filter.setGamName("");
                    btn_delSearch.setVisibility(View.GONE);
                }

                finish();
            }
        });

        btn_delSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                txt_searchString.setText("");
                filter.setGamName("");
                btn_delSearch.setVisibility(View.GONE);
            }
        });

        txt_searchString.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length()== 0){
                    btn_delSearch.setVisibility(View.GONE);
                }
                else {
                    btn_delSearch.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!filter.getGamName().equals("")) {
            txt_searchString.setText(filter.getGamName());
            txt_searchString.requestFocus();
            btn_delSearch.setVisibility(View.VISIBLE);
        } else {
            txt_searchString.setText("");
            btn_delSearch.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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
