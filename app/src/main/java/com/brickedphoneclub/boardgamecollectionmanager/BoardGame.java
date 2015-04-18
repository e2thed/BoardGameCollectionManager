package com.brickedphoneclub.boardgamecollectionmanager;

/**
 * Created by Giovanni Galasso on 4/18/2015.
 */

public class BoardGame {

    //https://boardgamegeek.com/xmlapi2/collection?username=brickedphoneclub
    private String name;
    private long objectid;
    private int yearpublished;
    //private int numplays
    //private Map<String, String> status = new HashMap<String, String>() ;
    //I don't think we need these right now, possibly later.
    private String thumbnail;
    private String image;


    //Board Game Details Fields
    //https://boardgamegeek.com/xmlapi2/thing?id=31260
    private String description;
    private int minplayers;
    private int maxplayers;
    private int playingtime;
    private int minplaytime;
    private int maxplaytime;
    private int minage;
    private String[] boardgamecategory;
    private String[] boardgamemechanic;

    public BoardGame(long objectid, String name) {
        this.objectid = objectid;
        this.name = name;
    }

    public BoardGame(long objectid, String name, int yearpublished, String image, String thumbnail) {
        this.objectid = objectid;
        this.name = name;
        this.yearpublished = yearpublished;
        this.image = image;
        this.thumbnail = thumbnail;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getObjectid() {
        return objectid;
    }

    public void setObjectid(long objectid) {
        this.objectid = objectid;
    }

    public int getYearpublished() {
        return yearpublished;
    }

    public void setYearpublished(int yearpublished) {
        this.yearpublished = yearpublished;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMinplayers() {
        return minplayers;
    }

    public void setMinplayers(int minplayers) {
        this.minplayers = minplayers;
    }

    public int getMaxplayers() {
        return maxplayers;
    }

    public void setMaxplayers(int maxplayers) {
        this.maxplayers = maxplayers;
    }

    public int getPlayingtime() {
        return playingtime;
    }

    public void setPlayingtime(int playingtime) {
        this.playingtime = playingtime;
    }

    public int getMinplaytime() {
        return minplaytime;
    }

    public void setMinplaytime(int minplaytime) {
        this.minplaytime = minplaytime;
    }

    public int getMaxplaytime() {
        return maxplaytime;
    }

    public void setMaxplaytime(int maxplaytime) {
        this.maxplaytime = maxplaytime;
    }

    public int getMinage() {
        return minage;
    }

    public void setMinage(int minage) {
        this.minage = minage;
    }

    public String[] getBoardgamecategory() {
        return boardgamecategory;
    }

    public void setBoardgamecategory(String[] boardgamecategory) {
        this.boardgamecategory = boardgamecategory;
    }

    public String[] getBoardgamemechanic() {
        return boardgamemechanic;
    }

    public void setBoardgamemechanic(String[] boardgamemechanic) {
        this.boardgamemechanic = boardgamemechanic;
    }
}
