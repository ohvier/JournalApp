package com.example.otuyishime.journalapp.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(tableName = "dairy")
public class DairyEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String entry_title;
    private Date entry_date;
    private String entry_body;

    @Ignore
    public DairyEntry() {
    }

    @Ignore
    public DairyEntry(String entry_title, Date entry_date, String entry_body) {

        this.entry_title = entry_title;
        this.entry_date = entry_date;
        this.entry_body = entry_body;
    }


    public DairyEntry(int id, String entry_title, Date entry_date, String entry_body) {
        this.id = id;
        this.entry_title = entry_title;
        this.entry_date = entry_date;
        this.entry_body = entry_body;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getEntry_title() {
        return entry_title;
    }

    public void setEntry_title(String entry_title) {
        this.entry_title = entry_title;
    }

    public Date getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(Date entry_date) {
        this.entry_date = entry_date;
    }

    public String getEntry_body() {
        return entry_body;
    }

    public void setEntry_body(String entry_body) {
        this.entry_body = entry_body;
    }

    public static List<DairyEntry> getDairyList() {

        List<DairyEntry> dairy = new ArrayList<>();


        String[] titles = {"Dairy 1", "Dairy 2", "Dairy 3", "Dairy 4"};
        Date[] date = {new Date(), new Date(), new Date(), new Date()};
        String[] body = {
                "Skipping duplicate class check due to unrecognized classloader",
                "Skipping duplicate class check due to unrecognized classloader",
                "Skipping duplicate class check due to unrecognized classloader",
                "Skipping duplicate class check due to unrecognized classloader"
        };

        for (int i = 0; i < titles.length; i++) {

            DairyEntry entry = new DairyEntry();
            entry.setEntry_title(titles[i]);
            entry.setEntry_date(date[i]);
            entry.setEntry_body(body[i]);
            dairy.add(entry);
        }

        return dairy;
    }
}
