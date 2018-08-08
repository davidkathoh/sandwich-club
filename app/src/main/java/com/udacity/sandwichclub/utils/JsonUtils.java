package com.udacity.sandwichclub.utils;


import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class JsonUtils {


    public static Sandwich parseSandwichJson(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        JSONObject name = object.getJSONObject("name");
        String mainName = name.getString("mainName");
        List<String> alsoKnownAs = Arrays.asList(name.getJSONArray("alsoKnownAs").toString().replace("[","").replace("]","").replace("\"","").split(","));
        String placeOfOrigin = object.getString("placeOfOrigin");
        String description = object.getString("description");
        String imageUrl = object.getString("image");
        List<String> ingredients = Arrays.asList(object.getJSONArray("ingredients").toString().replace("[","").replace("]","").replace("\"","").split(","));
        return new Sandwich(mainName,alsoKnownAs,placeOfOrigin,description,imageUrl,ingredients);
    }



}
