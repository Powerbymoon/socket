package com.example.administrator.test_sock.Activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.test_sock.CallBack;
import com.example.administrator.test_sock.Dialog.Progress;
import com.example.administrator.test_sock.Globle.GlobleArrt;
import com.example.administrator.test_sock.R;
import com.example.administrator.test_sock.Util.DesUtil;
import com.example.administrator.test_sock.Util.GetDeviceUniqueIDUtil;
import com.jky.struct2.http.FinalHttp;
import com.jky.struct2.http.core.AjaxCallBack;
import com.jky.struct2.http.core.AjaxParams;
import com.jky.struct2.http.entityhandle.HttpExceptionResult;
import com.jky.struct2.http.entityhandle.HttpResult;

import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import it.gmariotti.cardslib.library.view.CardListView;

public class MessageActivty extends AppCompatActivity {
    private CardListView messageCardLv;
    public Progress pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_activty);
        messageCardLv = (CardListView) findViewById(R.id.message_cardlv_message);
        initProgress();
        LoadMessage();
    }

    private void LoadMessage() {
        AjaxParams params = new AjaxParams();
        String APP_KEY = "kswby";
        String KEY_SECRET = "54sad1ferae";
     /*   rstr	 true	 string	 请求随机数
        sign	true	string	 生成加密串
        openid	 true	 string	 用户ID串
        refresh*/
        // FinalHttp(Context context, String appKey, String appSecret, String appVersion, String uuid, String channel)
        final FinalHttp httpRequest = new FinalHttp(getApplicationContext(), APP_KEY, KEY_SECRET, String.valueOf(8.0),
                GetDeviceUniqueIDUtil.getDeviceUniqueId(getApplicationContext()), "tusheng");
        String openid = "app"+GlobleArrt.message.data.uid;
        try {
            params.put("openid",DesUtil.encode(openid));
        } catch (Exception e) {
            e.printStackTrace();
        }
        params = httpRequest.addFixedParams(params);
        Log.d("pa",params.toString());
        httpRequest.getCustomFixedParams("http://tbxl.120ask.com/api/chat/news_list", params, new AjaxCallBack<HttpResult>() {
            //   http://tbxl.120ask.com/api/chat/news_list?fkey=kswby&openid=xxxxxxxx&rstr=randomstr&sign=F8E5211C67B1F5074E35BB77007A237C
            //  http://tbxl.120ask.com/api/chat/news_list?fkey=kswby&openid=app44686471&rstr=1445589573883&sign=5011EAF412C1A3E2E6F254291F04955D
           // openid=app44686471&rstr=1445589573883&fkey=kswby&os=2&sign=5011EAF412C1A3E2E6F254291F04955D&retype=2

            @Override
            public void onSuccess(HttpResult httpResult) {
                Log.d("json", httpResult.baseJson.toString());
            }
            @Override
            public void onFailure(int i, HttpExceptionResult httpExceptionResult) {
            }
        }, 4);
    }

    private void initProgress() {
        pDialog=new Progress(this, SweetAlertDialog.PROGRESS_TYPE);
    }


}
