package multithread.middle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 为没一个线程分配不同的对象，需要在应用层面保证，threadLocal只是充当容器
 * SimpleDateFormat 并不是线程安全的，在线程池共享此对象将导致失败
 * Created by lszhen on 2018/2/1.
 */
public class ThreadLocalDemoRe1 {
    //threadLocal只是充当容器
    static ThreadLocal<SimpleDateFormat> simpleDateLocal = new ThreadLocal<>();
    public static class ParseDate implements Runnable{
        int i = 0;

        public ParseDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            Date t = null;
            try {
                if (simpleDateLocal.get() == null){
                    simpleDateLocal.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                }
                Date date = simpleDateLocal.get().parse("2015-03-29 19:29:"+i%60);
                System.out.println(i+": "+date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(10);
        for (int i = 0;i<1000;i++){
            es.execute(new ParseDate(i));
        }
    }
}
