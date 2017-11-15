package multithread.basic.volatite;

/**
 * volatite无法保证一些复合操作的原子性
 * TODO volatite保证原子性的例子？
 * @author wzm
 * @create 2017-11-15-10:12
 */
public class MulitiThreadLong {
    static volatile int i = 0;
    public static class PlusTask implements Runnable{

        @Override
        public void run() {
            for (int k = 0;k<1000;k++){
                i++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int threadNum = 20;
        Thread[] threads = new Thread[threadNum];
        for (int i = 0;i< threadNum ;i++){
            threads[i] = new Thread(new PlusTask());
            threads[i].start();
        }

        for (int i =0;i<threadNum;i++){
            threads[i].join();
        }
        //理论上 i = 1000*threadNum 但实际上，它总会小于这个数
        System.out.println("finale i = "+ i);
    }
}
