package com.pathivu.Sections.Feeds.Model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by vijayarajsekar on 18/1/16.
 */
public class TNews {
    private String title;
    private String description;
    private String link;
    private String parent;
    private String date;
    private Date date2;

    public TNews () {
        title = "null";
        description = "null";
        link = "null";
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDate(String date) {

        String[] mTemp=date.split("\\+");

        this.date = mTemp[0];
        if (date == null) {
            this.date = "";
        }
        DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        try {
            this.date2 = formatter.parse(date);
        } catch (ParseException e) {
            this.date2 = null;
        }
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getDate() {
        return date;
    }

    public Date getDate2() {
        return date2;
    }

    public String getParent() {return parent;}
}
