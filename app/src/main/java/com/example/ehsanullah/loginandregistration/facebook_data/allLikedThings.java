package com.example.ehsanullah.loginandregistration.facebook_data;

import java.util.ArrayList;

/**
 * Created by Ehsan Ullah on 7/13/2017.
 */

public class allLikedThings {

    String name,category;

    public allLikedThings() {
    }

    public allLikedThings(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
