package server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.john.redisstream.entiry.Job;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 消息生产者
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JobStreamProducer {

    private final RedisTemplate<String, Object> redisTemplate;

    public void sendRecord(String streamKey) {
        Job job = Job.create();
        log.info("产生一个工作信息:[{}]", job);

        ObjectRecord<String, Job> record = StreamRecords.newRecord()
                .in(streamKey)
                .ofObject(job)
                .withId(RecordId.autoGenerate());

        RecordId recordId = redisTemplate.opsForStream()
                .add(record);

        log.info("返回的record-id:[{}]", recordId);
    }
}
