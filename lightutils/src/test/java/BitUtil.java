import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by lszhen on 2017/11/6.
 */
public class BitUtil
    {

        public static long timeTask(int nThreads, int singleNum, final Runnable task) {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);
        ThreadFactory tf = Executors.defaultThreadFactory();
        final int singleExeNum = singleNum == 0 ? 1 : singleNum;
        final AtomicLong sum = new AtomicLong();
        final AtomicLong min = new AtomicLong(10000);

        for (int i = 0;i<nThreads; i++){
            tf.newThread(new Thread(){
                public void run(){
                    try{
                        startGate.await();
                        for (int j = 0;j<singleExeNum;j++){
                            long start = System.nanoTime();
                            try{
                                task.run();
                            }finally {
                                long end = System.nanoTime();
                                long st = (end - start)/1000/1000;
                                sum.addAndGet(st);
                                if (min.get() > st){
                                    min.getAndAdd(st);
                                }
                            }
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        return 0;
    }
}
