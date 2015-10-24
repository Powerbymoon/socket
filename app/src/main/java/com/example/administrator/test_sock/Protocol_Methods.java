package com.example.administrator.test_sock;

import com.example.administrator.test_sock.Globle.GlobleArrt;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2015/10/21.
 */
public class Protocol_Methods implements Base_Methods{
    @Override
    public  byte[] pack(String info_send, byte os, byte type, byte flag) {
        try {
            int body_len = 0;
            body_len = info_send.getBytes("utf-8").length;
            int header_len = 7+body_len;
            byte[] data={};
            byte[] os_type_flag={os,type,flag};
            byte[] header = Until_Transition.byteMerger(Until_Transition.intToByteArray(header_len),os_type_flag);
            byte[] tmp =info_send.getBytes("utf-8");
            data = Until_Transition.byteMerger(header,tmp);
            return data;
            //String s9=new String(data,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void unpack(int len,byte[] receiveByte) throws UnsupportedEncodingException {
        GlobleArrt.result=new Unpack_Result();
        GlobleArrt.result.setTotle_len(Until_Transition.getTotle_Len(len, receiveByte));
        GlobleArrt.result.setOs(Until_Transition.getOs(len, receiveByte));
        GlobleArrt.result.setType( Until_Transition.getType(len, receiveByte));
        GlobleArrt.result.setFlag( Until_Transition.getFlag(len, receiveByte));
        GlobleArrt.result.setBody_data(Until_Transition.getBody_Data(len, receiveByte));
        }
    }

