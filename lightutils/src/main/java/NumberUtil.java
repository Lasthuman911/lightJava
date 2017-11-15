import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精确的浮点数运算，包括加减乘除和四舍五入。
 *
 * @author wzm
 * @create 2017-11-15-14:47
 */
public class NumberUtil {

    /**
     * 默认除法运算精度
     */
    private static final int DEF_DIV_SCALE = 10;
    /**
     * 这个类不能实例化
     */
    private NumberUtil() {
    }

    /**
     * 提供精确的加法运算
     *
     * @param v1
     * @param v2
     * @return
     * @author wzm
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算
     *
     * @param v1 减数
     * @param v2 被减数
     * @return
     * @author wzm
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算
     *
     * @param v1
     * @param v2
     * @return
     * @author wzm
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算,并指定带位数
     * @param v1
     * @param v2
     * @param scale
     * @return
     */
    public static double mulWithScale(double v1, double v2, int scale) {
        return round(mul(v1,v2),scale);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到小数点以后10位，以后的数字四舍五入
     *
     * @param v1
     * @param v2
     * @return
     * @author wzm
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 保留小数的位数
     * @return 除法后的结果
     * @author wzm
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理
     *
     * @param doubleValue  需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 4s5r 后的结果
     * @author wzm
     */
    public static double round(double doubleValue, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(doubleValue));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 只保留两位小数
     *
     * @param doubleValue double值
     * @return string类型的4s5r 结果
     */
    public static String roundWith2Digit(double doubleValue) {
        DecimalFormat df1 = new DecimalFormat("0.00");
        String str = df1.format(doubleValue);
        return str;
    }

}
