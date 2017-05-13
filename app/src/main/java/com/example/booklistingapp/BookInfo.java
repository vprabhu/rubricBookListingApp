package com.example.booklistingapp;

/**
 * Created by root on 5/13/17.
 */

public class BookInfo {

    private String title;
    private String author;
    private int pageCount;
    private double rating;
    private String retialPrice;
    private String ImageLinks;

    public BookInfo(String title, String author, int pageCount, double rating, String retialPrice, String imageLinks) {
        this.title = title;
        this.author = author;
        this.pageCount = pageCount;
        this.rating = rating;
        this.retialPrice = retialPrice;
        ImageLinks = imageLinks;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPageCount() {
        return pageCount;
    }

    public double getRating() {
        return rating;
    }

    public String getRetialPrice() {
        return retialPrice;
    }

    public String getImageLinks() {
        return ImageLinks;
    }
}
