package com.xtt.transaction.thread;

import java.util.concurrent.Semaphore;

/**
 * AlternatePrintA2ZThread
 *
 * @author dexu.tian
 * @date 2021/2/18
 */
public class AlternatePrintA2ZThread {

    private static Semaphore semaphore1 = new Semaphore(1);
    private static Semaphore semaphore2 = new Semaphore(0);
    private static Semaphore semaphore3 = new Semaphore(0);

    public static void print(String name ,Semaphore current,Semaphore next) {
        for (int i = 0 ; i < 5; i++ ) {
            try {
                current.acquire();
                System.out.println(name);
                next.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        new Thread(() -> {
            print("A",semaphore1,semaphore2);
        }).start();

        new Thread(() -> {
            print("B",semaphore2,semaphore3);
        }).start();

        new Thread(() -> {
            print("C",semaphore3,semaphore1);
        }).start();
    }

}
