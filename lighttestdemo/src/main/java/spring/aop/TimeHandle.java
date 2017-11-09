package spring.aop;

import org.springframework.stereotype.Component;

/**
 * 定义横切关注点，即和各业务无关的通用的逻辑
 */
@Component
public class TimeHandle {
    public void printCurrentTime(){
        System.out.println("currentTime = "+System.currentTimeMillis()+" nanoTime = "+System.nanoTime());
    }
}
