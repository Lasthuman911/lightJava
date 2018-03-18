package multithread.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * 最佳实践！
 * 网上很多分成100份小任务的都没真正的把此框架用好
 * RecursiveTask 是由返回值
 * RecursiveAction是无返回值的
 * Created by lszhen on 2017/11/20.
 */
public class CountTask extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 1000;
    private int start;
    private int end;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {

        int sum = 0;
        //拆分的条数：100000 ／ 1000 = 100
        boolean canCompute = (end-start)<=THRESHOLD;
        if (canCompute){
            for (int i=start;i<=end;i++){
                sum += i;
            }
        }else{

            int middle = (start + end)/2;
            CountTask leftTask = new CountTask(start,middle);
            CountTask rightTask = new CountTask(middle+1,end);
            //执行子任务
            leftTask.fork();
            rightTask.fork();

            int leftResult = (int) leftTask.join();
            int rightResult = (int) rightTask.join();

            sum = leftResult + rightResult;
        }

        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask task = new CountTask(1,100000);

        //执行任务
        Future<Integer> result = forkJoinPool.submit(task);
        try{
            //通过get得到最后的结果
            System.out.println(result.get());
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }
    }
}
