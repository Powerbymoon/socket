package com.example.administrator.test_sock;

/**
 * Created by Administrator on 2015/10/22.
 */
public class Unpack_Result {
    int totle_len;
    int os;
    int type;
    int flag;

    public int getTotle_len() {
        return totle_len;
    }

    public void setTotle_len(int totle_len) {
        this.totle_len = totle_len;
    }

    public int getOs() {
        return os;
    }

    public void setOs(int os) {
        this.os = os;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getBody_data() {
        return body_data;
    }

    public void setBody_data(String body_data) {
        this.body_data = body_data;
    }

    String body_data;
}
