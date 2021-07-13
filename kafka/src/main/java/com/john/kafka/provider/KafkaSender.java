package com.john.kafka.provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.john.kafka.entity.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * @author songjun
 * @date 2021-07-13
 * @desc 消息发送类
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaSender {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private Gson gson = new GsonBuilder().create();

    /**
     * 这里关键的代码为 kafkaTemplate.send() 方法，
     * topic1 是 Kafka 里的 topic，这个 topic 在 Java 程序中是不需要提前在 Kafka 中设置的，
     * 因为它会在发送的时候自动创建你设置的 topic
     */
    public void send() {
        Message message = new Message();
        message.setId(System.currentTimeMillis());
        message.setMsg(UUID.randomUUID().toString());
        message.setSendTime(new Date());
        log.info("+++++++++++++++++++++  message = {}", gson.toJson(message));
        kafkaTemplate.send("topic1", gson.toJson(message));
    }

}
