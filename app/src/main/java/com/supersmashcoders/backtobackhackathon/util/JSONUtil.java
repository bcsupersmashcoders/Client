package com.supersmashcoders.backtobackhackathon.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtil {
    public static JSONArray getArrayOrEmpty(JSONObject source, String arrayName) {
        try {
            JSONArray array = source.getJSONArray(arrayName);
            return array;
        } catch (JSONException e) {
            Log.e("PARSE ERROR", "Failed to parse array " + arrayName + " in json " + source.toString(), e);
            return new JSONArray();
        }
    }
}
