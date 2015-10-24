package com.example.administrator.test_sock.Been;

/**
 * Created by Administrator on 2015/10/23.
 */
public class Login_Response_Been {
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

   public String code;
    public String msg;
    public Data data;
    public class Data {
        public String uid;
        public String uname;
        public String nickname;
        public String realname;
        public  String face;
        public   String bind_mobile;
        public   String identity_card;
        public   String user_code;
        public  String lock_code;
        public   String msg_num;
        public   String topic_num;
        public   String question_nu;
        public  String score;
        public  String mobile;
        public   String first_login;
    }
       /* "uid": "26058000",
                "uname": "18600806131",
                "nickname": "asdfgfh",
                "realname": "测试",
                "face": "/dsfsd/sdsfs/dww.jpg",
                "bind_mobile": "15200000000",
                "identity_card": "4561xxxxxxx......",
                "user_code": "sde323",
                "lock_code": "233433",
                "msg_num": "0",
                "topic_num":"1"
        "question_num":"1"

        "score": "15",
                "mobile": "18600806131",
                "first_login": "0"*/
}
