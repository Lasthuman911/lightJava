package java8.basic;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * 应用场景：
 * 为测试方法输入参数，甚至是批量指定多个待测参数。这时Parameterized这个Runner就能满足我们的要求
 * @author lszhen
 * @date 2018/3/18.
 */
@RunWith(Parameterized.class)
public class TestGenerateParams {
    private String a;
    private String b;
    private String c;

    /**
     * 不能有多个构造方法，否则会报错
     * java.lang.IllegalArgumentException: Test class can only have one constructor
     * @param a
     * @param b
     * @param c
     */
    public TestGenerateParams(String a, String b, String c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

/*    public TestGenerateParams(String a, String b) {
        this.a = a;
        this.b = b;
    }*/

    /**
     * 这里返回的必须是一个可迭代的数组，必须是public static
     */
    @Parameterized.Parameters
    public static Collection<String[]> getP() {
        return Arrays.asList(new String[][]{
                {"a","b","ab"},
                {"a","b","ab1"}
        });
    }

    @Test
    public void test(){
        Assert.assertEquals(re(a,b),c);
    }

    private String re(String a, String b) {
        return a+b;
    }
}
