package com.supersmashcoders.backtobackhackathon.proxy;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.supersmashcoders.backtobackhackathon.models.EventModel;
import com.supersmashcoders.backtobackhackathon.models.UserEntity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by LuisRoberto on 6/13/2015.
 */
public class UserProxy {
    public void validateUser(final Context context, final String username, final String password, final RequestListener<UserEntity> listener) throws JSONException {
        final String url = "http://localhost:8080/_ah/api/backtoback/v1/users/login";

        JSONObject userPassword = new JSONObject();
        userPassword.put("username", username);
        userPassword.put("password", password);

        // Request a string response from the provided URL.
        JsonObjectRequest userRequest = new JsonObjectRequest(url, userPassword, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.print(response.toString());


                //UserEntity user = UserEntity.of(response.getLong("id"), username);
                //listener.onComplete(user);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError();
                Log.e("API FAIL", "Error calling API " + url);
            }
        });

        // Add the request to the RequestQueue.
        ApplicationRequestQueue.INSTANCE.addToRequestQueue(context, userRequest);
    }
}
