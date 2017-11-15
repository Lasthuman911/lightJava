package multithread.basic;

/**
 * 线程组相关内容
 *
 * @author wzm
 * @create 2017-11-15-10:42
 */
public class ThreadGroupTest implements Runnable {

    @Override
    public void run() {
        String groupAndName = Thread.currentThread().getThreadGroup().getName()
                + "-" + Thread.currentThread().getName();
        while (true){
            System.out.println("I am "+ groupAndName);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ThreadGroup threadGroup = new ThreadGroup("PrintGroup");
        //将线程和线程组关联
        Thread HttpThread = new Thread(threadGroup,new ThreadGroupTest(),"HttpThread");
        Thread FTPThread = new Thread(threadGroup,new ThreadGroupTest(),"FTPThread");
        HttpThread.start();
        FTPThread.start();
        //获取活动线程的总数，由于线程是动态的，所以它只是一个估计值，无法确定精确
        System.out.println(threadGroup.activeCount());
        //打印线程组中所有线程信息，方便调试
        threadGroup.list();

    }
}
