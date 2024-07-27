package com.jun.leetcode;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author songjun
 * @date 2023-05-31 10:28:40
 * @desc
 */
@SpringBootTest
class AtomicTests {


    @Test
    void concurrent() throws InterruptedException {

        // 原子性
        //atomic 主要利用 CAS (Compare And swap) 和 volatile 和 native 方法来保证原子操作，从而避免 synchronized 的高开销，执行效率大为提升。
        AtomicInteger inter = new AtomicInteger(1);

        inter.addAndGet(1);
        System.out.println("inter.addAndGet(1) = " + inter.addAndGet(1));

        Lock lock = new ReentrantLock();


        boolean tryLock = lock.tryLock(10, TimeUnit.MILLISECONDS);
        if(tryLock) {
            System.out.println("tryLock = " + tryLock);
        }


//        ConcurrentHashMap
    }


    /**
     * 创建线程池
     */
    @Test
    void createThread(){
        int a= 300;
        long b= 3000000000L;
        System.out.println("a = " + a);
        System.out.println("b = " + b);

//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,);
    }
}
