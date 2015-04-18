package com.brickedphoneclub.boardgamecollectionmanager;

import android.content.Context;
import android.util.Log;

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
        createDefaults();
    }

    public void addBoardGame(BoardGame bg) {

    }

    public void deleteBoardGame(BoardGame bg) {

    }

    public void deleteBoardGameById(Integer id) {

    }

    private ArrayList<BoardGame> createDefaults() {
        //e.g.: contactList.add(new Contact("Malcolm", "Reynolds", "Captain", "Mobile", "123-456-7890", "", "m123.hotmail", "Twitter", "MRey", 1));
        bgList.add(new BoardGame(1, "Test One", "2015", "", "cf.geekdo-images.com/images/pic1104600_t.jpg"));
        bgList.add(new BoardGame(2, "Test Two", "2008", "", "cf.geekdo-images.com/images/pic719935_t.jpg"));
        bgList.add(new BoardGame(3, "Test Three", "2003", "", "cf.geekdo-images.com/images/pic1324609_t.jpg"));
        Log.i("Info", "Added the contacts to the List.");
        return bgList;
    }

    public ArrayList<BoardGame> getBgList() {
        return bgList;
    }

    public void setBgList(ArrayList<BoardGame> bgList) {
        this.bgList = bgList;
    }
}
