package com.example.administrator.test_sock;

import android.util.Log;

import com.jky.struct2.http.core.AjaxCallBack;
import com.jky.struct2.http.entityhandle.HttpExceptionResult;
import com.jky.struct2.http.entityhandle.HttpResult;

/**
 * Created by Administrator on 2015/10/23.
 */
public class CallBack<HttpResult> extends AjaxCallBack<HttpResult> {



    @Override
    public void onSuccess(HttpResult httpResult) {
        httpResult.toString();
        //Log.d("sucess",httpResult.);
    }

    @Override
    public void onFailure(int i, HttpExceptionResult httpExceptionResult) {
        Log.d("callback","fail");
    }
}
