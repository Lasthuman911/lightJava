package multithread.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 5个为一批，每批之间间隔1秒
 * Created by lszhen on 2018/1/29.
 */
public class ThreadPoolDemo{

    public static class MyTask implements Runnable{

        @Override
        public void run() {
            System.out.println(System.currentTimeMillis()+" current Thread id = "+Thread.currentThread().getId());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        MyTask task = new MyTask();
       ExecutorService exec =  Executors.newFixedThreadPool(5);
//       ExecutorService exec =  Executors.newCachedThreadPool();
       for (int i = 0;i<10;i++){
           exec.submit(task);
       }
    }
}
