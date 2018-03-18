package multithread.basic.interrupt;

/**
 * 线程中断测试
 * 线程中断并不会让线程立刻退出，它会给线程发出一个通知，告诉线程，有人希望你退出，目标线程收到通知后如何处理-这
 * 完全由目标线程自行决定
 *
 * @author wzm
 * @create 2017-11-14-14:20
 */
public class InterruptTest3 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("i am living");
                    Thread.yield();
                }
            }
        };
        t1.start();
        Thread.sleep(2000);
        //因为没有对中断做任何处理，t1不会停止执行
        t1.interrupt();
    }
}
