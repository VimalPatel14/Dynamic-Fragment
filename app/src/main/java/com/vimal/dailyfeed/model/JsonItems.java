/**
 * Created by Vimal on July-2021.
 */
package com.vimal.dailyfeed.model;

import java.io.Serializable;

public class JsonItems implements Serializable {

    private String postId;
    private Integer image;

    public JsonItems(String postId, Integer image) {
        this.postId = postId;
        this.image = image;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

}



