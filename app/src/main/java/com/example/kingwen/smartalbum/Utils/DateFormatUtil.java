package com.example.kingwen.smartalbum.Utils;

import android.content.Context;
import android.util.Log;
import com.amap.api.maps.model.LatLng;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kingwen on 2016/12/7.
 *
 * 本类用于地图坐标格式转换，然而并没有实现，是留下个一个坑，在siteFragment中进行调用
 * 目前的效果是我们通过手机自动记录的坐标和我们在地图上显示的坐标是不一样的、需要进行修改
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
