package java8.basic;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * 类似Junit3 中的方式
 * 问题： 不知道怎么执行
 * 问题解决：
 * 1.必须是import junit.framework.Test; 不能是org.junit.Test下的Test包
 * 2.public static Test suite()必须完全一样，不能用其他方法名
 * @author lszhen
 * @date 2018/3/18.
 */
public class TestSuitMain2{
    public static Test suite(){
        TestSuite suit = new TestSuite();
        suit.addTest(new JUnit4TestAdapter(DemoTest.class));
        suit.addTest(new JUnit4TestAdapter(Person.class));

        return suit;
    }
}
