package com.brickedphoneclub.boardgamecollectionmanager;

import android.graphics.Bitmap;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * Created by Giovanni Galasso on 4/18/2015.
 */

@Root
public class SimpleBoardGame {

    //https://boardgamegeek.com/xmlapi2/collection?username=brickedphoneclub
    @Element
    private String name;
    @Attribute
    private long objectId;
    @Element
    private String yearPublished;

    //private int numplays
    //private Map<String, String> status = new HashMap<String, String>() ;
    //I don't think we need these right now, possibly later.

    @Element
    private String thumbnail_url;
    @Element
    private String image_url;
    @Element
    private int rank;
    @Element
    private float rating;
    @Element
    private Bitmap thumbnail_image;
    @Element
    private Bitmap coverart_image;

    //Board Game Details Fields
    //https://boardgamegeek.com/xmlapi2/thing?id=31260
    @Element
    private String description;
    @Element
    private int minPlayers;
    @Element
    private int maxPlayers;
    @Element
    private int playingTime;
    @Element
    private int minPlayTime;
    @Element
    private int maxPlayTime;
    @Element
    private int minAge;
    @Element
    private String[] boardGameCategory;
    @Element
    private String[] boardGameMechanic;

    public SimpleBoardGame() {
        super();
    }

    public SimpleBoardGame(long objectId, String name) {
        this.objectId = objectId;
        this.name = name;
    }

    public SimpleBoardGame(long objectId, String name, String yearPublished) {
        this.objectId = objectId;
        this.name = name;
        this.yearPublished = yearPublished;
        //this.image_url = image_url;
        //this.thumbnail_url = thumbnail_url;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public String getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(String yearPublished) {
        this.yearPublished = yearPublished;
    }

    public String getThumbnail_URL() {
        return thumbnail_url;
    }

    public void setThumbnail_URL(String thumbnail_url) {
        this.thumbnail_url = new String(thumbnail_url);
    }

    public String getLargeImage_URL() {
        return image_url;
    }

    public void setLargeImage_URL(String image_url) {
        this.image_url = new String(image_url);
    }

    public Bitmap getThumbnail() {
        return thumbnail_image;
    }

    public void setThumbnail(Bitmap bitmap) {
        this.thumbnail_image = bitmap;
    }

    public Bitmap getLargeImage() {
        return coverart_image;
    }

    public void setLargeImage(Bitmap bitmap) {
        this.coverart_image = bitmap;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getPlayingTime() {
        return playingTime;
    }

    public void setPlayingTime(int playingTime) {
        this.playingTime = playingTime;
    }

    public int getMinPlayTime() {
        return minPlayTime;
    }

    public void setMinPlayTime(int minPlayTime) {
        this.minPlayTime = minPlayTime;
    }

    public int getMaxPlayTime() {
        return maxPlayTime;
    }

    public void setMaxPlayTime(int maxPlayTime) {
        this.maxPlayTime = maxPlayTime;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public String[] getBoardGameCategory() {
        if(boardGameCategory != null) {
            return Arrays.copyOf(boardGameCategory, boardGameCategory.length);
        }
        return null;
    }

    public void setBoardGameCategory(String[] boardGameCategory) {
        this.boardGameCategory = Arrays.copyOf(boardGameCategory, boardGameCategory.length);
    }

    public String[] getBoardGameMechanic() {
        if(boardGameMechanic != null) {
            return Arrays.copyOf(boardGameMechanic, boardGameMechanic.length);
        }
        return null;
    }

    public void setBoardGameMechanic(String[] boardGameMechanic) {
        this.boardGameMechanic = Arrays.copyOf(boardGameMechanic, boardGameMechanic.length);
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    //Return the double rating value as a string for printing out and added to text fields.
    public String getRatingToString() {
        //GAG: Formatted number to one decimal places so it looks better.
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(1);
        return df.format(rating);
        //return Float.toString(rating);
    }

    //Return the range of number of players as a string. If the game only can be played with a specific number
    //then return just that number.
    public String getPlayerRange() {
        if(minPlayers == maxPlayers) {
            return Integer.toString(minPlayers);
        } else {
            return Integer.toString(minPlayers) + " to " + Integer.toString(maxPlayers);
        }
    }

    //Return the play time range based on the min and max play time.
    public String getPlayTimeRangeToString() {
        if (minPlayTime == maxPlayTime) {
            return Integer.toString(minPlayTime) + " minutes";
        } else {
            return Integer.toString(minPlayTime) + " to " + Integer.toString(maxPlayTime) + " minutes";
        }
    }

    //Get the age group as a string.
    public String getAgeGroupToString() {
        return minAge + "+";
    }

    //Get a string of the board game category array
    public String getCategoryToString() {
        String category = "";
        //Check for null otherwise return the empty string
        if (boardGameCategory != null) {
            for (String cat : boardGameCategory) {
                category += cat + "\n";
            }
        }
        return category;
    }



    //Get a string of the board game mechanics array
    public String getMechanicsToString() {
        String mechanics = "";
        //Check for null otherwise return the empty string
        if (boardGameMechanic != null) {
            for (String mec : boardGameMechanic) {
                mechanics += mec + "\n";
            }
        }
        return mechanics;
    }

}
