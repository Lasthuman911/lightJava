package multithread.basic.trap;

import java.util.ArrayList;
import java.util.Vector;

/**
 * 线程不安全的ArrayList
 *
 * 会有3中不同的结果：
 *  1. 正常结束
 *  2. 抛异常 ArrayIndexOutOfBoundException：ArrayList扩容，内部一致性破坏，由于没有锁的保护，另一个线程访问到了不一致的内部状态，导致下标越界
 *  3. 打印出小于2000000 的长度值，而且并无报错
 * 解决方法：
 *  使用线程安全的Vector来代替ArrayList
 *  TODO 研究下源码
 * @author wzm
 * @create 2017-11-15-13:38
 */
public class ArrayListMultiThread {
    static ArrayList<Integer> arrayList = new ArrayList<Integer>(10);
//    static Vector<Integer> arrayList = new Vector<Integer>(10);
    public static class AddThread implements Runnable{

        @Override
        public void run() {
            for (int i = 0;i< 1000000;i++){
                arrayList.add(i);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new AddThread());
        Thread t2 = new Thread(new AddThread());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(arrayList.size());
    }
}
