package java8.basic;

import org.junit.Assert;
import org.junit.Test;
/**
 * @author lszhen
 * @date 2018/3/18.
 */
public class DemoTest {

    /**
     * 测试基本的测试需要的配置，只需要加@Test即可
     */
    @Test
    public void test1(){
        System.out.println("123");
    }

    @Test
    public void tes2(){
        System.out.println(2.0-1.1);
        float a = (float) (2.0 -1.1);
        float b = 0.9f;
        Assert.assertSame(b,a);
    }

    @Test
    public void test3(){
        float c = 2.0f-1.1f;
        float b = 0.9f;
        Assert.assertSame(b,c);
    }
}
