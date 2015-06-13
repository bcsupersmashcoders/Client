package com.supersmashcoders.backtobackhackathon.proxy;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by LuisRoberto on 6/13/2015.
 */
public class ImageProxy {
    public void getURLImage(final Context context, String url, final ImageView imageview){
        // Request a string response from the provided URL.
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap> (){
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        imageview.setImageBitmap(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                       System.err.println("Error in getURLImage " + error.getMessage() + "\n" + error.getStackTrace());
                    }
                });
        // Add the request to the RequestQueue.
        ApplicationRequestQueue.INSTANCE.addToRequestQueue(context, request);
    }
}
