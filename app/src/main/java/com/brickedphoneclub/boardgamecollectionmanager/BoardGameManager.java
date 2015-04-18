package com.brickedphoneclub.boardgamecollectionmanager;

import android.content.Context;
import java.util.ArrayList;

/**
 * Created by Giovanni Galasso on 4/18/2015.
 */
public class BoardGameManager {
    private static BoardGameManager ourInstance = null;
    private ArrayList<BoardGame> bgList = new ArrayList<>();
    private Context context;

    public static BoardGameManager getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new BoardGameManager(context);
        }
        return ourInstance;
    }

    private BoardGameManager(Context context) {
    }

    public void addBoardGame(BoardGame bg) {

    }

    public void deleteBoardGame(BoardGame bg) {

    }

    public void deleteBoardGameById(Integer id) {

    }

    private ArrayList<BoardGame> createDefaults() {
        //e.g.: contactList.add(new Contact("Malcolm", "Reynolds", "Captain", "Mobile", "123-456-7890", "", "m123.hotmail", "Twitter", "MRey", 1));
        return bgList;
    }


}
