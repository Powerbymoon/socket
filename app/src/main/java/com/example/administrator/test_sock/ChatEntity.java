package com.example.administrator.test_sock;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/10/21.
 */
public class ChatEntity implements Serializable {
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

    int type;
    public static final int  RECEIVE = 0;
    public static final int SEND = 1;

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    private int messageType;
    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    private String sendTime;
    public String getBody_data() {
        return body_data;
    }

    public void setBody_data(String body_data) {
        this.body_data = body_data;
    }

    String body_data;
}

