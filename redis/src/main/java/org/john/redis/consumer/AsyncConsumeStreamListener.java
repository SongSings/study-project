package org.john.redis.consumer;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.john.redis.constants.Cosntants;
import org.john.redis.entity.Book;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;

/**
 * 通过监听器异步消费
 *
 * 实现这个接口的目的是为了，消费Stream中的数据。
 * 需要注意在注册时使用的是streamMessageListenerContainer.receiveAutoAck()
 * 还是streamMessageListenerContainer.receive()方法，
 * 如果是第二个，则需要手动ack，手动ack的代码：
 * redisTemplate.opsForStream().acknowledge("key","group","recordId");
 */
@Slf4j
@Getter
@Setter
public class AsyncConsumeStreamListener implements StreamListener<String, ObjectRecord<String, Book>> {
    /**
     * 消费者类型：独立消费、消费组消费
     */
    private String consumerType;
    /**
     * 消费组
     */
    private String group;
    /**
     * 消费组中的某个消费者
     */
    private String consumerName;

    public AsyncConsumeStreamListener(String consumerType, String group, String consumerName) {
        this.consumerType = consumerType;
        this.group = group;
        this.consumerName = consumerName;
    }

    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onMessage(ObjectRecord<String, Book> message) {
        String stream = message.getStream();
        RecordId id = message.getId();
        Book value = message.getValue();
        if (StringUtils.isBlank(group)) {
            log.info("[{}]: 接收到一个消息 stream:[{}],id:[{}],value:[{}]", consumerType, stream, id, value);
        } else {
            log.info("[{}] group:[{}] consumerName:[{}] 接收到一个消息 stream:[{}],id:[{}],value:[{}]", consumerType,
                     group, consumerName, stream, id, value);
        }

        // 当是消费组消费时，如果不是自动ack，则需要在这个地方手动ack
//        redisTemplate.opsForStream()
//                 .acknowledge(Cosntants.STREAM_KEY_001,group,id);
    }
}