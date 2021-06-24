package com.jun.jpa.config;

import com.jun.jpa.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.io.IOException;

/**
 * @author songjun
 * @date 2021-06-24
 * @desc 配置项目启动自动跳转浏览器
 */

@Configuration
@Slf4j
public class AutoBrowser {
    @EventListener({ApplicationReadyEvent.class})
    void applicationReadyEvent() {
        log.debug("应用已经准备就绪 ... 启动浏览器");
        // 这里需要注url:端口号+测试类方法名
        String port = SpringContextUtil.getApplicationContext().getEnvironment().getProperty("server.port");
        String url = "http://localhost:" + port + "/swagger-ui/index.html";
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
