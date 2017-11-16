package multithread.middle;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 中断响应，解决死锁问题
 *
 * @author wzm
 * @create 2017-11-16-9:44
 */
public class IntLock implements Runnable {
    public static ReentrantLock lock1 = new ReentrantLock();
    public static ReentrantLock lock2 = new ReentrantLock();
    int lock;

    public IntLock(int lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        try{
            if (lock == 1){
                //lockInterruptibly：可以对中断进行响应的锁申请动作，在等待锁的过程中，可以响应中断
                lock1.lockInterruptibly();
                try {
                    Thread.sleep(500);
                }catch (InterruptedException e){}
                lock2.lockInterruptibly();
            }else {
                lock2.lockInterruptibly();
                try {
                    Thread.sleep(500);
                }catch (InterruptedException e){}
                lock1.lockInterruptibly();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            // 查询当前线程是否保持此锁
            if (lock1.isHeldByCurrentThread()){
                lock1.unlock();
            }
            if (lock2.isHeldByCurrentThread()){
                lock2.unlock();
            }
            System.out.println(Thread.currentThread().getName() +"线程退出");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        IntLock r1 = new IntLock(1);
        IntLock r2 = new IntLock(2);
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();
        Thread.sleep(1000);
        //中断其中一个线程：放弃申请锁，并释放已持有的锁
        t2.interrupt();
    }
}
