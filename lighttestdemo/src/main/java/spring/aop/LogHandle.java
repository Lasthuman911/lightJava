package spring.aop;

import org.springframework.stereotype.Component;

@Component
public class LogHandle {
    public void logBefore() {
        System.out.println("log before");
    }

    public void logAfter() {
        System.out.println("log after");
    }
}
