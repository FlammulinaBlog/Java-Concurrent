package com.FlammulinaBlog.Java.Concurrent.Semaphore;

import java.util.concurrent.Semaphore;

public class SemaphoreExample {

    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(20); // 创建一个初始许可证数量为 3 的 Semaphore

        // 创建 5 个线程并启动
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Worker(semaphore, i + 1));
            thread.start();
        }

    }

    static class Worker implements Runnable {
        private final Semaphore semaphore;
        private final int id;

        public Worker(Semaphore semaphore, int id) {
            this.semaphore = semaphore;
            this.id = id;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire(); // 获取许可证
                System.out.println("Worker " + id + " is 工作中");
                Thread.sleep(2000); // 模拟工作
                System.out.println("Worker " + id + " 完成工作");
                semaphore.release(); // 释放许可证
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
