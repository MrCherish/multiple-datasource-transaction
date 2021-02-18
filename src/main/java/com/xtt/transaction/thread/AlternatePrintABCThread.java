package com.xtt.transaction.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 三个线程交替打印abc
 *
 * @author dexu.tian
 * @date 2021/2/18
 */
public class AlternatePrintABCThread {

    /**
     * 控制打印次数
     */
    private Integer times;

    /**
     * 当前期望值
     */
    private Integer state;

    private Lock lock = new ReentrantLock();

    public AlternatePrintABCThread(Integer times) {
        this.times = times;
        this.state = 0;
    }

    private void printName(String name,Integer targetNum) {

        for (int i = 0 ; i < times ;) {
            lock.lock();
            try {
                if (state % 3 == targetNum) {
                    state ++;
                    i++;
                    System.out.println(name);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {

        AlternatePrintABCThread thread = new AlternatePrintABCThread(2);

        new Thread(() -> {
            thread.printName("A",0);
        },"A").start();

        new Thread(() -> {
            thread.printName("B",1);
        },"B").start();

        new Thread(() -> {
            thread.printName("C",2);
        },"C").start();

    }
}
