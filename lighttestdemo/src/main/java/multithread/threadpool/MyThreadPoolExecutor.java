package multithread.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义线程池
 * @author lszhen 2018/1/31.
 */
public class MyThreadPoolExecutor extends ThreadPoolExecutor {
    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                                TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    public void execute(Runnable task) {
        Exception e = clientTrace();
        super.execute(wrap(task,e));
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(wrap(task,clientTrace()));
    }

    private Exception clientTrace(){
        Exception e = new Exception("Client stack track");
        return e;
    }

    //clientStack保存着提交任务的线程的堆栈信息
    private Runnable wrap(final Runnable task,final Exception clientStack){
        return new Runnable() {
            @Override
            public void run() {
                try {
                    task.run();
                }catch (Exception e){
                    //这里只是将调用的来源打印出来
                    clientStack.printStackTrace();
                    //run中的异常向外抛，由JVM捕获，默认会打印在console控制台中
                    throw e;
                }
            }
        };
    }

}
