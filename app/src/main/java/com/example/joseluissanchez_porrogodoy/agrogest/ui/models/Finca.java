package com.example.joseluissanchez_porrogodoy.agrogest.ui.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by joseluissanchez-porrogodoy on 21/11/2016.
 */
@IgnoreExtraProperties
public class Finca {
    public String uid;
    public String name;


    public Finca(){

    }

    public Finca( String name, String uid) {
        this.uid = uid;
        this.name = name;

    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("name", name);
        return result;
    }
}
