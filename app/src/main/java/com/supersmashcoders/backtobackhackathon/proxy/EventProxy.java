package com.supersmashcoders.backtobackhackathon.proxy;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.supersmashcoders.backtobackhackathon.models.EventModel;
import com.supersmashcoders.backtobackhackathon.models.UserEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventProxy {
    public void getAll(final Context context, final RequestListener<List<EventModel>> listener) {
        final String url = "https://backtoback-01.appspot.com/_ah/api/backtoback/v1/events";

        // Request a string response from the provided URL.
        JsonObjectRequest eventsRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(!response.has("items")) {
                    listener.onComplete(new ArrayList<EventModel>());
                    return;
                }
                try {
                    JSONArray events = response.getJSONArray("items");
                    List<EventModel> eventModelList = new ArrayList<>(events.length());
                    for (int i = 0; i < events.length(); i++) {
                        try {
                            JSONObject jsonObject = events.getJSONObject(i);
                            eventModelList.add(EventModel.of(jsonObject));
                        } catch (JSONException e) {
                            Log.e("JSON PARSE", "Failed to get JSON at position " + i + " from JSON Array " + events.toString());
                            Log.e("JSON PARSE", e.getMessage());
                        }
                    }
                    listener.onComplete(eventModelList);
                } catch (JSONException e) {
                    Log.e("JSON PARSE", "Failed to parse JSON " + response);
                    Log.e("JSON PARSE", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("API FAIL", "Error calling API " + url, error);
                listener.onError();
            }
        });

        // Add the request to the RequestQueue.
        ApplicationRequestQueue.INSTANCE.addToRequestQueue(context, eventsRequest);
    }

    public void get(final Context context, long id, final RequestListener<EventModel> listener) {
        final String url = "https://backtoback-01.appspot.com/_ah/api/backtoback/v1/event/" + id;

        // Request a string response from the provided URL.
        JsonObjectRequest eventRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                EventModel eventModel = EventModel.of(response);
                listener.onComplete(eventModel);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError();
                Log.e("API FAIL", "Error calling API " + url, error);
            }
        });

        // Add the request to the RequestQueue.
        ApplicationRequestQueue.INSTANCE.addToRequestQueue(context, eventRequest);
    }

    public void create(final Context context, EventModel newEvent, final RequestListener<EventModel> listener) {
        final String url = "https://backtoback-01.appspot.com/_ah/api/backtoback/v1/event";
        newEvent.setOwner(UserEntity.of(1L, "username"));

        // Request a string response from the provided URL.
        JsonObjectRequest eventRequest = new JsonObjectRequest(Request.Method.POST, url, newEvent.asJSON(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                EventModel eventModel = EventModel.of(response);
                listener.onComplete(eventModel);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError();
                Log.e("API FAIL", "Error calling API " + url, error);
            }
        });

        // Add the request to the RequestQueue.
        ApplicationRequestQueue.INSTANCE.addToRequestQueue(context, eventRequest);
    }
}
