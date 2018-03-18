package multithread.middle;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量
 * 和sync 或者 ReentrantLock不同点：可以指定多个线程同时访问同一个资源
 * Created by lszhen on 2018/1/27.
 */
public class SemapDemo implements Runnable{

    //指定n个线程同时访问同一个资源
    final Semaphore semaphore = new Semaphore(3);
    @Override
    public void run() {
        try {
            semaphore.acquire();
            //模拟耗时操作
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getId()+":done!");
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExecutorService extc = Executors.newFixedThreadPool(20);
        final SemapDemo semapDemo = new SemapDemo();
        for (int i=0;i<20;i++){
            extc.submit(semapDemo);
        }
    }
}
