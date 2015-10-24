package com.example.administrator.test_sock.Activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.dd.CircularProgressButton;
import com.example.administrator.test_sock.Been.Been;
import com.example.administrator.test_sock.Been.Login_Response_Been;
import com.example.administrator.test_sock.Globle.GlobleArrt;
import com.example.administrator.test_sock.NormalPostRequest;
import com.example.administrator.test_sock.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    public CircularProgressButton loginBtn;
    private EditText phoneEt,passEt;
    private CountDownTimer timeoutCount;
    //Context context;
    private long exitTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //isPermit();
        init();
    }

    private void init() {
        loginBtn = (CircularProgressButton) findViewById(R.id.login_btn_login);
        phoneEt = (EditText)findViewById(R.id.login_et_phone);
        passEt = (EditText)findViewById(R.id.login_et_code);
        loginBtnMethod();
    }

//    private void isPermit() {
//        SharedPreferences pre = getSharedPreferences("login", MODE_APPEND);
//        if(pre.getString("fg", "").equals("1")){
//            startActivity(new Intent(LoginActivity.this,MessageActivity.class));
//            finish();
//        }
//    }

    //haha
    private void loginBtnMethod() {
        loginBtn.setIndeterminateProgressMode(true);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBtn.setProgress(50);
                String phoneNumber = phoneEt.getText().toString();
                String passNumber = passEt.getText().toString();
                Map<String,String> map = new HashMap<String,String>();
               /* uuid	true	string	手机端标识
                uname	true	string	表单用户名
                passwd	true	string	表单密码
                app*/
              /*  AjaxParams params = new AjaxParams();
                CallBack cb=new CallBack();
                String APP_KEY = "kswby";
                String KEY_SECRET = "54sad1ferae";
              // FinalHttp(Context context, String appKey, String appSecret, String appVersion, String uuid, String channel)
                final FinalHttp httpRequest = new FinalHttp(getApplicationContext(), APP_KEY, KEY_SECRET, String.valueOf(8.0),"swdswfdwefwedf", "tusheng");
//                      params = httpRequest.addFixedParamsCustomSign(params);
                httpRequest.postCustomFixedParams("http://tbxl.120ask.com/api/menu/find", params, new AjaxCallBack<HttpResult>() {
                    @Override
                    public void onSuccess(HttpResult httpResult) {
                        Log.d("json",httpResult.baseJson.toString());
                    }
                    @Override
                    public void onFailure(int i, HttpExceptionResult httpExceptionResult) {
                    }
                }, 4);*/
                map.put("uuid","this_is_a_test");
                map.put("uname",phoneNumber);
                map.put("passwd",passNumber);
                if (phoneEt.getText().length() <= 0 || passEt.getText().length() <= 0) {
                    loginBtn.setProgress(-1);
                    loginBtn.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loginBtn.setProgress(0);
                        }
                    }, 1800);
                } else {
                    loginMethod(map);
                }
            }

            private void loginMethod(Map<String, String> map) {
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                Request<JSONObject> request = new NormalPostRequest("http://zys.iapp.120.net/common/account/login/", new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d(TAG, "response -> " + response.toString());
                        // Toast.makeText(GlobleData.context,response.toString(), Toast.LENGTH_LONG).show();
                        Gson gson = new Gson();
                       // Login_Response_Been message = new Login_Response_Been();
                        GlobleArrt.message = gson.fromJson(response.toString(),Login_Response_Been.class);
                        if(GlobleArrt.message.code.equals("200"))
                        {loginSeccess();}
                             else{
                            loginFail();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(LoginActivity.this, "连接超时，请稍后重试", Toast.LENGTH_SHORT).show();
                        loginBtn.setProgress(0);
                    }
                }, map);
                requestQueue.add(request);
                requestQueue.start();

            }

            private void loginSeccess() {
              /*  SharedPreferences pre = getSharedPreferences("login", MODE_APPEND);
                //String user = pre.getString("number", "");
                SharedPreferences.Editor edit = pre.edit();
                edit.putString("fg", "1");
                edit.commit();

                SharedPreferences account = getSharedPreferences("account", MODE_APPEND);
                //String user = pre.getString("number", "");
                edit = account.edit();
                edit.putString("mobile", GlobleData.mobile);
                edit.putString("token", GlobleData.token);
                edit.commit();*/

                // edit.putString("cookie", l.getCookie());
                loginBtn.setProgress(100);
                loginBtn.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(LoginActivity.this,MessageActivty.class));
                        finish();
                    }
                }, 700);
            }
        });
    }

    private void loginFail() {
        loginBtn.setProgress(-1);
        loginBtn.postDelayed(new Runnable() {
            @Override
            public void run() {
                loginBtn.setProgress(0);
            }
        }, 2000);
        //Toast.makeText(GlobleData.context,"验证码或密码错误",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
