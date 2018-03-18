package java8.basic;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 组合各个类进行全部测试
 * @author lszhen
 * @date 2018/3/18.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({DemoTest.class,Person.class})
public class TestSuitMain {
}
