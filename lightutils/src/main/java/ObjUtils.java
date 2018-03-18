import java.util.Objects;

/**
 * Created by lszhen on 2018/1/24.
 */
public class ObjUtils {

    /**
     * 获取对象多个参数的组合hashCode值
     * @param values
     * @return
     */
    public static int getHashcode(Object... values){
        return Objects.hash(values);
    }
}
