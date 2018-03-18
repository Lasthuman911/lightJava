package java8;

import java.util.Arrays;
import java.util.function.IntConsumer;

/**
 * Created by lszhen on 2018/2/6.
 */
public class NewForRe1 {
    static int[] arr = {1, 2, 3, 4, 5, 6, 7, 8};

    public static void main(String[] args) {
        //forEach函数的参数可以从上下文推导，由编译器去做
        Arrays.stream(arr).forEach((final int x) -> {
            System.out.println(x);
        });
    }
}
/***
 1
 2
 3
 4
 5
 6
 7
 8
 ***/
