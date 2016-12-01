package com.example.joseluissanchez_porrogodoy.agrogest.ui.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by joseluissanchez-porrogodoy on 30/11/2016.
 */

public class Fenologico {
    public String uid;
    public String uidCultivo;
    public String date;
    public String state;
    public String stateString;
    public String imageUrl;

    public Fenologico() {
    }

    public Fenologico(String uid, String uidCultivo, String date, String state,String stateString, String imageUrl) {
        this.uid = uid;
        this.date = String.valueOf(date);
        this.uidCultivo=uidCultivo;
        this.state = state;
        this.imageUrl = imageUrl;
        this.stateString = stateString;
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("uidCultivo", uidCultivo);
        result.put("date", date);
        result.put("state",state);
        result.put("stateString",stateString);
        result.put("imageUrl",imageUrl);
        return result;
    }
}
