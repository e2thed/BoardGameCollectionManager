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
        bgList.add(bg);
    }

    public void deleteBoardGame(BoardGame bg) {

    }

    public void deleteBoardGameById(Integer id) {

    }

    private ArrayList<BoardGame> createDefaults() {
        //e.g.: contactList.add(new Contact("Malcolm", "Reynolds", "Captain", "Mobile", "123-456-7890", "", "m123.hotmail", "Twitter", "MRey", 1));
        bgList.add(new BoardGame(1, "Test One", "2015", "", "cf.geekdo-images.com/images/pic1104600_t.jpg"));
        bgList.add(new BoardGame(2, "Test Two", "2008", "", "cf.geekdo-images.com/images/pic719935_t.jpg"));
        BoardGame game1 = new BoardGame(3, "Test Three", "2003", "", "cf.geekdo-images.com/images/pic1324609_t.jpg");
        game1.setRating(7.1234);
        bgList.add(game1);
        Log.i("Info", "Added default board games to list.");
        return bgList;
    }

    public ArrayList<BoardGame> getBgList() {
        return bgList;
    }

    public void setBgList(ArrayList<BoardGame> bgList) {
        this.bgList = bgList;
    }

    public BoardGame getBoardGameById(long id) {
        for (BoardGame game : getBgList()) {
            if (game.getObjectId() == id) {
                Log.i("BoardGameManager", "Found game ID: " + game.getObjectId() + "\n");
                return game;
            }
        }
        return null;
    }

}
