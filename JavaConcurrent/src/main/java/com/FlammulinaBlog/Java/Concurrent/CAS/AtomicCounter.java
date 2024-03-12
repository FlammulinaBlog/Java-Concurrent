package com.FlammulinaBlog.Java.Concurrent.CAS;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter {

    private AtomicInteger value = new AtomicInteger(0);

    public int getValue() {
        return value.get();
    }

    public void increment() {
        int current;
        int next;
        do {
            // 读取当前值
            current = value.get();
            // 计算下一个值
            next = current + 1;
            // CAS操作尝试更新值
            System.out.println(Thread.currentThread().getName() + ": CAS operation  current: " + current + " next:" + next + (current == next ? "succeeded" : "failed"));
        } while (!value.compareAndSet(current, next)); // 如果当前值已经被其他线程修改，则重试
        // 打印CAS操作结果
    }

    public static void main(String[] args) {
        AtomicCounter counter = new AtomicCounter();
        // 启动多个线程并发执行递增操作
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    counter.increment();
                }
            }).start();
        }

        ArrayBlockingQueue sss =  new ArrayBlockingQueue<>(1);

        CountDownLatch countDownLatch = new CountDownLatch(2);

        // 等待所有线程执行完毕
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 输出最终计数值
        System.out.println("Final count: " + counter.getValue());
    }

}
