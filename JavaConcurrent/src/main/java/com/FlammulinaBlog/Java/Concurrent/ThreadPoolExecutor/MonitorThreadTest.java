package com.FlammulinaBlog.Java.Concurrent.ThreadPoolExecutor;

import java.util.concurrent.*;

/**
 * 
 * ThreadFactory 根据需要创建新线程的对象。使用线程工厂就无需再手工编写对 new Thread 的调用了，
 *  			   从而允许应用程序使用特殊的线程子类、属性等等
 * 
 * @author flammulinaBlog
 */
public class MonitorThreadTest {

    public static void main(String[] args) throws InterruptedException {

        RejectedExecutionHandlerImpl rejected = new RejectedExecutionHandlerImpl();

        ThreadFactory tf = Executors.defaultThreadFactory();

        ThreadPoolExecutor executorPool = new ThreadPoolExecutor(300, 300, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), tf, rejected);

        MonitorThread monitor = new MonitorThread(executorPool, 3);

        Thread monitorThread = new Thread(monitor);

        monitorThread.start();

        for (int i = 0; i < 10000000; i++) {

            executorPool.execute(new WorkThread());

        }

        Thread.sleep(5000);
        executorPool.shutdown();

        while (!executorPool.isTerminated()) {
        }

        Thread.sleep(3000);
        monitor.shutdown();
    }

}
