package multithread.basic.volatite;

/**
 * volatite保证可见性和有序性的例子
 *
 * @author wzm
 * @create 2017-11-15-10:22
 */
public class VolatiteTest2 {
    //若不加volatitle run中的ready永远都是false，无法知道主线程已经修改了ready的值-jvm server模式下
    //在jvm client模式下 jit没有足够的优化，ReadThread 可以发现主线程ready的变化
    private volatile static boolean ready;
    private static int number;

    public static class ReadThread extends Thread{
        @Override
        public void run(){
            while (!ready){
                System.out.println("not ready");
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReadThread().start();
        Thread.sleep(100);
        number = 42;
        ready = true;
        Thread.sleep(1000);
    }
}
