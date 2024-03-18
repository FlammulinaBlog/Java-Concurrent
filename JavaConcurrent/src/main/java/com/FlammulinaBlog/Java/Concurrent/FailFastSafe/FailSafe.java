package com.FlammulinaBlog.Java.Concurrent.FailFastSafe;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class FailSafe {
    public static void main(String[] args) {


        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        map.put(1, "One");
        map.put(2, "Two");
        map.put(3, "Three");

        // 创建一个线程用于并发修改集合
        Thread modifyThread = new Thread(() -> {
            for (int i = 4; i <= 6; i++) {
                map.put(i, "Number " + i);
                try {
                    Thread.sleep(100); // 模拟修改过程中的一些耗时操作
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        modifyThread.start();

        // 在主线程中遍历 ConcurrentHashMap
        try {
            Iterator<Integer> iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                Integer key = iterator.next();
                System.out.println(key + ": " + map.get(key));
                Thread.sleep(10); // 模拟迭代过程中的一些耗时操作
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}



