package com.brickedphoneclub.boardgamecollectionmanager;

import android.content.Context;

/**
 * Created by Giovanni Galasso on 4/28/2015.
 */
public class Filter {
    private static Filter ourInstance = null;

    private String numPlayers, playTime, ageGroup, mechanic, category, rating;
    private Context context;
    private boolean activeFilter;

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

}
