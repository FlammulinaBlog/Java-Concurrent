package com.FlammulinaBlog.Java.Concurrent;

import java.util.concurrent.TimeUnit;

/**
 * 休眠工具类
 * @author flammulinaBlog
 */
public class SleepUtil {

    /**
     * 以秒单位休眠
     *
     * @param second
     */
    public static final void second(long second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 以毫秒单位休眠
     *
     * @param millisecond
     */
    public static final void millisecond(long millisecond) {
        try {
            TimeUnit.MILLISECONDS.sleep(millisecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
