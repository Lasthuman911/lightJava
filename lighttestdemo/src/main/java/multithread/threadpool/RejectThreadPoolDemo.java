package multithread.threadpool;

import java.util.concurrent.*;

/**
 * 自定义线程池、线程工厂、拒绝策略、扩展
 * Created by lszhen on 2018/1/31.
 */
public class RejectThreadPoolDemo {
    public static class MyTask implements Runnable {
        public String taskName;

        public MyTask(String name) {
            taskName = name;
        }

        @Override
        public void run() {

            //获取可用cup数量，最优线程池的大小 = N*N*（1+w/c）N：cup数量 w：等待时间，c：计算时间
            int cpuNums = Runtime.getRuntime().availableProcessors();
            System.out.println("可用cup数量 = "+cpuNums);

            System.out.println("----执行任务，当前时间"+System.currentTimeMillis() + ":Thread ID = " +
                    Thread.currentThread().getId());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {

        //自定义线程工厂
        ThreadFactory myThreadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                //主线程退出后，将强制销毁线程池
                t.setDaemon(true);
                //记录线程的创建
                System.out.println("线程池初始create " + t);
                return t;
            }
        };
//       ThreadFactory myThreadFactory = new MyThreadFacory("wzm factory");
        //自定义线程池
        ExecutorService es = new ThreadPoolExecutor(5, 5,
                0L, TimeUnit.MILLISECONDS,
                //队列中线程数若超过10，并且线程池中线程达到了5，后续再要加进来，就会执行拒绝策略
                new LinkedBlockingDeque<Runnable>(10),
                //使用系统默认的线程工厂创建线程
//                Executors.defaultThreadFactory(),
                //使用自定义线程工厂创建线程
                myThreadFactory,
                //自定义拒绝策略
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        System.out.println(r.toString() + " is discard");
                    }
                }) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println("准备执行"+((MyTask)r).taskName);
            }

            //TODO 如果触发了拒绝策略，这方法不会被执行到？
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println("执行完成"+((MyTask)r).taskName+"\n");
            }

            //TODO 如果触发了拒绝策略，这方法不会被执行到？
            @Override
            protected void terminated() {
                System.out.println("线程池退出");
            }
        };

        for (int i = 0; i < 10; i++) {
            MyTask myTask = new MyTask("task-"+i);
//            es.submit(myTask);//若用到了扩展的before 等，使用此方式报错
            es.execute(myTask);
            Thread.sleep(1000);
        }
        //不会暴力停止线程，当调用了shutDown方法后，线程池就不会再接收任务,可以调节下sleep 时间 看看结果
        es.shutdown();
    }
}
