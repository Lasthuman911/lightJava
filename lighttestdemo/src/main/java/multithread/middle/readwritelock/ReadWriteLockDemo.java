package multithread.middle.readwritelock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读操作远大与写操作，采用读写锁可以提高性能
 * Created by lszhen on 2018/1/29.
 */
public class ReadWriteLockDemo {
    //普通的重入锁
    public static Lock lock = new ReentrantLock();
    public static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    //读锁
    public static Lock readLock = readWriteLock.readLock();
    //写锁
    public static Lock writeLock = readWriteLock.writeLock();

    private int value;

    /**
     * 模拟读操作
     */
    public Object getValue(Lock lock) throws InterruptedException {
        try{
            lock.lock();
            System.out.println(Thread.currentThread().getId()+"读");
            Thread.sleep(1000);
            return value;
        }finally {
            lock.unlock();
        }
    }
    /**
     * 模拟写操作
     */
    public void writeValue(Lock lock,int value) throws InterruptedException {
        try{
            lock.lock();
            System.out.println(Thread.currentThread().getId()+"写");
            Thread.sleep(1000);
            value = this.value;
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final ReadWriteLockDemo demo = new ReadWriteLockDemo();
        Runnable readRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    demo.getValue(readLock);
//                    demo.getValue(lock)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable writeRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    demo.writeValue(writeLock,new Random().nextInt());
//                    demo.writeValue(lock,new Random().nextInt());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        for (int i = 0; i< 20;i++){
            new Thread(readRunnable).start();
        }

        for (int j= 0;j<2;j++){
            new Thread(writeRunnable).start();
        }

    }
}
