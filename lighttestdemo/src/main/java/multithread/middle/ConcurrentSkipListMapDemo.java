package multithread.middle;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 跳表 的简单demo
 * 应用场景：应用需要有序性，则选择
 * Created by lszhen on 2018/2/1.
 */
public class ConcurrentSkipListMapDemo {
    public static void main(String[] args) {
        Map<Integer,Integer> map = new ConcurrentSkipListMap<>();
        for (int i=0;i<30;i++){
            map.put(i,i);
        }

        for (Map.Entry<Integer,Integer> entry:map.entrySet()){
            System.out.println(entry.getKey());
        }
    }
}
