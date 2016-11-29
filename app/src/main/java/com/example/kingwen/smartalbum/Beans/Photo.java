package com.example.kingwen.smartalbum.Beans;

/**
 * Created by kingwen on 2016/11/28.
 */
public class Photo  {

    /**
     * 照片id
     *
     */
    private String id;

    /**
     * 拍摄时间
      */
    private String time;
    /**
     * 存储地址
     */
    private String data;
    /**
     * 经度
     */
    private String  latitude;

    /**
     * 纬度
     */
    private String longitude;

    public Photo(){}

    /**
     * 构造方法
     * @param id
     * @param time
     * @param data
     * @param latitude
     * @param longitude
     */
    public Photo(String id, String time, String data, String latitude, String longitude) {
        this.id = id;
        this.time = time;
        this.data = data;
        this.latitude = latitude;
        this.longitude = longitude;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

}

