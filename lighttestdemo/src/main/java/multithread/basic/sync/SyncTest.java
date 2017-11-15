package multithread.basic.sync;

/**
 * synchronized 的使用方法
 *
 * @author wzm
 * @create 2017-11-15-11:34
 */
public class SyncTest implements Runnable{
    static SyncTest sync = new SyncTest();
    static int i = 0;
    //在进入increase方法之前必须获得当前对象实例的锁-sync
    public synchronized void increase(){
        i++;
    }
    @Override
    public void run() {
        for (int j = 0;j< 10000000;j++){
            increase();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //t1 和 t2都指向同一个runnable接口实例 - sync对象，这样才能保证两个线程在工作时，能够关注到同一个对象锁上，保证线程安全
        Thread t1 = new Thread(sync);
        Thread t2 = new Thread(sync);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("final i = "+ i);
    }
}
