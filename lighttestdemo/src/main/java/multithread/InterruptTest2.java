package multithread;

/**
 * 中断异常触发条件的测试
 *
 * @author wzm
 * @create 2017-11-14-14:53
 */
public class InterruptTest2 {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(){
            @Override
            public void run(){
                while (true){
                    //判断是否中断，不清除当前线程中断状态，如果是静态的Thread.isInterrupted 则会清除当前中断状态
                    if (Thread.currentThread().isInterrupted()){
                        System.out.println("interruted state="+Thread.currentThread().isInterrupted());
                        break;
                    }
                    try {
                        Thread.sleep(5000);
                    }catch (InterruptedException e){
                        System.out.println("interrupt when sleep");
                        //sleep()抛异常后，会清除中断标记
                        System.out.println("interruted state="+Thread.currentThread().isInterrupted());
                        //如果不加处理，17行，下一次判断时就无法捕获这个中断,在此程序中，线程将一直休眠下去
                        Thread.currentThread().interrupt();
                    }
                }
                Thread.yield();
            }
        };
        t1.start();
        Thread.sleep(2000);
        //通知线程中断，并设置线程中断标志位，以Unsafe中的stopMe类似，但是功能更强
        //若在循环体中，出现了类似sleep或者wait这样的操作，只能通过中断来识别
        t1.interrupt();
    }
}
