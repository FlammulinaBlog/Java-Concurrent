package com.FlammulinaBlog.Java.Concurrent.ThreadLocal;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * ThreadLocal  不是用来解决对象共享访问问题的，而主要是提供了线程保持对象的方法和避免参数传递的方便的对象访问方式 
 *  			应用场合，最适合的是按线程多实例（每个线程对应一个实例）的对象的访问，并且这个对象很多地方都要用到。
 * 
 * AtomicInteger 一个提供原子操作的Integer的类。在Java语言中，++i和i++操作并不是线程安全的，在使用的时候，
 * 				  不可避免的会用到synchronized关键字。而AtomicInteger则通过一种线程安全的加减操作接口。
 * 
 * @author flammulinaBlog
 */
public class ThreadLocalTest {

//  static volatile int count = 0;
    static AtomicInteger count = new AtomicInteger();

    static class MyRunnable implements Runnable {
        ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();

        @Override
        public void run() {
            threadLocal.set((int) (Math.random() * 10));
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " value is " + threadLocal.get() + " , count is " + count + " ,date is " + new Date());

        }
    }


    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new MyRunnable(), "Thread-01");
        Thread t2 = new Thread(new MyRunnable(), "Thread-02");

        t1.start();
        t1.join();
        t2.start();
        t2.join();
    }
}
