package pattern.factory.simplefactory;


/**
 * @author lszhen
 * @date 2018/3/22.
 */
public class ClientMain {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
//        ori();
//        oriR1();
//        oriR2withFactory();
//        oriR3withFactory();
//        oriR4withFactory();
        oriR5WithFactory();
       /* Fruit apple = FruitFactory.getFruit3(Apple.class);
        Fruit orange = FruitFactory.getFruit3(Orange.class);
        apple.getName();
        orange.getName();*/

    }

    private static void oriR5WithFactory() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Fruit apple = FruitFactory.getFruit2("Apple");
        Fruit orange = FruitFactory.getFruit2("Orange");
        apple.getName();
        orange.getName();
    }

    private static void oriR4withFactory() throws IllegalAccessException, InstantiationException {
        Fruit apple = FruitFactory.getFruit("AppLE");
        Fruit orange = FruitFactory.getFruit("OranGe");
        apple.getName();
        orange.getName();
    }

     private static void oriR3withFactory() {
        Fruit apple = FruitFactory.getApple();
        apple.getName();
        Fruit orange = FruitFactory.getOrange();
        orange.getName();
    }

   /* private static void oriR2withFactory() {
        Fruit apple = new FruitFactory().getApple();
        apple.getName();
        Fruit orange = new FruitFactory().getOrange();
        orange.getName();
    }*/

    /**
     * 添加多态
     */
    private static void oriR1() {
        Fruit apple = new Apple();
        apple.getName();
        Fruit orange = new Orange();
        orange.getName();
    }

    private static void ori() {
        Apple apple = new Apple();
        apple.getName();
        Orange orange = new Orange();
        orange.getName();
    }
}
