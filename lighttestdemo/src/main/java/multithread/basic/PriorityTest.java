package multithread.basic;

/**
 * 线程优先级
 * 一般情况下 都是高优先级的线程先执行
 * @author wzm
 * @create 2017-11-15-11:16
 */
public class PriorityTest {

    public static class HighPriorityThread extends Thread{
        static int count = 0;
        @Override
        public void run(){
            while (true){
                synchronized (PriorityTest.class){
                    count ++;
                    if (count > 10000000){
                        System.out.println("hightThread end!");
                        break;
                    }
                }
            }
        }
    }

    public static class LowPriorityThread extends Thread{
        static int count = 0;
        @Override
        public void run(){
            while (true){
                synchronized (PriorityTest.class){
                    count ++;
                    if (count > 10000000){
                        System.out.println("lowThread end!");
                        break;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread highThread = new HighPriorityThread();
        Thread lowThread  = new LowPriorityThread();
        highThread.setPriority(Thread.MAX_PRIORITY);
        lowThread.setPriority(Thread.MIN_PRIORITY);
        highThread.start();
        lowThread.start();
    }
}
