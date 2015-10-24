package com.example.administrator.test_sock;

/**
 * Created by Administrator on 2015/10/20.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.administrator.test_sock.Globle.GlobleArrt;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UpDateUI_Broadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("BC_One")) {
            if (GlobleArrt.result.getType() == 99) {
                Log.d("异常", "type：99");
            } else {
                String s = intent.getStringExtra("msg");
                System.out.println("reveiver1收到消息：" + s);
                ChatEntity chatMessage = new ChatEntity();
                chatMessage.setBody_data(s);
                chatMessage.setMessageType(ChatEntity.RECEIVE);
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd hh:mm:ss");
                String sendTime = sdf.format(date);
                chatMessage.setSendTime(sendTime);
                GlobleArrt.main.chatList.add(chatMessage);
                GlobleArrt.main.chatMessageAdapter.notifyDataSetChanged();
            }
        } else if (action.equals("BC_Two")) {
            String s = intent.getStringExtra("msg");
            System.out.println("reveiver1收到消息：" + s);
        }
    }

}
