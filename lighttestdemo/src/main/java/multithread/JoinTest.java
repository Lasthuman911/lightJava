package multithread;

/**
 * join 的功能说明
 *
 * @author wzm
 * @create 2017-11-14-17:07
 */
public class JoinTest {
    public volatile static int i = 0;
    public static class AddThread extends Thread{
        @Override
        public void run(){
            for (i = 0;i< 10000;i++) {
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new AddThread();
        t1.start();
        //主线程等待t1执行完毕
        t1.join();
        //i 永远是10000
        System.out.println("i="+ i);
    }
}
