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
        //GAG - 05/02/15
        //Added in check if game is already in the collection it doesn't get added again.
        //This is probably not very good performance, I'm guessing some better way to handle this.
        if(getBoardGameById(bg.getObjectId()) == null) {
            bgList.add(bg);
        }
    }

    public void deleteBoardGame(BoardGame bg) {

    }

    public void deleteBoardGameById(Integer id) {

    }

    private ArrayList<BoardGame> createDefaults() {
        //e.g.: contactList.add(new Contact("Malcolm", "Reynolds", "Captain", "Mobile", "123-456-7890", "", "m123.hotmail", "Twitter", "MRey", 1));
        bgList.add(new BoardGame(1, "One", "2015", "", "//cf.geekdo-images.com/images/pic1104600_t.jpg"));
        bgList.add(new BoardGame(2, "Two", "2008", "", "//cf.geekdo-images.com/images/pic719935_t.jpg"));
        BoardGame game1 = new BoardGame(3, "Four", "2003", "", "//cf.geekdo-images.com/images/pic1324609_t.jpg");
        //Mockup Info to test details screen since we don't have XML working yet.
        game1.setRating(7.1234f);
        game1.setMinPlayers(2);
        game1.setMaxPlayers(4);
        game1.setMinPlayTime(60);
        game1.setMaxPlayTime(120);
        game1.setMinAge(10);
        String[] categories = {"Bluffing","Card Game", "Science Fiction", "Science Fiction", "Science Fiction", "Science Fiction", "Science Fiction", "Science Fiction"};
        game1.setBoardGameCategory(categories);
        String[] mechanics = {"Hand Management","Secret Unit Deployment", "Variable Player Powers"};
        game1.setBoardGameMechanic(mechanics);

        bgList.add(game1);
        Log.i("Info", "Added default board games to list.");
        return bgList;
    }

    public ArrayList<BoardGame> getBgList() {
        return bgList;
    }

    public int getCollectionSize() {
        return this.bgList.size();
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

    /*
    public String[] getNumOfPlayerFilterOptions() {
        String[] numOfPlayers = {"1","2","3","4","5","6","7","8","9","10+"};
        return numOfPlayers;
    }
    */
}
