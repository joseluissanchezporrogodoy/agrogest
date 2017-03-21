package com.example.joseluissanchez_porrogodoy.agrogest.ui.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by joseluissanchez-porrogodoy on 24/11/2016.
 */

public class Cultivo {
    public String uid;
    public String uidParcela;
    public String name;
    public String area;

    public Cultivo() {
    }

    public Cultivo(String uid, String name,String uidParcela, String area) {
        this.uid = uid;
        this.name = name;
        this.area = String.valueOf(area);
        this.uidParcela=uidParcela;
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("uidParcela", uidParcela);
        result.put("name", name);
        result.put("area", area);
        return result;
    }
}
