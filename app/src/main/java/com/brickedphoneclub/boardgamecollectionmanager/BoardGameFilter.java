package com.brickedphoneclub.boardgamecollectionmanager;

import android.content.Context;
import android.util.Log;

import com.brickedphoneclub.boardgamecollectionmanager.BoardGameManager;
import com.brickedphoneclub.boardgamecollectionmanager.SimpleBoardGame;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Giovanni Galasso on 4/28/2015.
 * Renamed to SimpleBoardGameFilter due to some conflict Vandana was having.
 */
public class BoardGameFilter {
    private static BoardGameFilter ourInstance = null;

    private String numPlayers, playTime, ageGroup, mechanic, category, rating, gamName;
    private boolean activeFilter;
    private Context context;

    private ArrayList<SimpleBoardGame> mainList = BoardGameManager.getInstance(context).getBgList();
    private ArrayList<SimpleBoardGame> filterList = new ArrayList<>();

    public static BoardGameFilter getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new BoardGameFilter(context);
        }
        return ourInstance;
    }

    private BoardGameFilter(Context context) {
        this.context = context;
        this.gamName = "";
        this.numPlayers = "";
        this.playTime = "";
        this.ageGroup = "";
        this.mechanic = "";
        this.category = "";
        this.rating = "";
        this.activeFilter = false;

    }

    public ArrayList<SimpleBoardGame> getFilterList() {
        return filterList;
    }

    public void setFilterList(ArrayList<SimpleBoardGame> filterList) {
        this.filterList = filterList;
    }

    public ArrayList<SimpleBoardGame> getMainList() {
        return mainList;
    }

    public void setMainList(ArrayList<SimpleBoardGame> mainList) {
        this.mainList = mainList;
    }

    public boolean isActiveFilter() {
        return activeFilter;
    }

    public void setActiveFilter(boolean activeFilter) {
        this.activeFilter = activeFilter;
    }

    public String getGamName() {
        return gamName;
    }

    public void setGamName(String gamName) {
        this.gamName = gamName;
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

        if(numPlayers != null && !numPlayers.isEmpty()) {
            activeFilter = true;
            return true;
        } else if (playTime != null && !playTime.isEmpty()) {
            activeFilter = true;
            return true;
        } else if (ageGroup != null && !ageGroup.isEmpty()) {
            activeFilter = true;
            return true;
        } else if (mechanic != null && !mechanic.isEmpty()) {
            activeFilter = true;
            return true;
        } else if (category != null && !category.isEmpty()) {
            activeFilter = true;
            return true;
        } else if (rating != null && !rating.isEmpty()) {
            activeFilter = true;
            return true;
        } else if (gamName != null && !gamName.isEmpty()) {
            activeFilter = true;
            return true;
        }else {
            activeFilter = false;
            return false;
        }
    }

    public ArrayList<SimpleBoardGame> applyFilters() {
        filterList.clear();
        ArrayList<SimpleBoardGame> tempList = mainList;

        if(!gamName.equals("")) {
            tempList = searchByGamName(tempList);
            Log.i("FILTER SIZE", "Size:" + tempList.size());
        }
        if(!numPlayers.equals("")) {
            tempList = filterByPlayer(tempList);
            Log.i("FILTER SIZE", "Size:" + tempList.size());
        }
        if(!playTime.equals("")) {
            tempList = filterByTime(tempList);
            Log.i("FILTER SIZE", "Size:" + tempList.size());
        }
        if(!ageGroup.equals("")) {
            tempList = filterByAge(tempList);
            Log.i("FILTER SIZE", "Size:" + tempList.size());
        }
        if(!mechanic.equals("")) {
            tempList = filterByMechanic(tempList);
            Log.i("FILTER SIZE", "Size:" + tempList.size());
        }
        if(!category.equals("")) {
            tempList = filterByCategory(tempList);
            Log.i("FILTER SIZE", "Size:" + tempList.size());
        }
        if(!rating.equals("")) {
            tempList = filterByRating(tempList);
            Log.i("FILTER SIZE", "Size:" + tempList.size());
        }
        filterList = tempList;
        return filterList;
    }

    public String toString() {
        return "Game: " + gamName + "\n" +
                "Players: " + numPlayers + "\n" +
                "Time: " + playTime + "\n" +
                "Age: " + ageGroup + "\n" +
                "Category: " + category + "\n" +
                "Mechanic: " + mechanic + "\n" +
                "Rating: " + rating + "\n";

    }

    // Search by gameName
    public ArrayList<SimpleBoardGame> searchByGamName(ArrayList<SimpleBoardGame> list) {
        ArrayList<SimpleBoardGame> tempList = new ArrayList<>();
        for (SimpleBoardGame game: list) {
            String gName = game.getName();
            if (gName != null) {
                //Debug print out
                Log.i("Search Game", "List Item Name: " + gName + " Target Name: " + gamName);
                if (gName.toLowerCase().contains(gamName.toLowerCase())) {
                    addGameToList(tempList, game);
                }
            }
        }
        return tempList;
    }

    //GAG: Filter by player count
    public ArrayList<SimpleBoardGame> filterByPlayer(ArrayList<SimpleBoardGame> list) {
        //int playerCount = Integer.parseInt(numPlayers);
        int playerCount = extractInt(numPlayers);
        ArrayList<SimpleBoardGame> tempList = new ArrayList<>();
        for (SimpleBoardGame game: list) {
            //Debug print out
            Log.i("FILTER PLAYERS", "Game: " + game.getName() + " Min players: " + game.getMinPlayers() +
                    " Max players: " + game.getMaxPlayers() + " Target players: " + playerCount);
            //TODO: Is there some case for 10+ that would differ from the below?
            if(game.getMinPlayers() <= playerCount && game.getMaxPlayers() >= playerCount) {
                addGameToList(tempList, game);
            }
        }
        return tempList;
    }

    //GAG: Filter by play time
    public ArrayList<SimpleBoardGame> filterByTime(ArrayList<SimpleBoardGame> list) {
        int time = extractInt(playTime);
        ArrayList<SimpleBoardGame> tempList = new ArrayList<>();
        for (SimpleBoardGame game: list) {
            //Debug print out
            Log.i("FILTER TIME", "Game: " + game.getName() + " Min Time: " + game.getMinPlayTime() +
                    " Max Time: " + game.getMaxPlayTime() + " Target Time: " + time);
            if((game.getMinPlayTime() <= time && game.getMaxPlayTime() >= time) || game.getMaxPlayTime() <= time) {
                addGameToList(tempList, game);
            }
        }
        return tempList;
    }

    public ArrayList<SimpleBoardGame> filterByAge(ArrayList<SimpleBoardGame> list) {
        int age = extractInt(ageGroup);
        ArrayList<SimpleBoardGame> tempList = new ArrayList<>();
        for (SimpleBoardGame game: list) {
            //Debug print out
            Log.i("FILTER Age", "Game: " + game.getName() + " Min Age: " + game.getMinAge() +
                    " Target Age: " + age);
            if(age >= game.getMinAge()) {
                addGameToList(tempList, game);
            }
        }
        return tempList;
    }

    public ArrayList<SimpleBoardGame> filterByMechanic(ArrayList<SimpleBoardGame> list) {
        ArrayList<SimpleBoardGame> tempList = new ArrayList<>();
        for (SimpleBoardGame game: list) {
            if (game.getBoardGameMechanic() != null) {
                for (String mec : game.getBoardGameMechanic()) {
                    //Debug print out
                    Log.i("FILTER Mechanic", "Game: " + game.getName() + " Mechanic: " + mec + " Target Mechanic: " + mechanic);
                    if (mec.equals(mechanic)) {
                        addGameToList(tempList, game);
                    }
                }
            }
        }
        return tempList;
    }

    public ArrayList<SimpleBoardGame> filterByCategory(ArrayList<SimpleBoardGame> list) {
        ArrayList<SimpleBoardGame> tempList = new ArrayList<>();
        for (SimpleBoardGame game: list) {
            if (game.getBoardGameCategory() != null) {
                for (String cat : game.getBoardGameCategory()) {
                    //Debug print out
                    Log.i("FILTER Category", "Game: " + game.getName() + " Category: " + cat + " Target Category: " + category);
                    if (cat.equals(category)) {
                        addGameToList(tempList, game);
                    }
                }
            }
        }
        return tempList;
    }

    public ArrayList<SimpleBoardGame> filterByRating(ArrayList<SimpleBoardGame> list) {
        ArrayList<SimpleBoardGame> tempList = new ArrayList<>();
        Double targetRating = extractDouble(rating);
        for (SimpleBoardGame game: list) {
            //Debug print out
            Log.i("FILTER Rating", "Game: " + game.getName() + " Rating: " + game.getRating() + " Target Rating: " + targetRating);
            if (game.getRating() >= targetRating) {
                addGameToList(tempList, game);
            }
        }
        return tempList;
    }

    public void addGameToList(ArrayList<SimpleBoardGame> list, SimpleBoardGame game) {
        if(!list.contains(game)) {
            list.add(game);
        }
    }

    public int extractInt(String s) {
        //Create a regex matcher for only digits
        String pattern = "(\\d+)";
        //Create a Pattern object
        Pattern r = Pattern.compile(pattern);
        //Look for a match
        Matcher m = r.matcher(s);
        if (m.find( )) {
            Log.i("REGEX", "Found value: " + m.group(0));
             return Integer.parseInt(m.group(0));
        } else {
            Log.i("REGEX", "No match found.");
            return 0;
        }
    }

    public double extractDouble(String s) {
        //Create a regex matcher for only digits
        String pattern = "(\\d+)";
        //Create a Pattern object
        Pattern r = Pattern.compile(pattern);
        //Look for a match
        Matcher m = r.matcher(s);
        if (m.find( )) {
            Log.i("REGEX", "Found value: " + m.group(0));
            return Double.parseDouble(m.group(0));
        } else {
            Log.i("REGEX", "No match found.");
            return 0;
        }

}

}


