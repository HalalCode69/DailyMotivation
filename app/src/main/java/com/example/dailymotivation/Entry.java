package com.example.dailymotivation;

public class Entry {
    private int id;
    private String title;
    private String text;

    public Entry(int id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getText() { return text; }
}