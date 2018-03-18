package multithread.middle;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 倒计时
 * Created by lszhen on 2018/1/29.
 */
public class CountDownLatchDemo implements Runnable{
    public static final CountDownLatch end = new CountDownLatch(10);
    public static final CountDownLatchDemo demo = new CountDownLatchDemo();

    @Override
    public void run(){
        try {
            //模拟检查人物
            Thread.sleep(new Random().nextInt(10)*1000);
            System.out.println("check Complete");
            //运行一次 减1
            end.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newFixedThreadPool(3);
        for (int i =0;i<10;i++){
            exec.submit(demo);
        }
        //等待检查
        end.await();
        //发射火箭
        System.out.println("Fire!");
        exec.shutdown();
    }
}
