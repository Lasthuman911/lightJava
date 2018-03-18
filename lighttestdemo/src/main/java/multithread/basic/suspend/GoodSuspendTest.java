package multithread.basic.suspend;

/**
 * BadSuspend重构后：添加一个标记状态
 * @author wzm
 * @create 2017-11-14-16:41
 */
public class GoodSuspendTest {
    public static Object obj = new Object();

    public static class ChangeObjectThread extends Thread {
        volatile boolean suspendme = false;

        public void suspendMe() {
            suspendme = true;
        }

        public void resumeMe() {
            suspendme = false;
            synchronized (this) {
                notify();
            }
        }

        @Override
        public void run() {
            while (true) {
                //可以思考下这里为什么要用this不用obj----目的只让当前线（changeObj的线程）程等待，
                // 而不是让挂在obj上的线程等待
                synchronized (this) {
                    while (suspendme) {
                        try {
                            //执行wait后，当前对象要notify后才会继续往下执行
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                synchronized (obj) {
                    System.out.println("in ChangeObjectThread");
                    try {
                        //为了看输出，自己加的
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Thread.yield();
            }
        }
    }

    public static class ReadObjectThread extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (obj) {
                    System.out.println("in ReadObjectThread");
                    try {
                        //为了看输出，自己加的
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ChangeObjectThread t1 = new ChangeObjectThread();
        ReadObjectThread t2 = new ReadObjectThread();
        System.out.println("begin");
        t1.start();
        t2.start();
        Thread.sleep(1000);
        t1.suspendMe();
        System.out.println(System.currentTimeMillis()+" suspend t1 2s");
        //2s后才唤醒t1线程，这时t2 还是一直在跑的，正常情况下，能看到两条t2的输出
        Thread.sleep(2000);
        System.out.println(System.currentTimeMillis()+" resume t1");
        t1.resumeMe();
        System.out.println("end");
    }
}
