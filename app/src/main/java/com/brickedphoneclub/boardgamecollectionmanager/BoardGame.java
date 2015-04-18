package com.brickedphoneclub.boardgamecollectionmanager;

/**
 * Created by Giovanni Galasso on 4/18/2015.
 */

public class BoardGame {

    private String firstName;
    private String lastName;
    private String contactTitle;
    private String phoneType;
    private String phoneNumber;
    private String emailType;
    private String emailAdd;
    private String socialType;
    private String social;
    private int id;

    public BoardGame(String firstName, String lastName, String contactTitle, String phoneType, String phoneNumber, String emailType, String emailAdd, String socialType, String social ,int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactTitle = contactTitle;
        this.phoneType = phoneType;
        this.phoneNumber = phoneNumber;
        this.emailType = emailType;
        this.emailAdd = emailAdd;
        this.socialType = socialType;
        this.social = social;
        this.id = id;
    }


}
