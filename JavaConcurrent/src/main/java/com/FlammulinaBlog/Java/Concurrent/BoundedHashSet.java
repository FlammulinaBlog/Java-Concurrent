package com.FlammulinaBlog.Java.Concurrent;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * Semaphore >0许可   =0不许可
 * 使用Semaphore为容器设置许可 从而控制运行
 * <p/>
 * @author flammulinaBlog
 */
public class BoundedHashSet<T> {

    private final Set<T> set;
    private final Semaphore sem;

    public BoundedHashSet(int bound) {
        this.set = Collections.synchronizedSet(new HashSet<T>());
        this.sem = new Semaphore(bound);
    }

    public boolean add(T o) throws InterruptedException {
        sem.acquire();
        boolean result = false;
        try {
            result = set.add(o);
            System.out.println("添加成功");
            return result;
        } finally {
            if (!result) {
                sem.release();
            }
        }
    }

    public boolean remove(T o) {
        boolean result = set.remove(o);
        if (result) {
            sem.release();
            System.out.println("删除成功");
        }
        return result;
    }
    
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
    	
    	BoundedHashSet test=new BoundedHashSet(1);
    	try {
			if(test.add("new")) {
				test.remove("new");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
