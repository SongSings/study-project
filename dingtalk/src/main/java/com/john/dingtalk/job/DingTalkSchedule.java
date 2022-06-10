package com.john.dingtalk.job;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import com.john.dingtalk.util.DingTalkHelp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * @author join
 * @date 2022-01-12
 * @desc
 */
@Slf4j
@Configuration
@EnableScheduling
public class DingTalkSchedule {

    @Scheduled(cron = "0 0/5 * * * ?")
    @Async
    public void pushData() {
        log.info("begin ==============");
        String today = DateUtil.format(new Date(), "yyyy-MM-dd");
        HttpUtil.get("http://timor.tech/api/holiday/info/" + today);
        //推送相关 数据来源你的机器人设置（仅设置安全密钥
        String webhook = "****";
        String sercetKey= "****";
        DingTalkHelp.messagePush(webhook,sercetKey);
    }
}

