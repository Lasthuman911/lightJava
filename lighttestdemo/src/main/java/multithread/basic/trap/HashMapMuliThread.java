package multithread.basic.trap;

import java.util.HashMap;
import java.util.Map;

/**
 * hashMap线程不安全
 * 在jdk1.7 中容易出现死循环，1.8中有所改善
 * @author wzm
 * @create 2017-11-15-14:09
 */
public class HashMapMuliThread {
    static Map<String,String> map = new HashMap<String, String>();

    public static class AddThread implements Runnable{
        int addStart = 0;

        public AddThread(int addStart) {
            this.addStart = addStart;
        }

        @Override
        public void run() {
            for (int i = addStart;i< 100000;i+=2){
                map.put(Integer.toString(i),Integer.toBinaryString(i));
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new AddThread(0));
        Thread t2 = new Thread(new AddThread(2));

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(map.size());
    }
}
