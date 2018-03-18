package multithread.middle;

import javax.swing.plaf.PanelUI;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁限时等待
 * Created by lszhen on 2018/1/27.
 */
public class TimeLock implements Runnable {
    public static ReentrantLock lock = new ReentrantLock();
    @Override
    public void run() {
        try {
            //5秒钟未申请到锁，则放弃申请
            if (lock.tryLock(5, TimeUnit.SECONDS)){
            //不带参：未被其他thread占用则申请锁成功，否则直接返回false，它不会等待
//            if (lock.tryLock()){
                System.out.println(Thread.currentThread().getName()+"：得到锁");
                Thread.sleep(6000);
            }else {
                System.out.println(Thread.currentThread().getName()+" get lock failed");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if (lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        TimeLock t = new TimeLock();
        Thread t1 = new Thread(t);
        Thread t2 = new Thread(t);
        t1.start();
        t2.start();
    }
}
