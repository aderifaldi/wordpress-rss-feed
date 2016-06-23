package com.ar.simplerssfeed.app.model;

import java.io.Serializable;

/**
 * Created by aderifaldi on 26/04/2016.
 */
public class ModelWordPressFeed implements Serializable {

    private String found;
    private Post[] posts;

    public String getFound() {
        return found;
    }

    public Post[] getPosts() {
        return posts;
    }
}
