package com.xtt.transaction.thread;

import java.util.concurrent.locks.LockSupport;

/**
 * AlternatePrintA2ZLockSupportThread
 *
 * @author dexu.tian
 * @date 2021/2/18
 */
public class AlternatePrintA2ZLockSupportThread {

    private static Thread threadA,threadB,threadC;

    public static void main(String[] args) {

        threadA = new Thread(() -> {

            System.out.println("A");

            // 唤醒线程B
            LockSupport.unpark(threadB);

            // 阻塞当前线程
            LockSupport.park();
        });

        threadB = new Thread(() -> {

            // 阻塞当前线程
            LockSupport.park();

            System.out.println("B");

            // 唤醒线程C
            LockSupport.unpark(threadC);

        });

        threadC = new Thread(() -> {

            // 阻塞当前线程
            LockSupport.park();

            System.out.println("C");

            // 唤醒线程B
            LockSupport.unpark(threadA);
        });

        threadA.start();
        threadB.start();
        threadC.start();

    }

}
