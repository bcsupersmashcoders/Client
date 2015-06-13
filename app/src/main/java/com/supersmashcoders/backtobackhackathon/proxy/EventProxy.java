package com.supersmashcoders.backtobackhackathon.proxy;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.supersmashcoders.backtobackhackathon.models.EventModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventProxy {
    public void getEvents(final Context context, final RequestListener<List<EventModel>> listener) {
        final String url = "http://jsonplaceholder.typicode.com/posts/1";

        // Request a string response from the provided URL.
        JsonObjectRequest eventsRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                List<EventModel> eventModelList = new ArrayList<>();
                eventModelList.add(new EventModel());
                eventModelList.add(new EventModel());
                eventModelList.add(new EventModel());
                listener.onComplete(eventModelList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("API FAIL", "Error calling API " + url);
                Log.e("API FAIL", error.getMessage());
                listener.onError();
            }
        });

        // Add the request to the RequestQueue.
        ApplicationRequestQueue.INSTANCE.addToRequestQueue(context, eventsRequest);
    }
}
