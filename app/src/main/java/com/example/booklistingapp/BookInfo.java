package com.example.booklistingapp;

/**
 * Created by root on 5/13/17.
 */

public class BookInfo {

    private String title;
    private String author;
    private double rating;
    private String retialPrice;

    public BookInfo(String title, String author , double rating, String retialPrice) {
        this.title = title;
        this.author = author;
        this.rating = rating;
        this.retialPrice = retialPrice;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getRating() {
        return rating;
    }

    public String getRetialPrice() {
        return retialPrice;
    }

}
