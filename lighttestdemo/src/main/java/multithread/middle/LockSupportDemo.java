package multithread.middle;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.LockSupport;

/**
 * 线程阻塞工具类，用来代替suspend
 * Created by lszhen on 2018/1/29.
 */
public class LockSupportDemo {
    public static Object u = new Object();
    public static ChangeObjeThread t1 = new ChangeObjeThread("t1");
    public static ChangeObjeThread t2 = new ChangeObjeThread("t2");
    public static class ChangeObjeThread extends Thread{
        public ChangeObjeThread(String name) {
            super.setName(name);
        }

        @Override
        public void run() {
            synchronized (u){
                System.out.println("in "+getName());
                //类似suspend,阻塞当前线程,许可不可用，线程阻塞
                //正常情况park在前，unpark在后，先阻塞，再将许可变为可用
                //若unpark在前，许可可用，park在许可可用的情况下，将消费这个许可，然后直接返回，并不会将线程阻塞
                LockSupport.park();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(100);
        t2.start();
        LockSupport.unpark(t1);
        //将许可变为可用
        LockSupport.unpark(t2);
        t1.join();
        t2.join();
        new CopyOnWriteArrayList<>();
    }
}
