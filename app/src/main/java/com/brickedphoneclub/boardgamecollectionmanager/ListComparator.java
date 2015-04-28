package com.brickedphoneclub.boardgamecollectionmanager;

import java.util.Comparator;

/**
 * Created by Vandana on 4/27/2015.
 */
public
class ListComparator implements Comparator<BoardGame> {

    private String orderType;

    public ListComparator(String type) {

        this.orderType = type;

    }

    @Override
    public int compare(BoardGame lhs, BoardGame rhs) {

        int res=0;
        if (orderType.equals("A-Z")) {
            res = (lhs.getName()).compareTo(rhs.getName());
        }
        else if (orderType.equals("Z-A")) {
            res = (rhs.getName()).compareTo(lhs.getName());
        }
        else if (orderType.equals("Highest First")) {
            res = (rhs.getRatingToString()).compareTo(lhs.getRatingToString());
        }
        else if (orderType.equals("Lowest First")) {
            res = (lhs.getRatingToString()).compareTo(rhs.getRatingToString());
        }
        else if (orderType.equals("Latest First")) {
            res = (rhs.getYearPublished()).compareTo(lhs.getYearPublished());
        }
        else if (orderType.equals("Earliest First")) {
            res = (lhs.getYearPublished()).compareTo(rhs.getYearPublished());
        }
        return res;
    }
    //return lhs.getName().compareTo(rhs.getName());

}
