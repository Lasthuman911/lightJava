package multithread.basic;

/**
 * 守护线程
 *
 * @author wzm
 * @create 2017-11-15-11:05
 */
public class DaemonTest {
    public static class Daemon extends Thread{
        @Override
        public void run(){
            while (true){
                System.out.println("i am alive");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Daemon();
        //setDaemon要放在start之前，否则报错，会被当作用户线程
        thread.setDaemon(true);
        thread.start();

        //因为thread是守护线程，所以在主线程结束后，将会停止打印 i am alive
        Thread.sleep(2000);
        System.out.println("main end!");
    }
}
