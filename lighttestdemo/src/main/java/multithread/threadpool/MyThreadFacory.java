package multithread.threadpool;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadFactory;

/**
 * 完整的自定义线程工厂例子
 * Created by lszhen on 2018/1/31.
 */
public class MyThreadFacory implements ThreadFactory{
    private int counter;
    private String name;
//    private List<String> stats;

    public MyThreadFacory(String name) {
        counter = 0;
        this.name = name;
//        stats = new ArrayList<String>();
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r,name+"-Thread-"+counter);
        counter++;
//        stats.add(String.format("Created thread %d with name %s on %s\n",t.getId(),t.getName(),new Date()));
        String result = String.format("Created thread %d with name %s on %s\n",t.getId(),t.getName(),new Date());
        //todo 后面这里改为用log显示
        System.out.println(result);
        return t;
    }

   /* public String getStas(){
        StringBuffer buffer = new StringBuffer();
        Iterator<String> it = stats.iterator();
        while (it.hasNext()){
            buffer.append(it.next());
            buffer.append("\n");
        }
        return buffer.toString();
    }*/
}
