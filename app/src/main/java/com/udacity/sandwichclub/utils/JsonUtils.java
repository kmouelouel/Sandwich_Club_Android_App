package com.udacity.sandwichclub.utils;

import android.util.Log;
import android.widget.Toast;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String KEY_Name = "name";
    private static final String KEY_MAIN_NAME = "mainName";
    private static final String KEY_KNOWN_AS = "alsoKnownAs";
    private static final String KEY_PLACE_ORIGIN = "placeOfOrigin";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_INGREDIENTS = "ingredients";
    //Global Variables:CONSTANTS
    private static String TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) {

        String mainName = null;
        String placeOfOrigin = null;
        String description = null;
        String image = null;
        List<String> alsoKnownAs = new ArrayList<>();
        List<String> ingredients = new ArrayList<>();
        JSONObject JsonObjectSandwich = null;

        try {
            JsonObjectSandwich = new JSONObject(json);
            // name node is JSON Object
            JSONObject JsonObjectName = JsonObjectSandwich.getJSONObject(KEY_Name);
            mainName = JsonObjectName.getString(KEY_MAIN_NAME);
            // Getting JSON Array node of AlsoKnownAs
            JSONArray jsonArrayAlsoKnownAS = JsonObjectName.getJSONArray(KEY_KNOWN_AS);
            // looping through All AlsoKnownAs' Array array by calling the convertJsonArrayToList method
            alsoKnownAs = convertJsonArrayToList(jsonArrayAlsoKnownAS);
            placeOfOrigin = JsonObjectSandwich.getString(KEY_PLACE_ORIGIN);
            description = JsonObjectSandwich.getString(KEY_DESCRIPTION);
            image = JsonObjectSandwich.optString(KEY_IMAGE);
            // Getting JSON Array node of Ingredients
            JSONArray jsonArrayIngredients = JsonObjectSandwich.getJSONArray(KEY_INGREDIENTS);
            // looping through All Ingredients' array by calling the convertJsonArrayToList method
            ingredients = convertJsonArrayToList(jsonArrayIngredients);

        } catch (JSONException e) {
            Log.e(TAG, "Problem with parse", e);
            return null;
        }

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }

    private static List<String> convertJsonArrayToList(JSONArray jsonArray) {
        List<String> list = new ArrayList<>(0);
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.optString(i));
            }
        }
        return list;
    }

}
