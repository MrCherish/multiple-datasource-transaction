package com.xtt.transaction.threadPool;

import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * ForkJoinThreadPool
 *
 * @author dexu.tian
 * @date 2021/1/25
 */
public class ForkJoinThreadPool {


    public static void main(String[] args) {

        List<PooVolidator> pooVolidators = new ArrayList<>();

        PooVolidator pooVolidator1 = new PooVolidator() {
            @Override
            public Boolean volidate(Integer num) {
                return num % 2 == 0;
            }
        };

        PooVolidator pooVolidator2 = new PooVolidator() {
            @Override
            public Boolean volidate(Integer num) {
                return num % 4 == 0;
            }
        };

        pooVolidators.add(pooVolidator1);
        pooVolidators.add(pooVolidator2);

        int data = 4;
        MyPooltask pooltask =new MyPooltask(pooVolidators,data);

        ForkJoinPool forkJoinPool = new ForkJoinPool(2);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Future<Boolean> res = forkJoinPool.submit(pooltask);
        try {
            System.out.println(res.get());
            stopWatch.stop();
            System.out.println(stopWatch.getTotalTimeMillis());;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public static class MyPooltask extends RecursiveTask<Boolean> {

        private static final int THRESHOLD = 2;

        private List<PooVolidator> pooVolidators;

        private Integer data;

        private Integer index;

        public MyPooltask(List<PooVolidator> pooVolidators,Integer data) {
            this.pooVolidators = pooVolidators;
            this.data = data;
        }

        public MyPooltask(List<PooVolidator> pooVolidators, Integer data, Integer index) {
            this.pooVolidators = pooVolidators;
            this.data = data;
            this.index = index;
        }

        @Override
        protected Boolean compute() {
            int size = pooVolidators.size();

            if (size < THRESHOLD) {
                return pooVolidators.get(0).volidate(data);
            }

            int mid = size / 2;

            MyPooltask pooltask1 = new MyPooltask(pooVolidators.subList(0,mid),data);
            MyPooltask pooltask2 = new MyPooltask(pooVolidators.subList(mid,size),data);

//            pooltask1.fork();
//            pooltask2.fork();

            invokeAll(pooltask1,pooltask2);

            try {
                return pooltask1.get() && pooltask2.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            return false;
        }
    }

    interface PooVolidator {

        /**
         *
         * @return
         */
        Boolean volidate(Integer num);
    }

}
