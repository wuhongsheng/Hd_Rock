package com.hd.rock.dao;

import java.io.Serializable;

/**
 * Created by whs on 2015/4/3.
 */
public class Rock implements Serializable {
    private String time;
    private String latitude;
    private String longitude;

    public Rock() {
    }

    public Rock(String latitude, String longitude, String time) {
        this.latitude = latitude;
        this.time = time;
        this.longitude = longitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
