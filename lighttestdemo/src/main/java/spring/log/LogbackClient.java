package spring.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackClient {
    private static final Logger logger = LoggerFactory.getLogger(LogbackClient.class);
    public static void main(String[] args) {
        logger.debug("成功了");
        logger.info("成功了");
        logger.warn("warn");
        logger.error("error");
    }
}
