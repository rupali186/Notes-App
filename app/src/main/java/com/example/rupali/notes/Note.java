package com.example.rupali.notes;

/**
 * Created by RUPALI on 20-02-2018.
 */

public class Note {
    private String title;
    private String description;
    private int id;

    public Note(String title, String description) {
        this.title=title;
        this.description=description;
        id=-1;
    }

    public Note(String title, String description, int id) {
        this.title = title;
        this.description=description;
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
