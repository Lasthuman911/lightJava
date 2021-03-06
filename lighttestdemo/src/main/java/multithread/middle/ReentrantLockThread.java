package multithread.middle;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁-简单用法
 *
 * @author wzm
 * @create 2017-11-15-14:25
 */
public class ReentrantLockThread implements Runnable {
    /**创建锁*/
    public static ReentrantLock lock = new ReentrantLock();
    public static int i = 0;
    @Override
    public void run() {
        for (int j=0;j<10000000;j++){
            //加锁
            lock.lock();
            try{
                i++;
            }finally {
                //释放锁
                lock.unlock();
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockThread thread = new ReentrantLockThread();
        ReentrantLockThread thread1 = new ReentrantLockThread();
        Thread t1 = new Thread(thread);
        Thread t2 = new Thread(thread1);//这里用thread 或thread1 都没关系，因为有锁机制
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("final i = "+ i);
    }
}
