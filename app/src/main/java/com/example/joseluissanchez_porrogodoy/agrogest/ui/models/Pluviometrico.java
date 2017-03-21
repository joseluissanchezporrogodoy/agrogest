package com.example.joseluissanchez_porrogodoy.agrogest.ui.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by joseluissanchez-porrogodoy on 09/12/2016.
 */

public class Pluviometrico {
    public String uid;
    public String uidCultivo;
    public String date;
    public String data;

    public Pluviometrico() {
    }

    public Pluviometrico(String uid, String uidCultivo, String date, String data) {
        this.uid = uid;
        this.uidCultivo = uidCultivo;
        this.date = date;
        this.data = data;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("uidCultivo", uidCultivo);
        result.put("date", date);
        result.put("data",data);

        return result;
    }
}
