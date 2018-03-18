package multithread.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 固定间隔时间的串行任务
 * 注意两个方法之间的时间间隔
 * 因为是串行的，所以若任务遇到异常，后面的任务都会停止调度
 * 所以必须保证异常被及时处理，为周期性的任务稳定调度提供条件
 * Created by lszhen on 2018/1/29.
 */
public class ScheduledExecutorServiceDemo {
    public static void main(String[] args) {
      ScheduledExecutorService exec =  Executors.newScheduledThreadPool(10);
//      exec.scheduleAtFixedRate(new Runnable() {
      exec.scheduleWithFixedDelay(new Runnable() {
          @Override
          public void run() {
              try {
                  Thread.sleep(3000);
                  System.out.println(System.currentTimeMillis()/1000);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }
      },1,2, TimeUnit.SECONDS);
    }
}
