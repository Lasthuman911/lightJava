package spring.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class LogbackClient {
    private static final Logger logger = LoggerFactory.getLogger(LogbackClient.class);
    public static void main(String[] args) {
        //使用MDC输出用户等信息,可以把获取user的方法直接定义在过滤器中，然后在配置文件中配置获取ip的显示就可以了
        MDCInfo.setUser("lszhen");
        logger.debug("成功了");
        logger.info("成功了");
        logger.warn("warn");
        logger.error("error");
    }
}
