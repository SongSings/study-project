package org.john.redis.producer;

import lombok.AllArgsConstructor;
import org.john.redis.constants.Cosntants;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 周期性的向流中产生消息,每隔5s就生产一个数据到Stream中
 */
@Component
@AllArgsConstructor
public class CycleGeneratorStreamMessageRunner implements ApplicationRunner {

    private final StreamProducer streamProducer;

    @Override
    public void run(ApplicationArguments args) {
        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(() -> streamProducer.sendRecord(Cosntants.STREAM_KEY_001),
                                     0, 60, TimeUnit.SECONDS);
    }
}