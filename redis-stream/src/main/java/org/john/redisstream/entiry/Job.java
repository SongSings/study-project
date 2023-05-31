package org.john.redisstream.entiry;

import com.github.javafaker.Faker;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class Job implements Serializable {

    private String title;
    private String position;

    // 每次调用create方法时，会自动产生一个Job的对象，对象模拟数据是使用javafaker来模拟生成的
    public static Job create() {
        com.github.javafaker.Job fakerJob= Faker.instance().job();
        Job job = new Job();
        job.setTitle(fakerJob.title());
        job.setPosition(fakerJob.position());
        return job;
    }
}