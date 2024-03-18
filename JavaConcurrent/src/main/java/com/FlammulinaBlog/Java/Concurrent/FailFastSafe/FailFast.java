package com.FlammulinaBlog.Java.Concurrent.FailFastSafe;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class FailFast {

    public static void main(String[] args) {

        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        // 创建一个线程用于并发修改集合
        Thread modifyThread = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                list.add(i);
                System.out.println(i);
            }
        });
        modifyThread.start();
        System.out.println("1111");
        // 在主线程中遍历集合
        try {
            Iterator<Integer> iterator = list.iterator();
            while (iterator.hasNext()) {
                Integer num = iterator.next();
                System.out.println(num);
                Thread.sleep(10); // 模拟迭代过程中的一些耗时操作
            }
        } catch (ConcurrentModificationException | InterruptedException e) {
            System.out.println("ConcurrentModificationException caught: " + e.getMessage());
        }
    }


    }


