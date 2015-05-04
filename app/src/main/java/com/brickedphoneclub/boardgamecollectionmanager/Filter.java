package com.brickedphoneclub.boardgamecollectionmanager;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Giovanni Galasso on 4/28/2015.
 */
public class Filter {
    private static Filter ourInstance = null;

    private String numPlayers, playTime, ageGroup, mechanic, category, rating;
    private boolean activeFilter;
    private Context context;

    private ArrayList<BoardGame> mainList = BoardGameManager.getInstance(context).getBgList();
    private ArrayList<BoardGame> filterList = new ArrayList<>();

    public static Filter getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new Filter(context);
        }
        return ourInstance;
    }

    private Filter(Context context) {
        this.numPlayers = "";
        this.playTime = "";
        this.ageGroup = "";
        this.mechanic = "";
        this.category = "";
        this.rating = "";
        this.activeFilter = false;
    }

    public ArrayList<BoardGame> getFilterList() {
        return filterList;
    }

    public void setFilterList(ArrayList<BoardGame> filterList) {
        this.filterList = filterList;
    }

    public ArrayList<BoardGame> getMainList() {
        return mainList;
    }

    public void setMainList(ArrayList<BoardGame> mainList) {
        this.mainList = mainList;
    }

    public boolean isActiveFilter() {
        return activeFilter;
    }

    public void setActiveFilter(boolean activeFilter) {
        this.activeFilter = activeFilter;
    }

    public String getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(String numPlayers) {
        this.numPlayers = numPlayers;
    }

    public String getPlayTime() {
        return playTime;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public String getMechanic() {
        return mechanic;
    }

    public void setMechanic(String mechanic) {
        this.mechanic = mechanic;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public boolean checkActiveFilter() {
        if(numPlayers != "") {
            activeFilter = true;
            return activeFilter;
        } else if (playTime != "") {
            activeFilter = true;
            return activeFilter;
        } else if (ageGroup != "") {
            activeFilter = true;
            return activeFilter;
        } else if (mechanic != "") {
            activeFilter = true;
            return activeFilter;
        } else if (category != "") {
            activeFilter = true;
            return activeFilter;
        } else if (rating != "") {
            activeFilter = true;
            return activeFilter;
        } else {
            activeFilter = false;
            return activeFilter;
        }
    }

    public ArrayList<BoardGame> applyFilters() {
        filterList.clear();
        if(numPlayers != "") {
            filterCollectionByPlayer();
            Log.i("FILTER SIZE", "Size:" + filterList.size());
        }

        return filterList;
    }

    public String toString() {
        return "Players: " + numPlayers + "\n" +
               "Time: " + playTime + "\n" +
               "Age: " + ageGroup + "\n" +
               "Category: " + category + "\n" +
               "Mechanic: " + mechanic + "\n" +
               "Rating: " + rating + "\n";
    }

    public ArrayList<BoardGame> filterCollectionByPlayer() {
        int playerCount = Integer.parseInt(numPlayers);
        for (BoardGame game: mainList) {
            //TODO: Need to check for 10+ and filter appropriately
            if(game.getMinPlayers() <= playerCount && game.getMaxPlayers() >= playerCount) {
                addGameToFilterList(game);
            }
        }
        return filterList;
    }

    public void addGameToFilterList(BoardGame game) {
        if(!filterList.contains(game)) {
            filterList.add(game);
        }
    }

}
