package org.john.redis.consumer;

import lombok.extern.slf4j.Slf4j;
import org.john.redis.constants.Cosntants;
import org.john.redis.entity.Book;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.connection.stream.StreamReadOptions;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 脱离消费组-直接消费Stream中的数据，可以获取到Stream中所有的消息
 */
@Component
@Slf4j
public class XreadNonBlockConsumer01 implements InitializingBean, DisposableBean {

    private ThreadPoolExecutor threadPoolExecutor;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private volatile boolean stop = false;

    @Override
    public void afterPropertiesSet() {

        // 初始化线程池
        threadPoolExecutor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS,
                                                    new LinkedBlockingDeque<>(), r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            thread.setName("xread-nonblock-01");
            return thread;
        });

        StreamReadOptions streamReadOptions = StreamReadOptions.empty()
                // 如果没有数据，则阻塞1s 阻塞时间需要小于`spring.redis.timeout`配置的时间
                .block(Duration.ofMillis(1000))
                // 一直阻塞直到获取数据，可能会报超时异常
                // .block(Duration.ofMillis(0))
                // 1次获取10个数据
                .count(10);

        StringBuilder readOffset = new StringBuilder("0-0");
        threadPoolExecutor.execute(() -> {
            while (!stop) {
                // 使用xread读取数据时，需要记录下最后一次读取到offset，然后当作下次读取的offset，否则读取出来的数据会有问题
                List<ObjectRecord<String, Book>> objectRecords = redisTemplate.opsForStream()
                        .read(Book.class, streamReadOptions, StreamOffset.create(Cosntants.STREAM_KEY_001, ReadOffset.from(readOffset.toString())));
                if (CollectionUtils.isEmpty(objectRecords)) {
                    log.warn("没有获取到数据");
                    continue;
                }
                for (ObjectRecord<String, Book> objectRecord : objectRecords) {
                    log.info("获取到的数据信息 id:[{}] book:[{}]", objectRecord.getId(), objectRecord.getValue());
                    readOffset.setLength(0);
                    readOffset.append(objectRecord.getId());
                }
            }
        });
    }

    @Override
    public void destroy() throws Exception {
        stop = true;
        threadPoolExecutor.shutdown();
        threadPoolExecutor.awaitTermination(3, TimeUnit.SECONDS);
    }
}