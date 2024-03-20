package com.FlammulinaBlog.Java.Concurrent.BlockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

/**
 * 
 * BlockingQueue 不接受 null 元素。试图 add、put 或 offer 一个 null 元素时，某些实现会抛出 NullPointerException。null 被用作指示 poll 操作失败的警戒值。 

 * BlockingQueue 可以是限定容量的。它在任意给定时间都可以有一个 remainingCapacity，超出此容量，便无法无阻塞地 put 附加元素。没有任何内部容量约束的 BlockingQueue 总是报告 Integer.MAX_VALUE 的剩余容量。 

 * BlockingQueue 实现主要用于生产者-使用者队列，但它另外还支持 Collection 接口。因此，举例来说，使用 remove(x) 从队列中移除任意一个元素是有可能的。然而，这种操作通常不 会有效执行，只能有计划地偶尔使用，比如在取消排队信息时。 

 * BlockingQueue 实现是线程安全的。所有排队方法都可以使用内部锁或其他形式的并发控制来自动达到它们的目的。然而，大量的 Collection 操作（addAll、containsAll、retainAll 和 removeAll）没有 必要自动执行，除非在实现中特别说明。因此，举例来说，在只添加了 c 中的一些元素后，addAll(c) 有可能失败（抛出一个异常）。 

 * BlockingQueue 实质上不 支持使用任何一种“close”或“shutdown”操作来指示不再添加任何项。这种功能的需求和使用有依赖于实现的倾向。例如，一种常用的策略是：对于生产者，插入特殊的 end-of-stream 或 poison 对象，并根据使用者获取这些对象的时间来对它们进行解释。 
 * 
 * 
 * @author flammulinaBlog
 */
public class ConsumerProducer {

    //消费者
    static class Consumer implements Runnable {
        private BlockingQueue blockingQueue;

        private String name;

        private CountDownLatch latch;

        public Consumer(BlockingQueue blockingQueue, String name, CountDownLatch latch) {
            this.blockingQueue = blockingQueue;
            this.name = name;
            this.latch = latch;
        }


        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(500L);
                    //阻塞式消费
                    Object val = blockingQueue.take();
                    System.out.println(name + "消费信息：" + val);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    //生产者
    static class Producer implements Runnable {
        private final BlockingQueue blockingQueue;

        private CountDownLatch latch;
        public Producer(BlockingQueue queue, CountDownLatch latch) {
            this.blockingQueue = queue;
            this.latch = latch;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                try {
                    //生产消息(阻塞)
                    blockingQueue.put(i + ":message");

                    System.out.println("生产消息：" + i + ":message");
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(100);


        BlockingQueue queue = new ArrayBlockingQueue(15);
        Producer producer = new Producer(queue, latch);
        new Thread(producer).start();


        for (int i = 0; i < 20; i++) {
            Consumer consumer = new Consumer(queue, "consumer" + i, latch);
            new Thread(consumer).start();
        }
        latch.await();
        System.out.println("执行完成");
    }
}
