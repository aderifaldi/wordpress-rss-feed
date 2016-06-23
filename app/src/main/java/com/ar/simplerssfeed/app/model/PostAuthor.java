package com.ar.simplerssfeed.app.model;

import java.io.Serializable;

/**
 * Created by aderifaldi on 26/04/2016.
 */
public class PostAuthor implements Serializable {

    private String ID;
    private String name;
    private String avatar_URL;

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getAvatar_URL() {
        return avatar_URL;
    }
}
