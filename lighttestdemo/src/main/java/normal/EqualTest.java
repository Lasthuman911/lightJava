package normal;

/**
 * 查看equals方法的具体实现
 * Created by lszhen on 2017/11/30.
 */
public class EqualTest {
    public static void main(String[] args) {
        Object a = new Object();
        System.out.println(a.equals("1"));
        System.out.println("1".equals("2"));
    }
}
