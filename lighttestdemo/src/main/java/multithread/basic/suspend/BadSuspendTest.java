package multithread.basic.suspend;

/**
 * jdk不建议使用方法之 suspend 和 resume
 *
 * @author wzm
 * @create 2017-11-14-16:16
 */
public class BadSuspendTest {
        public static Object obj = new Object();
        static ChangeObjectThread t1 = new ChangeObjectThread("t1");
        static ChangeObjectThread t2 = new ChangeObjectThread("t2");
    public static class ChangeObjectThread extends Thread{
        public ChangeObjectThread(String name) {
            super.setName(name);
        }

        @Override
        public void run(){
            synchronized(obj){
                System.out.println("in "+getName());
                Thread.currentThread().suspend();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(100);
        t2.start();
        t1.resume();
        t2.resume();
        // t.join()方法阻塞调用此方法的线程(calling thread)，直到线程t完成，此线程再继续；
        // 通常用于在main()主线程内，等待其它线程完成再结束main()主线程
        t1.join();
        t2.join();

    }
}
