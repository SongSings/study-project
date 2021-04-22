package com.jun.websocket.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;
import java.util.Set;

/**
 * @author songjun
 * @date 2021-04-22
 * @desc  WebSocket 核心配置类,项目启动时会自动启动，类似与ContextListener.
 */
@Configuration
@Slf4j
public class WebSocketConfig implements ServerApplicationConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    /**
     * 注解方式
     * 扫描src下所有类@ServerEndPoint注解的类。
     */
    @Override
    public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> arg0) {
        log.info("============="+arg0.size());
        return arg0;
    }

    /**
     * 获取所有以接口方式配置的webSocket类。
     */
    @Override
    public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> arg0) {
        return null;
    }
}
