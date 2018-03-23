package pattern.factory.simplefactory;

import java.lang.reflect.Type;

/**
 * @author lszhen
 * @date 2018/3/22.
 */
public class FruitFactory {

    private static final String packageName = "pattern.factory.simplefactory";

    public static Fruit getApple() {
        return new Apple();
    }

    public static Fruit getOrange() {
        return new Orange();
    }

    public static Fruit getFruit(String fruitName) throws IllegalAccessException, InstantiationException {
        if ("apple".equalsIgnoreCase(fruitName)) {
            return Apple.class.newInstance();
        } else if ("orange".equalsIgnoreCase(fruitName)) {
            return Orange.class.newInstance();
        } else {
            System.out.println("没有相应的水果供应");
            return null;
        }
    }

    public static Fruit getFruit2(String fruitName) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return (Fruit) Class.forName(packageName+"."+fruitName).newInstance();
    }

    public static Fruit getFruit3(Class clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return (Fruit) Class.forName(clazz.getName()).newInstance();
    }
}
