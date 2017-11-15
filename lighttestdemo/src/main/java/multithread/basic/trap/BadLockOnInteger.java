package multithread.basic.trap;

/**
 * 错误加锁例子
 *
 * @author wzm
 * @create 2017-11-15-13:57
 */
public class BadLockOnInteger implements Runnable{
    public static Integer i  = 0;
    static BadLockOnInteger instance = new BadLockOnInteger();
    @Override
    public void run() {
        for (int j = 0;j<1000000;j++){
            //重构 将i 改为instance
            synchronized (i){
                //i++的本质是：创建一个新的Integer对象，并将它的引用赋值给i，所以用i同步将会有问题
                i++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("final i = "+ i);
    }
}
