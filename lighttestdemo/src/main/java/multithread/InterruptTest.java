package multithread;

/**
 * 线程中断测试
 * 线程中断并不会让线程立刻退出，它会给线程发出一个通知，告诉线程，有人希望你退出，目标线程收到通知后如何处理-这
 * 完全由目标线程自行决定
 *
 * @author wzm
 * @create 2017-11-14-14:20
 */
public class InterruptTest {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(){
          @Override
          public void run(){
              while (true){
                  //判断是否中断，不清除当前线程中断状态，如果是静态的Thread.isInterrupted 则会清除当前中断状态
                  if (Thread.currentThread().isInterrupted()){
                      System.out.println("interruted state="+Thread.currentThread().isInterrupted());
                      System.out.println("thread.interruted()="+Thread.interrupted());
                      System.out.println("interruted state="+Thread.currentThread().isInterrupted());
                      break;
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
