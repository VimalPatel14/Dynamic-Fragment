/**
 * Created by Vimal on July-2021.
 */
package com.vimal.dailyfeed.model;

import java.io.Serializable;

public class JsonMusic implements Serializable {

    private String postId;
    private String postTitle;

    public JsonMusic(String postId, String postTitle) {
        super();
        this.postId = postId;
        this.postTitle = postTitle;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

}



