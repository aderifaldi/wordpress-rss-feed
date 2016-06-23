package com.ar.simplerssfeed.app.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by aderifaldi on 26/04/2016.
 */
public class Post implements Serializable {

    private String ID;
    private String site_ID;
    private PostAuthor author;
    private Date date;
    private String title;
    private String content;

    public String getID() {
        return ID;
    }

    public String getSite_ID() {
        return site_ID;
    }

    public PostAuthor getAuthor() {
        return author;
    }

    public Date getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
