package multithread.basic.create;

/**
 * 实现Runnable接口来创建线程，将实例传入Thread，避免了重载Thread.run()
 */
public class CreateThreadTest2 implements Runnable {
    public static void main(String[] args) {
        Thread t1 = new Thread(new CreateThreadTest2());
        t1.start();
    }

    @Override
    public void run() {
        System.out.println("impl Runnable");
    }
}
