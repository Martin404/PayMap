package com.hugnew.core.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ID生成
 * Created by Martin on 2016/7/01.
 */
public class IdGenerator {
    private static final DateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    private static final Random r = new Random();
    private static char[] A2Z = null;

    static {
        int j = 0;
        A2Z = new char[26];
        for (int i = 65; i < 91; i++) {
            A2Z[j] = (char) i;
            j++;
        }
    }

    public static String getTargetId() {
        char[] temp = new char[5];
        for (int i = 0; i < 5; i++) {
            temp[i] = A2Z[r.nextInt(26)];
        }
        String string = new String(temp);
        Integer max = 999999;
        Integer min = 10000;
        int s = r.nextInt(max) % (max - min + 1) + min;
        return string + s;

    }

    public static String getTranSid() {
        Lock lock = new ReentrantLock();
        lock.lock();
        String temp = null;
        AtomicInteger atomicInteger = new AtomicInteger();
        try {
            String currDate = format.format(new Date());
            Integer max = 999;
            Integer min = 100;
            int s = r.nextInt(max) % (max - min + 1) + min;
            temp = currDate + String.valueOf(s);

        } finally {
            lock.unlock();
        }
        return temp;
    }

    public static String getTranSid19() {
        Lock lock = new ReentrantLock();
        lock.lock();
        String temp = null;
        AtomicInteger atomicInteger = new AtomicInteger();
        try {
            String currDate = format.format(new Date());
            Integer max = 99;
            Integer min = 10;
            int s = r.nextInt(max) % (max - min + 1) + min;
            temp = currDate + String.valueOf(s);
        } finally {
            lock.unlock();
        }
        return temp;
    }

    public static String getIcbcTimeStamp() {
        DateFormat dateFormatStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Lock lock = new ReentrantLock();
        lock.lock();
        String temp = null;
        AtomicInteger atomicInteger = new AtomicInteger();
        try {
            String currDate = dateFormatStamp.format(new Date());
            Integer max = 999999;
            Integer min = 100000;
            int s = r.nextInt(max) % (max - min + 1) + min;
            temp = currDate + "." + String.valueOf(s);
        } finally {
            lock.unlock();
        }
        return temp;
    }

    public static String getIcbcReqSid() {
        DateFormat formatSid = new SimpleDateFormat("yyyyMMddHHmmss");
        Lock lock = new ReentrantLock();
        lock.lock();
        String temp = null;
        AtomicInteger atomicInteger = new AtomicInteger();
        try {
            String currDate = format.format(new Date());
            Integer max = 9999;
            Integer min = 1000;
            int s = r.nextInt(max) % (max - min + 1) + min;
            temp = currDate + "." + String.valueOf(s);
        } finally {
            lock.unlock();
        }
        return temp;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.err.println(getTargetId());
        }
    }
}
