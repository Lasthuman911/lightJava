package multithread.basic.create;

/**
 * 通过继承Thread来实现线程的创建，此例用了匿名内部类
 */
public class CreateThreadTest1 {
    public static void main(String[] args) {
        Thread t1 = new Thread(){
            @Override
            public void run(){
                System.out.println("hello");
            }
        };
        t1.start();
//        t1.run();
    }
}
