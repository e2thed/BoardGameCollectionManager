package com.brickedphoneclub.boardgamecollectionmanager;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        ArrayList<BoardGame> tempList = mainList;
        if(numPlayers != "") {
            tempList = filterByPlayer(tempList);
            Log.i("FILTER SIZE", "Size:" + tempList.size());
        }
        if(playTime != "") {
            tempList = filterByTime(tempList);
            Log.i("FILTER SIZE", "Size:" + tempList.size());
        }
        if(ageGroup != "") {
            tempList = filterByAge(tempList);
            Log.i("FILTER SIZE", "Size:" + tempList.size());
        }
        if(mechanic != "") {
            tempList = filterByMechanic(tempList);
            Log.i("FILTER SIZE", "Size:" + tempList.size());
        }
        if(category != "") {
            tempList = filterByCategory(tempList);
            Log.i("FILTER SIZE", "Size:" + tempList.size());
        }
        if(rating != "") {
            tempList = filterByRating(tempList);
            Log.i("FILTER SIZE", "Size:" + tempList.size());
        }
        filterList = tempList;
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

    //GAG: Filter by player count
    public ArrayList<BoardGame> filterByPlayer(ArrayList<BoardGame> list) {
        //int playerCount = Integer.parseInt(numPlayers);
        int playerCount = extractInt(numPlayers);
        ArrayList<BoardGame> tempList = new ArrayList<>();
        for (BoardGame game: list) {
            //Debug print out
            Log.i("FILTER PLAYERS", "Game: " + game.getName() + " Min players: " + game.getMinPlayers() +
                    " Max players: " + game.getMaxPlayers() + "Target players: " + playerCount);
            //TODO: Is there some case for 10+ that would differ from the below?
            if(game.getMinPlayers() <= playerCount && game.getMaxPlayers() >= playerCount) {
                addGameToList(tempList, game);
            }
        }
        return tempList;
    }

    //GAG: Filter by play time
    public ArrayList<BoardGame> filterByTime(ArrayList<BoardGame> list) {
        int time = extractInt(playTime);
        ArrayList<BoardGame> tempList = new ArrayList<>();
        for (BoardGame game: list) {
            //Debug print out
            Log.i("FILTER TIME", "Game: " + game.getName() + " Min Time: " + game.getMinPlayTime() +
                    " Max Time: " + game.getMaxPlayTime() + "Target Time: " + time);
            if((game.getMinPlayTime() <= time && game.getMaxPlayTime() >= time) || game.getMaxPlayTime() <= time) {
                addGameToList(tempList, game);
            }
        }
        return tempList;
    }

    public ArrayList<BoardGame> filterByAge(ArrayList<BoardGame> list) {
        int age = extractInt(ageGroup);
        ArrayList<BoardGame> tempList = new ArrayList<>();
        for (BoardGame game: list) {
            //Debug print out
            Log.i("FILTER Age", "Game: " + game.getName() + " Min Age: " + game.getMinAge() +
                    " Target Age: " + age);
            if(age >= game.getMinAge()) {
                addGameToList(tempList, game);
            }
        }
        return tempList;
    }

    public ArrayList<BoardGame> filterByMechanic(ArrayList<BoardGame> list) {
        ArrayList<BoardGame> tempList = new ArrayList<>();
        for (BoardGame game: list) {
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

    public ArrayList<BoardGame> filterByCategory(ArrayList<BoardGame> list) {
        ArrayList<BoardGame> tempList = new ArrayList<>();
        for (BoardGame game: list) {
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

    public ArrayList<BoardGame> filterByRating(ArrayList<BoardGame> list) {
        ArrayList<BoardGame> tempList = new ArrayList<>();
        Double targetRating = extractDouble(rating);
        for (BoardGame game: list) {
            //Debug print out
            Log.i("FILTER Rating", "Game: " + game.getName() + " Rating: " + game.getRating() + " Target Rating: " + targetRating);
            if (game.getRating() >= targetRating) {
                addGameToList(tempList, game);
            }
        }
        return tempList;
    }

    public void addGameToList(ArrayList<BoardGame> list, BoardGame game) {
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


