package multithread;

/**
 * wait 和 notify 方法的简单测试
 *
 * @author wzm
 * @create 2017-11-14-15:55
 */
public class WaitNotifyTest {
    final static Object obj = new Object();
    public static class T1 extends Thread{
        @Override
        public void run(){
            synchronized(obj){
                System.out.println(System.currentTimeMillis()+" T1 start");
                try{
                    System.out.println(System.currentTimeMillis()+" T1 wait for notify");
                    //在对象的实例上调用wait方法后，当前线程会在这个对象上等待
                    obj.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis() + " T1 end!");
            }
        }
    }

    public static class T2 extends Thread{
        @Override
        public void run(){
            synchronized(obj){
                System.out.println(System.currentTimeMillis()+" T2 start");
                //随机唤醒此对象上的一个线程，但并不代表监视器立马就释放了
                obj.notify();
                System.out.println(System.currentTimeMillis() + " T2 end!");
                try {
                    //整个代码执行完，才真正的释放对象监视器
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void main(String[] args) {
        Thread t1 = new T1();
        Thread t2 = new T2();
        t1.start();
        t2.start();
    }
}
