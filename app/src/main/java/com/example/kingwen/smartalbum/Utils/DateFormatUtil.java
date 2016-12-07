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
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

/**
 * Created by kingwen on 2016/12/7.
 */
public class DateFormatUtil {

    private static final String TAG="DataFormUtil";


    public static LatLng getCorrectLatLng(Context context,double Lat,double Lng){

        String mURL="http://api.go2map.com/engine/api/translate/json?points="+Lng+","+Lat+"&type=2";


        RequestQueue mQueue=Volley.newRequestQueue(context);

        LatLng latlng=new LatLng(0,0);

        StringRequest stringRequest=new StringRequest(Request.Method.GET, mURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);
                     for(int i=0;i<jsonArray.length();i++){
                         JSONObject jsonObject=jsonArray.getJSONObject(i);
                         String points=jsonObject.getString("point");
                         Log.e(TAG,"point"+points);


                     }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: onErroe" );
            }
        }

        );

       mQueue.add(stringRequest);

        return latlng;
    }

}
