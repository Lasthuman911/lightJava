package multithread.middle;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 在多线程环境产生随机数性能测试
 * Created by lszhen on 2017/1/7.
 */
public class RandomNatureInMulityThread implements Callable<Long> {
    /**
     * 每个线程要产生的随机数数量
     */
    public static final int GEN_COUNT = 1000000;
    public static final int THREAD_COUNT = 4;
    static ExecutorService exe = Executors.newFixedThreadPool(THREAD_COUNT);
    public static Random random = new Random(123);
    public static ThreadLocal<Random> threadLocalRandom = new ThreadLocal<Random>() {
        @Override
        protected Random initialValue() {
            return new Random(123);
        }
    };

    private int mode = 0;

    public RandomNatureInMulityThread(int mode) {
        this.mode = mode;
    }

    /**
     * 不同的策略情况下，随机数的产生逻辑
     */
    public Random getRandom() {
        if (mode == 0) {
            return random;
        } else if (mode == 1) {
            return threadLocalRandom.get();
        } else {
            return null;
        }
    }

    /**
     * todo 加synchronized  结果完全不同
     */
    @Override
    public Long call() throws Exception {
        long startTime = System.currentTimeMillis();
//        synchronized (random){
//            long startTime = System.currentTimeMillis();
            //实际工作内容：产生若干个随机数
            for (long i = 0; i < GEN_COUNT; i++) {
                getRandom().nextInt();
            }

            long endTime = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + " 花费时间：" + (endTime - startTime) + "ms");
            return endTime - startTime;
//        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Future<Long>[] futures = new Future[THREAD_COUNT];
       for (int i = 0;i<THREAD_COUNT;i++){
            futures[i] = exe.submit(new RandomNatureInMulityThread(0));
        }
        long totalTime = 0;
        for (int i= 0;i<THREAD_COUNT;i++){
            totalTime += futures[i].get();
        }
        System.out.println("多线程访问同一个Random实例："+totalTime+"ms");

        //ThreadLocal情况
        for (int i=0;i<THREAD_COUNT;i++){
            futures[i] = exe.submit(new RandomNatureInMulityThread(1));
        }
        totalTime = 0;

        for (int i = 0;i<THREAD_COUNT;i++){
            totalTime += futures[i].get();
        }
        System.out.println("使用ThreadLocal包装的Random实例："+totalTime+"ms");
        exe.shutdown();
    }
}
/***
 pool-1-thread-4 花费时间：1324ms
 pool-1-thread-3 花费时间：1378ms
 pool-1-thread-2 花费时间：1515ms
 pool-1-thread-1 花费时间：1518ms
 多线程访问同一个Random实例：5735ms
 pool-1-thread-4 花费时间：192ms
 pool-1-thread-3 花费时间：195ms
 pool-1-thread-1 花费时间：249ms
 pool-1-thread-2 花费时间：229ms
 使用ThreadLocal包装的Random实例：865ms
 */
