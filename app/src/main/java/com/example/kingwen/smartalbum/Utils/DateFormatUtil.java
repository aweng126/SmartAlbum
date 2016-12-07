package com.example.kingwen.smartalbum.Utils;

import android.content.Context;
import android.util.JsonToken;
import android.util.Log;

import com.amap.api.maps.model.LatLng;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by kingwen on 2016/12/7.
 */
public class DateFormatUtil {

    private static final String TAG="DataFormUtil";

    public LatLng getCorrectLatLng(Context context,double Lat,double Lng){
        RequestQueue mQueue=Volley.newRequestQueue(context);
        JsonObjectRequest request=new JsonObjectRequest("", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {





            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG,error.toString());

             }
           }
        );

        return null;
    }

}
