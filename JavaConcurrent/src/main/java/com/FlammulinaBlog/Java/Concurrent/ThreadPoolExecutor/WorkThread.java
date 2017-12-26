package com.FlammulinaBlog.Java.Concurrent.ThreadPoolExecutor;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author flammulinaBlog
 */
public class WorkThread implements Runnable {

    AtomicInteger atomInteger = new AtomicInteger(0);

    public void run() {
        System.out.println(Thread.currentThread().getName() + " counts:" + atomInteger.incrementAndGet());
    }

}
