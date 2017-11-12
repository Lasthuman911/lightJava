package spring.log;

import org.slf4j.MDC;

/**
 * Created by lszhen on 2017/11/11.
 */
public class MDCInfo {
    public static final String KEY_USER = "user";

    public static void setUser(String userId){
        MDC.put(KEY_USER,userId);
    }
}
