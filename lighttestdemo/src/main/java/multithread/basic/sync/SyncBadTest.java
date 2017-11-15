package multithread.basic.sync;

/**
 * 错误的同步方式--包括重构后的解决方式
 *
 * @author wzm
 * @create 2017-11-15-11:47
 */
public class SyncBadTest implements Runnable{

    static SyncBadTest sync = new SyncBadTest();
    static int i = 0;
    //在进入increase方法之前必须获得当前对象实例的锁
    public synchronized void increase(){
        i++;
    }
    //重构后-作用于静态方法，需要请求当前类的锁，而非实例
/*    public synchronized static void increase(){
        i++;
    }*/
    @Override
    public void run() {
        for (int j = 0;j< 10000000;j++){
            increase();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //此时t1 和 t2 指向了不同的runnable实例，不能保证线程安全
        Thread t1 = new Thread(new SyncBadTest());
        Thread t2 = new Thread(new SyncBadTest());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("final i = "+ i);
    }
}
