package com.example.administrator.test_sock.Activity;

import android.content.Intent;
import android.os.AsyncTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.example.administrator.test_sock.ChatEntity;
import com.example.administrator.test_sock.ChatMessageAdapter;
import com.example.administrator.test_sock.Globle.GlobleArrt;
import com.example.administrator.test_sock.Service.MyStartService;
import com.example.administrator.test_sock.Protocol_Methods;
import com.example.administrator.test_sock.R;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    private ListView chatMeessageListView;
    public ChatMessageAdapter chatMessageAdapter;
    public List<ChatEntity> chatList;
    EditText input;
    Button send;
    boolean is_connect = false;
    Intent intent2;
    public String token ="PyPQetDSyPl5cmVGxv27OA";
    byte os = 2, type_send = 2, type_bind = 1, flag = 1;
    Protocol_Methods protocol_methods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        GlobleArrt.main = this;
        input = (EditText) findViewById(R.id.chat_edit_input);
        send = (Button) findViewById(R.id.chat_btn_send);
        chatMeessageListView = (ListView) findViewById(R.id.chat_Listview);
        chatList = new ArrayList<ChatEntity>();
        new Init().execute();
    }

    public class Init extends AsyncTask<Boolean, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(Boolean... params) {
            try {
                GlobleArrt.socket = new Socket();
                SocketAddress socketaddress = new InetSocketAddress("219.239.89.129", 8282);
                GlobleArrt.socket.connect(socketaddress, 10000);
                is_connect = GlobleArrt.socket.isConnected();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e);
            }
            return is_connect;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean is_connect) {
            // TODO Auto-generated method stub
            if (is_connect) {
                protocol_methods = new Protocol_Methods();
                // String token = format("PyPQetDSyPl5cmVGxv27OA");
               // byte  os=2,type9=1,flag9=1;
                byte[] data = protocol_methods.pack("PyPQetDSyPl5cmVGxv27OA==",os,type_bind,flag);
                try {
                    GlobleArrt.socket.getOutputStream().write(data);
                    GlobleArrt.socket.getOutputStream().flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                IsConnect_Thread t = new IsConnect_Thread();
                t.start();

                intent2 = new Intent(MainActivity.this, MyStartService.class);
                startService(intent2);

                chatMessageAdapter = new ChatMessageAdapter(MainActivity.this, chatList);
                chatMeessageListView.setAdapter(chatMessageAdapter);

                send.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                       // String msg = input.getText().toString();

                        if (GlobleArrt.socket.isConnected()) {
                            if (!GlobleArrt.socket.isOutputShutdown()) {
                                String message = input.getText().toString();
                                String message_send = format(message);
                              //  byte os1=2,type1=2,flag1=1;
                                try {
                                    byte[] data = protocol_methods.pack(message_send, os, type_send, flag);
                                    GlobleArrt.socket.getOutputStream().write(data);
                                    GlobleArrt.socket.getOutputStream().flush();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                input.setText("");
                                ChatEntity chatMessage = new ChatEntity();
                                chatMessage.setBody_data(message);
                                chatMessage.setMessageType(ChatEntity.SEND);
                                Date date = new Date();
                                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd hh:mm:ss");
                                String sendTime = sdf.format(date);
                                chatMessage.setSendTime(sendTime);
                                chatList.add(chatMessage);
                                chatMessageAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
            } else {
                new Init().execute();
            }
            super.onPostExecute(is_connect);
        }

        private String format(String info) {
            // TODO Auto-generated method stub
            String str = "{\"body_data\":";
            String result = str + "\"" + info + "\"" + "}";
            System.out.println(result);
            return result;
        }

        private class IsConnect_Thread extends Thread {
            public void run() {
                try {
                    this.sleep(10000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                while (true) {
                     byte os3=2,type3=2,flag3=1;
                    byte[] data = protocol_methods.pack("", os3, type3, flag3);
                    try {
                        GlobleArrt.socket.getOutputStream().write(data);
                        GlobleArrt.socket.getOutputStream().flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                        GlobleArrt.count = GlobleArrt.count + 1;
                    }
                    Log.d("lalala", "不连");
                    if (GlobleArrt.count >= 5)
                        try {
                            //GlobleArrt.socket.close();
                            GlobleArrt.socket.close();
                            Log.d("lalala", "重连");
                            GlobleArrt.socket = new Socket();
                            SocketAddress socketaddress = new InetSocketAddress("219.239.89.129", 8282);
                            GlobleArrt.socket.connect(socketaddress, 10000);
                            byte os4=2,type4=1,flag4=1;
                            byte[] data_bind = protocol_methods.pack(token, os4, type4, flag4);
                            try {
                                GlobleArrt.socket.getOutputStream().write(data_bind);
                                GlobleArrt.socket.getOutputStream().flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            // new Init().execute();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    try {
                        this.sleep(10000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

        }
    }
}


