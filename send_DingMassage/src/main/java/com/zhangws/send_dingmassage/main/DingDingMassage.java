package com.zhangws.send_dingmassage.main;


import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.taobao.api.ApiException;
import com.zhangws.send_dingmassage.config.DingConfig;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


/**
 * @Author: zhangWs
 * @Date: 2021/12/30 11:02
 * @Classname: DingDingMassage
 * @Description: 发送钉钉信息
 */

@Component
public class DingDingMassage {

    @Autowired
    private DingConfig dingConfig;

    @Scheduled(cron = "0 0 11 ? * 2,3,4,5,6")
    private void lunchExecute() throws ApiException {
       sendMassage("各位小主，该点外卖了~");
    }

    @Scheduled(cron = "0 30 17 ? * 2,3,4,5,6")
    private void dinnerExecute() throws ApiException {
        sendMassage("各位小主，该点外卖了~");
    }

    @Scheduled(cron = "0 0 9 ? * 2,3,4,5,6")
    private void workExecute() throws ApiException {
        sendMassage("各位小主，上班了~，记得打卡哟~");
    }

    @Scheduled(cron = "0 0 21 ? * 2,3,4,5,6")
    private void getOffWorkExecute() throws ApiException {
        sendMassage("各位小主，下班了~，记得打卡哟~");
    }


    private  void sendMassage(String massage) throws ApiException {
        String sign = getSign(dingConfig.getUrl(), dingConfig.getToken(), dingConfig.getSecret());
        DefaultDingTalkClient dingTalkClient = new DefaultDingTalkClient(sign);
        OapiRobotSendRequest sendRequest = new OapiRobotSendRequest();

        //设置信息类型
        sendRequest.setMsgtype("text");
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent(massage+"各位小主，该点外卖了~");
        sendRequest.setText(text);

        dingTalkClient.execute(sendRequest);
    }


    private  String getSign(String baseUrl, String token, String secret) {
        String url = "";
        try {
            long timestamp = System.currentTimeMillis();
            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
            url = baseUrl +"?access_token="+ token + "&timestamp=" + timestamp + "&sign=" +
                    URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }
}
