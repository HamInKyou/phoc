package com.example.phoc.UserFeedActivity;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class UserFeedItem {
    public String title;
    public String comment;
    public String date;
    public String exifJsonString;
    public String imgUri;
    public int phocNum;
    public String postId;
    public boolean isPhoccedFlag = false;

    public UserFeedItem(String json, String id) {
        JsonElement ele = new JsonParser().parse(json);
        JsonObject obj = ele.getAsJsonObject();

        this.title = obj.get("theme").getAsString();
        this.comment = obj.get("content").getAsString();
        this.date = obj.get("createdAt").getAsString().split(" ",0)[0];
        this.exifJsonString = obj.get("camera").getAsString();
        this.imgUri = obj.get("img").getAsString();
        this.phocNum = obj.get("num_phoc").getAsInt();
        this.postId = id;
    }

    public String getExifJsonString() { return exifJsonString; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
