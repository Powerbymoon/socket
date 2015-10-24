package com.example.administrator.test_sock;

import android.util.Log;

import com.example.administrator.test_sock.Been.Been;
import com.example.administrator.test_sock.Globle.GlobleArrt;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2015/10/20.
 */
public class Until_Transition {

    /**
     * byte数组中取int数值，本方法适用于(低位在后，高位在前)的顺序。和intToBytes2（）配套使用
     */
    public int bytesToInt2(byte[] src, int offset) {
        int value;
        value = (int) (((src[offset] & 0xFF) << 24)
                | ((src[offset + 1] & 0xFF) << 16)
                | ((src[offset + 2] & 0xFF) << 8)
                | (src[offset + 3] & 0xFF));
        return value;
    }

    //byte转换为int
    public static int byteToInt2(byte[] b) {
        int mask = 0xff;
        int temp = 0;
        int n = 0;
        for (int i = 0; i < b.length; i++) {
            n <<= 8;
            temp = b[i] & mask;
            n |= temp;
        }
        return n;
    }

    public static void send(String info_send, byte[] type_flag, String ext) {
        // TODO Auto-generated method stub
        try {
            int body_len = info_send.getBytes("utf-8").length;
            int ext_len = ext.getBytes("utf-8").length;
            int header_len = 12 + body_len + ext_len;
            byte[] data = {};
            // byte[] type_flag={1,0};
            byte[] header = byteMerger(intToByteArray(header_len), type_flag);
            header = byteMerger(header, intToByteArray(ext_len));
            byte[] tmp = byteMerger(ext.getBytes("utf-8"), info_send.getBytes("utf-8"));
            data = byteMerger(header, tmp);
            String s9 = new String(data, "utf-8");
//                byte[] ext_len = intToByteArray(protocol.getExt().getBytes("utf-8").length);
//                byte[] ext = protocol.getExt().getBytes("utf-8");
//                String s1=new String(ext,"utf-8");
//                byte[] body = info_send.getBytes("UTF-8");
//                byte[] head = byteMerger(len,type,flag,ext_len);
//                byte[] data = byteMerger(head,ext,body);
//                byte[] b=data[0];
//                Integer b = bytesToInt2(intToByteArray(length), 0);
////                //System.arraycopy(len, 0, byte_1, 0, len.length);
//                String s0 =  new String(data, "utf-8");
//                System.out.println("长度" + b.toString());
//                String s=bytes2HexString(data);
//                String ss=toStringHex(s);
//                Log.d("www","lenght:"+b.toString());
//                Log.d("www","body:"+ss);
//                Log.d("www", s9);
            GlobleArrt.socket.getOutputStream().write(data);
            GlobleArrt.socket.getOutputStream().flush();
            GlobleArrt.count = 0;
        } catch (IOException e) {
            e.printStackTrace();
            GlobleArrt.count = GlobleArrt.count + 1;
            Integer i = GlobleArrt.count;
            Log.d("断线次数：", i.toString());
        }
    }

    public static byte[] byteMerger(byte[] bytes, byte[] type_flag) {
        byte[] byte_2 = new byte[bytes.length + type_flag.length];
        // byte[] byte_3 = new byte[len.length];
        System.arraycopy(bytes, 0, byte_2, 0, bytes.length);
        System.arraycopy(type_flag, 0, byte_2, bytes.length, type_flag.length);
        return byte_2;
    }

    private String format(String info) {
        // TODO Auto-generated method stub
        String str = "{\"body_data\":";
        String result = str + "\"" + info + "\"" + "}";
        System.out.println(result);
        return result;
    }


    //将二进制字符串转换成Unicode字符串
    private String BinstrToStr(String binStr) {
        String[] tempStr = StrToStrArray(binStr);
        char[] tempChar = new char[tempStr.length];
        for (int i = 0; i < tempStr.length; i++) {
            tempChar[i] = BinstrToChar(tempStr[i]);
        }
        return String.valueOf(tempChar);
    }

    //将初始二进制字符串转换成字符串数组，以空格相隔
    private String[] StrToStrArray(String str) {
        return str.split(" ");
    }

    //将二进制字符串转换为char
    private char BinstrToChar(String binStr) {
        int[] temp = BinstrToIntArray(binStr);
        int sum = 0;
        for (int i = 0; i < temp.length; i++) {
            sum += temp[temp.length - 1 - i] << i;
        }
        return (char) sum;
    }

    //将二进制字符串转换成int数组
    private int[] BinstrToIntArray(String binStr) {
        char[] temp = binStr.toCharArray();
        int[] result = new int[temp.length];
        for (int i = 0; i < temp.length; i++) {
            result[i] = temp[i] - 48;
        }
        return result;
    }

    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }

    public byte[] byteMerger(byte[] head, byte[] ext, byte[] body) {
        byte[] byte_3 = new byte[head.length + ext.length + body.length];
        // byte[] byte_3 = new byte[len.length];
        System.arraycopy(head, 0, byte_3, 0, head.length);
        System.arraycopy(ext, 0, byte_3, head.length, ext.length);
        System.arraycopy(body, 0, byte_3, ext.length, body.length);
        return byte_3;
    }

    static int getTotle_Len(int len, byte[] receiveByte) {
        Integer l = 0;
        if (len > 0) {
            byte[] length = new byte[4];
            System.arraycopy(receiveByte, 0, length, 0, 4);
            l = Until_Transition.byteToInt2(length);
        }
        return l;
    }

    static int getType(int len, byte[] receiveByte) {

        byte[] type = new byte[1];
        System.arraycopy(receiveByte, 5, type, 0, 1);
        // char t = byteToChar(type);
        //char t= (char)type[0];
        Integer t = Until_Transition.byteToInt2(type);
        // String str = String.valueOf(t);
        return t;

    }

    static int getFlag(int len, byte[] receiveByte) {

        byte[] flag = new byte[1];
        System.arraycopy(receiveByte, 6, flag, 0, 1);
        Integer f = Until_Transition.byteToInt2(flag);
        //char t= (char)type[0];
        return f;

    }

    static String getBody_Data(int len, byte[] receiveByte) throws UnsupportedEncodingException {
        Integer j = GlobleArrt.result.getFlag();
        if (j == 1) {
            return null;
            } else {
                String str2 = new String(receiveByte, "utf-8");
                String sub2 = str2.substring(7);
                sub2 = sub2.trim();
                if (sub2.length() > 0) {
                    Log.d("re", sub2);
                    Gson gson = new Gson();
                    Been message = new Been();
                    message = gson.fromJson(sub2,Been.class);
                    try {
                        // GlobleArrt.respone_protocol.setBody_data(message.getBody_data());
                        return message.getBody_data();
                    } catch (NullPointerException e) {

                    }
                }
            }
        return "";
    }


    public static int getOs(int len, byte[] receiveByte) {
        byte[] type = new byte[1];
        System.arraycopy(receiveByte, 4, type, 0, 1);
        Integer t = Until_Transition.byteToInt2(type);
        // String str = String.valueOf(t);
        return t;
    }
}
