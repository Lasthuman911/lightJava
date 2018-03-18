package normal.obj;

import java.util.Objects;

/**
 * 查看equals方法的具体实现
 * Created by lszhen on 2017/11/30.
 */
public class EqualTest {
    public static void main(String[] args) {
        Object a = new Object();
        //Obj 比较的是实际的地址
        System.out.println(a.equals("1"));

        //String equlas重写了
        System.out.println("1".equals("2"));
        System.out.println("1".equals("1"));

        //String 的equals 比较的是字面值
        String aString = "abc";
        String bString = new String("abc");
        String cString = new String("abc");
        System.out.println("aString.equals(bString)?= " + aString.equals(bString));
        System.out.println("aString == bString?= " + (aString == bString));
        System.out.println("bString == cString?= "+(bString == cString));

        //equals值相同，则对象的hashCode值必须相同
        System.out.println("aString.hashCode() = "+aString.hashCode());
        System.out.println("bString.hashCode() = "+bString.hashCode());
        System.out.println("cString.hashCode() = "+cString.hashCode());

        //查看StringBulider的hashCode方法,没有重写，都是直接用Object中的
        StringBuilder aBulider = new StringBuilder(aString);
        StringBuilder bBulider = new StringBuilder(aString);
        System.out.println("aBulider.equals(bBulider) = "+aBulider.equals(bBulider));
        System.out.println("aBulider.hashCode() = "+aBulider.hashCode());
        System.out.println("bBulider.hashcode() = "+bBulider.hashCode());

        //组合多个参数的散列值的方法
        int hashResult = Objects.hash("1","2");
        System.out.println("hashResult = "+hashResult);
    }
}
