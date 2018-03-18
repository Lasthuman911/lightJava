package multithread.middle;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁的的公平锁demo
 * Created by lszhen on 2018/1/27.
 */
public class FairLock implements Runnable{
    //true 表示公平锁，一般情况都使用非公平锁，公平锁要额外维护一个有序队列，影响性能
    public static ReentrantLock fairLock = new ReentrantLock(true);

    @Override
    public void run() {
       for (int i = 0;i<1000;i++){
           try {
               fairLock.lock();
               System.out.println(Thread.currentThread().getName()+" 获得锁");
           }finally {
               fairLock.unlock();
           }
       }
    }

    public static void main(String[] args) {
        FairLock t = new FairLock();
        Thread t1 = new Thread(t,"t1");
        Thread t2 = new Thread(t,"t2");
        t1.start();
        t2.start();
    }
}
