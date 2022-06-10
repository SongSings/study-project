package com.john.dingtalk.util;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.ObjectUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author songjun
 * @date 2022-01-12
 * @desc
 */
@Slf4j
@UtilityClass
public class DingTalkHelp {

    /**
     * @Description: 推送消息
     * @Param: [webhook,sercetKey] 创建机器人的时候生成的机器人webhook和密钥如果是别的安全设置则参考别的方式
     * @return: void
     */
    public void messagePush(String webhook,String sercetKey){
        //文本内容组织
        StringBuilder text = new StringBuilder();
        //图片
        text.append("![screenshot](https://static.dingtalk.com/media/lALPDh0cQsrZm_3M0czx_241_209.png_720x720q90g.jpg?bizType=im)");
        //换行
        text.append("\n");
        //文本
        text.append("### today");
        //钉钉actionCard推送对象
        OapiRobotSendRequest.Actioncard actionCard = new OapiRobotSendRequest.Actioncard();
        //推送透出首页标题 （你收到这条推送的时候会显示的初内容
        actionCard.setTitle(new Date().toString());
        //放入你组织好的内容
        actionCard.setText(text.toString());
        //做推送
        OapiRobotSendResponse resp = robotPushActionCardMessage(webhook,sercetKey,actionCard);
    }


    /**
     * @Description: 推送actionCard消息模版
     * @Param: [webhook,sercetKey,actionCard]
     * @return: void
     */
    public OapiRobotSendResponse robotPushActionCardMessage(String webhook,String sercetKey, OapiRobotSendRequest.Actioncard actionCard) {
        try {
            DingTalkClient client = getDingTalkClient(webhook, sercetKey);
            OapiRobotSendRequest request = new OapiRobotSendRequest();
            request.setMsgtype("actionCard");
            request.setActionCard(actionCard);
            OapiRobotSendResponse response = client.execute(request);
            if (!ObjectUtils.isEmpty(response) && response.isSuccess()) {
                log.info("钉钉机器人推送actionCard成功:{}", actionCard.getText());
            } else {
                log.error("钉钉机器人推送actionCard失败:{},{}", actionCard.getText(), response.getErrmsg());
            }
            return response;
        } catch (NoSuchAlgorithmException e) {
            log.error("=======钉钉机器人推送actionCard异常:{}======", e.getMessage());
        } catch (UnsupportedEncodingException e) {
            log.error("=======钉钉机器人推送actionCard异常:{}========", e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("=======钉钉机器人推送actionCard异常:{}=======", e.getMessage());
        } catch (ApiException e) {
            log.error("=======钉钉机器人推送actionCard异常:{}=====", e.getErrMsg());
        }
        return null;
    }
    /**
     * 获取钉钉对象 机器人仅设置密钥
     *
     * @param webHook   机器人webHook
     * @param secretKey 机器人密钥
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     */
    private DingTalkClient getDingTalkClient(String webHook, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        Map<String, String> sign = sign(secretKey);

        String aceRobotUrl = webHook + ("&timestamp=" + sign.get("timestamp") + "&sign=" + sign.get("sign"));
        DingTalkClient client = new DefaultDingTalkClient(aceRobotUrl);
        return client;
    }
    /**
     * 计算签名
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     */
    public static Map<String, String> sign(String robotSecret) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        Long timestamp = System.currentTimeMillis();
        //机器人加签密钥
        String stringToSign = timestamp + "\n" + robotSecret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(robotSecret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
        Map<String, String> map = new HashMap<>(2);
        map.put("timestamp", timestamp.toString());
        map.put("sign", sign);
        return map;
    }

}
