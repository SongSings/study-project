package server;

import lombok.AllArgsConstructor;
import org.john.redisstream.config.Constant;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 周期性的向流中产生消息,每隔5s就生产一个数据到Stream中
 */
@Component
@AllArgsConstructor
public class CycleGeneratorStreamMessageRunner implements ApplicationRunner {

    private final JobStreamProducer streamProducer;

    @Override
    public void run(ApplicationArguments args) {
        ScheduledFuture<?> scheduledFuture = Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(() -> streamProducer.sendRecord(Constant.STREAM_KEY_JOB),
                        0, 60, TimeUnit.SECONDS);
    }
}