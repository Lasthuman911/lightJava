package normal;

import java.util.Arrays;

/**
 * Arrays.copyOf 测试
 * Created by lszhen on 2018/2/1.
 */
public class Test {
    public static void main(String[] args) {
        int[] array = new int[]{1,2,3};
        System.out.println("array = "+array);
        System.out.println(array.length);
        int len = array.length;
        int [] copyArray = Arrays.copyOf(array,len+1);
        System.out.println(copyArray.length);
        System.out.println("copy = "+copyArray);
        for (int i = 0;i<copyArray.length;i++){
            System.out.println(Arrays.toString(copyArray));
        }
/*        int a = 1<<4;
        System.out.println("a="+a);*/
    }
}
