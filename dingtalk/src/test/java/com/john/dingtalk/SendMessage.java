package com.john.dingtalk;

import cn.hutool.http.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author join
 * @date 2022-01-11
 * @desc
 */
public class SendMessage {

    public static void main(String[] args){

        // 钉钉的webhook
        String dingDingToken="https://oapi.dingtalk.com/robot/send?access_token=***";
        // 请求的JSON数据，这里我用map在工具类里转成json格式
        Map<String,Object> json=new HashMap();
        Map<String,Object> text=new HashMap();
        json.put("msgtype","text");
        text.put("content"," Σ(☉▽☉");
        json.put("text",text);
        // 发送post请求
        String response = HttpUtil.post(dingDingToken, json);
        System.out.println("相应结果："+response);
    }
}
