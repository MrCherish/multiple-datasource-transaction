package com.xtt.transaction.service;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author dexu.tian
 * @date 2020-11-30
 */
public class OrderServiceImpl implements OrderService {


    public static void main(String[] args) {

        Lock lock = new ReentrantLock();
        Condition upperCondition =  lock.newCondition();
        Condition localCondition =  lock.newCondition();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        while (!printUpper.get()) {

                            // 当前线程加入 localCondition 等待对列，同时释放锁
                            localCondition.await();
                        }
                        System.out.println("thread-A:" + UPPERARR[getUpperIndex()]);
                        printUpper.compareAndSet(true, false);

                        // 通知 upperCondition 队列线程获取锁
                        upperCondition.signal();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        while (printUpper.get()) {
                            // 当前线程加入 upperCondition 等待对列，同时释放锁
                            upperCondition.await();
                        }
                        System.out.println("thread-B:" + LOCALARR[getLocalIndex()]);
                        printUpper.compareAndSet(false, true);

                        // 通知 localCondition 队列线程获取锁
                        localCondition.signal();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        });

        thread1.start();
        thread2.start();

    }

    private static AtomicBoolean printUpper = new AtomicBoolean(true);

    private static AtomicInteger upperIndex = new AtomicInteger(0);
    private static int getUpperIndex() {
        int i = upperIndex.get();
        if (i > 24) {
            i = 0;
            upperIndex.set(i);
        }
        return upperIndex.getAndIncrement();
    }

    private static AtomicInteger localIndex = new AtomicInteger(0);
    private static int getLocalIndex() {
        int i = localIndex.get();
        if (i > 24) {
            i = 0;
            localIndex.set(i);
        }
        return localIndex.getAndIncrement();
    }

    private static  String [] UPPERARR = new String[] {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","R","S","T","U","V","W","X","Y","Z"};
    private static  String [] LOCALARR = new String[] {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","r","s","t","u","v","w","x","y","z"};

}
