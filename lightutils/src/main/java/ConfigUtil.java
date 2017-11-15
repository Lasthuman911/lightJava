import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 配置文件获取值
 *
 * @author wzm
 * @create 2017-11-15-15:24
 */
public class ConfigUtil {
    public final static String CONFIG_FILE = "/conf.properties";
    private static long lastModified = 0L;
    private static File configFile = null;

    private static Logger log = LoggerFactory.getLogger(ConfigUtil.class);
    private static Properties props = new Properties();

    static {
        loadProperty();
    }

    /**
     * 装载配置文件
     *
     * @author wzm
     * @Date 2017年9月9日
     */
    private static void loadProperty() {
        try {
            String path = ConfigUtil.class.getResource(CONFIG_FILE).getFile();
            if (System.getProperty("os.name").startsWith("Windows")) {
                path = path.substring(1);
                path = path.replaceAll("%20", " ");
            }
            File f = new File(path);

            lastModified = f.lastModified();
            configFile = f;
            log.info("load config from: " + f.getAbsolutePath());
            props.load(new FileInputStream(f));
            (new ReloadThread()).start();
        } catch (Exception e) {
            log.error("load config falied!", e);
            System.exit(-1);
        }
    }

    private static void checkUpdate() {
        if (configFile != null) {
            long m = configFile.lastModified();
            if (m != lastModified) {
                lastModified = m;
                try {
                    Properties prop = new Properties();
                    prop.load(new FileInputStream(configFile));
                    props = prop;
                    log.info("reload config file:"
                            + configFile.getAbsolutePath());
                } catch (Exception e) {
                    log.error("failed to reload config file: "
                            + configFile.getAbsolutePath(), e);
                }
            }
        }
    }

    public static String getConfig(String name, String defaultValue) {
        String ret = props.getProperty(name);
        if (ret == null) {
            return defaultValue;
        } else {
            return ret.trim();
        }
    }

    public static String getConfig(String name) {
        return getConfig(name, null);
    }

    /**
     * 动态加载配置文件
     *
     * @version 1.0.0
     * @Date: 2017年9月9日
     * @author: wzm
     * @since JDK 1.7.0
     */
    static class ReloadThread extends Thread {
        @Override
        public void run() {
            log.info("update checking for config file: "
                    + configFile.getAbsolutePath());
            while (true) {
                if (configFile != null) {
                    long m = configFile.lastModified();
                    if (m != lastModified) {
                        lastModified = m;
                        try {
                            Properties prop = new Properties();
                            prop.load(new FileInputStream(configFile));
                            props = prop;
                            log.info("config file changed, reload: "
                                    + configFile.getAbsolutePath());
                        } catch (Exception e) {
                            log.error("failed to reload config file: "
                                    + configFile.getAbsolutePath(), e);
                        }
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (Exception e) {
                        log.error("", e);
                    }
                } else {
                    break;
                }
            }
        }
    }


    /**
     * 动态更新写入配置文件值
     *
     * @param key
     * @param value
     * @author wzm
     * @Date 2017年9月9日
     */
    public void writeConf(String key, String value) {
        checkUpdate();
        OutputStream fos = null;
        try {
            fos = new FileOutputStream(configFile);
            props.setProperty(key, value);
            props.store(fos, null);
            log.debug("Visit config file:" + configFile.getAbsolutePath() + " update (" + key + " = " + value + ") success");
        } catch (IOException e) {
            log.debug("Visit config file:" + configFile.getAbsolutePath() + " update (" + key + " = " + value + ") error", e);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
            }
        }
    }

    /**
     * 获取动态文件地址
     *
     * @return
     * @author wzm
     * @Date 2017年9月9日
     */
    public static String getPathOfProject() {

        String url = ConfigUtil.class.getClassLoader().getResource("")
                .toString();
        String reg = "file:(.+)WEB-INF";
        //String reg = "file:(.+)bin";
        Matcher mat = Pattern.compile(reg, Pattern.CASE_INSENSITIVE).matcher(
                url);
        if (mat.find()) {
            String path = mat.group(1);
            path = path.replaceAll("/", "\\" + File.separator);
            if (File.separator.equals("\\")){
                // windows
                return path.substring(1);
            }
            return path;
        }
        return null;
    }

}
