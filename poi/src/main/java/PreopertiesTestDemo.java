import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

/**
 * 读取properties中的内容
 * Created by lszhen on 2018/1/22.
 */
public class PreopertiesTestDemo {
    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        FileInputStream inputStream = new FileInputStream("/Users/lszhen/Documents/MIT/a.properties");
        properties.load(inputStream);

        Iterator<String> it = properties.stringPropertyNames().iterator();
        while (it.hasNext()){
            //获取key值
            String key = it.next();
            //获取value值
            System.out.println(key + ":"+properties.getProperty(key));
        }

        inputStream.close();
    }
}
