package com.example.administrator.test_sock;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2015/10/21.
 */
public interface Base_Methods {
    byte[] pack(String info_send, byte os, byte type, byte flag);

    void unpack(int len, byte[] receiveByte) throws UnsupportedEncodingException;
}
