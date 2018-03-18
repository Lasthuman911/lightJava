package multithread.threadpool;

import java.util.concurrent.*;

/**
 * 使用默认的线程池如果用submit则会将错误隐藏
 * Created by lszhen on 2018/1/31.
 */
public class DivTask implements Runnable {
    int a;
    int b;

    public DivTask(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void run() {
        double re = a / b;
        System.out.println(re);
    }

    public static void main(String[] args) {
//        ExecutorService es = new ThreadPoolExecutor(0,
//                Integer.MAX_VALUE,0L, TimeUnit.MILLISECONDS,
//                new SynchronousQueue<>());
        ExecutorService es = new MyThreadPoolExecutor(0,
                Integer.MAX_VALUE, 0L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<>());

        for (int i = 0; i < 5; i++) {
//            es.submit(new DivTask(100,i));
            es.execute(new DivTask(100, i));
        }
    }
}
