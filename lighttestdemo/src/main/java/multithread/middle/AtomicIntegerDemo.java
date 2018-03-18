package multithread.middle;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lszhen on 2018/2/2.
 */
public class AtomicIntegerDemo {
    static AtomicInteger i = new AtomicInteger();
    public static class AddThread implements Runnable{

        @Override
        public void run() {
            for (int k=0;k<10;k++){
                i.incrementAndGet();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] ts = new Thread[10];
        for (int a = 0;a<10;a++){
            ts[a] = new Thread(new AddThread());
        }
        for (int b = 0;b<10;b++){
            ts[b].start();
        }

        for (int c = 0;c<10;c++){
            ts[c].join();
        }

        System.out.println(i);
    }
}
