package multithread.middle;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * cas ABA的问题模拟，消费20 充值20 导致m不变
 * 改为带时间戳
 * Created by lszhen on 2018/2/2.
 */
public class AtomicreferenceDemoRe1 {
    static AtomicStampedReference<Integer> money = new AtomicStampedReference<>(19,0);

    public static void main(String[] args) {
        //模拟多个线程同时更新后台数据库，为用户充值
        for (int i= 0;i<3;i++){
            //获取时间戳
            final int timestamp = money.getStamp();
            new Thread(){
                @Override
                public void run() {
                    while (true){
                        while (true){
                            Integer m = money.getReference();
                            if (m<20){
                                if (money.compareAndSet(m,m+20,timestamp,timestamp+1)){
                                    System.out.println("余额小于20，充值成功，余额："+money.getReference()+"元");
                                    break;
                                }
                            }else {
                                break;
                            }
                        }
                    }
                }
            }.start();
        }


        //模拟用户消费
        new Thread(){
            @Override
            public void run() {
                for (int i = 0;i<100;i++){
                    int timstamp = money.getStamp();
                    while (true){
                        Integer m = money.getReference();
                        if (m >10){
                            System.out.println("大于10元");
                            if (money.compareAndSet(m,m-10,timstamp,timstamp+1)){
                                System.out.println("成功消费10元，余额："+money.getReference()+"元");
                                break;
                            }
                        }else {
                            System.out.println("没由足够的金额");
                            break;
                        }
                    }try{
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
