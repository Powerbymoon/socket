package com.example.administrator.test_sock.Service;

/**
 * Created by Administrator on 2015/10/20.
 */

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.administrator.test_sock.Globle.GlobleArrt;
import com.example.administrator.test_sock.Protocol_Methods;
import com.example.administrator.test_sock.Until_Transition;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class MyStartService extends Service {
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        Log.d("info", "Service--onCreate()");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        Log.d("info", "Service--onStartCommand()");
        new Response_Thread().start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        Log.d("info", "Service--onDestroy()");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        Log.d("info", "Service--onBind()");
        return null;
    }

    private class Response_Thread extends Thread {
        public void run() {
            while (true) {
                if (GlobleArrt.socket != null) {
                    if (!GlobleArrt.socket.isClosed()) {
                        if (GlobleArrt.socket.isConnected()) {
                            if (!GlobleArrt.socket.isInputShutdown()) {
                                try {
                                   /* boolean flag=true;
                                    while (flag) {
                                        InputStream in=GlobleArrt.socket.getInputStream();
                                        byte[] b=new byte[4];
                                        in.read(b);
                                        if(b.length==4){
                                            Integer l=Until_Transition.byteToInt2(b);
                                            Log.d("l:",l.toString());
                                            ByteBuffer buffer= ByteBuffer.allocate(l);
                                            byte[] b_data=new byte[l];
                                            int len = in.read(b_data);
                                            if(l==len){
                                                flag=false;
                                            }else if(l>len){
                                                buffer.wrap(b);
                                            }else if(l<len){

                                            }
                                        }
                                    }*/

                                    byte[] receiveByte = new byte[1024];
                                    int len = 0;
                                    len = GlobleArrt.socket.getInputStream().read(receiveByte);
                                    if (len > 0) {
                                        new Protocol_Methods().unpack(len, receiveByte);
                                        if (GlobleArrt.result.getBody_data() == null) {
                                        } else {
                                            Intent intent = new Intent();
                                            intent.putExtra("msg", GlobleArrt.result.getBody_data());
                                            intent.setAction("BC_One");
                                            sendBroadcast(intent);
                                        }
                                    }
                                } catch (IOException e) {

                                }

                            }
                        }
                    }

                }
            }
        }
    }
}
