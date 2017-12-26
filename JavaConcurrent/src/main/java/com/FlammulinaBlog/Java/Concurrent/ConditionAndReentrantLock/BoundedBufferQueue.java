package com.FlammulinaBlog.Java.Concurrent.ConditionAndReentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * RentrantLock  提供了synchronized的所有功能，而且比synchronized更加地灵活；
 * 
 * 
 * 有界阻塞缓冲队列
 * @author flammulinaBlog
 */
public class BoundedBufferQueue<E> {

    private Object[] buffer;

    private final ReentrantLock lock;

    private final Condition notEmpty;

    private final Condition notFull;

    private int count;

    private int putIndex;

    private int takeIndex;

    /**
     * 初始化队列大小
     *
     * @param size
     */
    public BoundedBufferQueue(int size) {
        buffer = new Object[size];
        lock = new ReentrantLock();
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();
    }

    public void put(E e) throws InterruptedException {
        lock.lock();
        try {
            while (count == buffer.length) {
                notFull.await();
            }
            buffer[putIndex] = e;

            if (++putIndex == buffer.length) {
                putIndex = 0;
            }
            count++;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public E take() throws InterruptedException {
        lock.lock();

        try {
            while (count == 0) {
                notEmpty.await();
            }
            E e = (E) buffer[takeIndex];
            count--;

            if (++takeIndex == buffer.length) {
                takeIndex = 0;
            }
            notFull.signal();
            return e;
        } finally {
            lock.unlock();
        }
    }
}
