package com.fty.onlinecar;

import cn.hutool.http.HttpUtil;

import javax.crypto.Cipher;
import java.security.Key;

public class Test {

    private static final String KEY_ALGORITHM = "AES";
    private static final String ALGORITHM_STR = "AES/CBC/PKCS7Padding";
    private static Key key;
    private static Cipher cipher;


    public static void main(String[] args) {
        String msgtype = "text";
        String openId = "oiB6c5bCAMEoWCN0LxSlsR-AKb3o";
        String access_token = "38_fBM61G40gSPBdh68-B57K31QzeKlPrVCXjr8IaYCrD2Cc7r7MPa4qUdf14ukEjqkKl1JjOF_sEr5J3DFLW3eGW2HQOqhpBn22dWf3c1AqT1fsc1etAbS4lq-gRdUqOo_BpJ8dpk15fQ-NkZ7TEZbABACNI";
//        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=";
        String content = "hello world";
        String template_id = "p3efgYnUWStvLEKCSqznWlpq5VPnUFoJPQdKYQfThDs";
        String data = "";

        String body = "{\n" +
                "  \"touser\": \""+openId+"\",\n" +
                "  \"template_id\": \""+template_id+"\",\n" +

                "  \"miniprogram_state\":\"developer\",\n" +
                "  \"lang\":\"zh_CN\",\n" +
                "  \"data\": {\n" +
                "      \"number01\": {\n" +
                "          \"value\": \"339208499\"\n" +
                "      },\n" +
                "      \"date01\": {\n" +
                "          \"value\": \"2015年01月05日\"\n" +
                "      },\n" +
                "      \"site01\": {\n" +
                "          \"value\": \"TIT创意园\"\n" +
                "      } ,\n" +
                "      \"site02\": {\n" +
                "          \"value\": \"广州市新港中路397号\"\n" +
                "      }\n" +
                "  }\n" +
                "}\n";

        String res= HttpUtil.post(url+access_token,body);
        System.out.println(res);
//        String res = TestUtil.post(access_token,userId);
//
    }

}
