package com.vad.volsuproject.websector;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notificate {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("body")
    @Expose
    private Body body;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }
}
