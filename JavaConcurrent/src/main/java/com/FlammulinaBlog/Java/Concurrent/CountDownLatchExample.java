package com.FlammulinaBlog.Java.Concurrent;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {
    private static final int THREAD_COUNT = 64;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                try {
                    startSignal.await(); // 等待startSignal为0
                    // 模拟任务执行
                    System.out.println("Thread " + Thread.currentThread().getName() + " is executing task...");
                    Thread.sleep(1000); // 模拟任务耗时
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    doneSignal.countDown(); // 任务执行完毕，计数器减1
                }
            }).start();
        }

        System.out.println("Start all threads...");

        Thread.sleep(1000L);
        startSignal.countDown(); // 触发所有线程开始执行任务
        System.out.println("触发所有线程开始执行任务");
        doneSignal.await(); // 等待所有线程执行完毕
        System.out.println("All threads have finished executing tasks.");
    }
}
