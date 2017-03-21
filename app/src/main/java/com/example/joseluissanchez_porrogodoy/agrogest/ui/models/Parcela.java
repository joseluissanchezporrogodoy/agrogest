package com.example.joseluissanchez_porrogodoy.agrogest.ui.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by joseluissanchez-porrogodoy on 21/11/2016.
 */

public class Parcela {
    public String uid;
    public String uidFinca;
    public String name;
    public String area;

    public Parcela() {
    }

    public Parcela(String uid, String name,String uidFinca, String area) {
        this.uid = uid;
        this.name = name;
        this.area = String.valueOf(area);
        this.uidFinca=uidFinca;
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("uidFinca", uidFinca);
        result.put("name", name);
        result.put("area", area);
        return result;
    }
}
