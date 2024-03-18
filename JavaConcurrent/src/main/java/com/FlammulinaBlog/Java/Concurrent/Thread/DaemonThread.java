package com.FlammulinaBlog.Java.Concurrent.Thread;

public class DaemonThread {

        public static void main(String[] args) {
            // 创建一个守护线程
            Thread daemonThread = new Thread(() -> {
                while (true) {
                    System.out.println("Daemon thread is running...");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            // 将线程设置为守护线程
            // 设置线程名称
            daemonThread.setName("DaemonThread");
            daemonThread.setDaemon(true);


            // 打印主线程信息
            Thread mainThread = Thread.currentThread();
            System.out.println("Main thread name: " + mainThread.getName());
            System.out.println("Main thread ID: " + mainThread.getId());


            // 启动线程
            daemonThread.start();

            // 主线程休眠一段时间，模拟其他用户线程的运行
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Main thread exiting...");
        }
    }
