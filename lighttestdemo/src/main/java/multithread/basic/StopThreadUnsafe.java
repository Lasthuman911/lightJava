package multithread.basic;

/**
 * 使用不安全的stop方法终止线程，容易产生不一致的情况
 * 重构后 添加一个stopme 状态来避免不一致情况
 *
 * @author wzm
 */
public class StopThreadUnsafe {
    private static User user = new User();

    public static class User {
        private int id;
        private String name;

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * 使用static定义内部类为外部类
     * <p>
     * 知识点：静态类和普通内部类的区别：
     * 如果你不需要内部类对象与其外围类对象之间有联系，那你可以将内部类声明为static。这通常称为嵌套类(nested class)
     * Static Nested Class是被声明为静态（static）的内部类，它可以不依赖于外部类实例被实例化。
     * 而通常的内部类需要在外部类实例化后才能实例化。
     * 想要理解static应用于内部类时的含义，你就必须记住，普通的内部类对象隐含地保存了一个引用，指向创建它的外围类对象
     * 然而，当内部类是static的时，就不是这样了。嵌套类意味着：
     * 1. 嵌套类的对象，并不需要其外围类的对象。
     * 2. 不能从嵌套类的对象中访问非静态的外围类对象。
     */
    public static class ChangeObjectThread extends Thread {
        /**
         * 通过添加一个标志位，来判断线程是否需要stop
         */
        volatile boolean stopme = false;

        public void stopMe() {
            stopme = true;
        }

        @Override
        public void run() {
            while (true) {
                if (stopme) {
                    System.out.println("exixt by stopMe");
                    break;
                }
                synchronized (user) {
                    int userId = (int) (System.currentTimeMillis() / 1000);
                    user.setId(userId);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    user.setName(String.valueOf(userId));
                }
                Thread.yield();
            }
        }
    }

    public static class ReadObjceThread extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (user) {
                    if (user.getName() != null) {
                        if (user.getId() != Integer.parseInt(user.getName())) {
                            System.out.println(user);
                        }
                    }
                }
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReadObjceThread().start();
        while (true) {
//            Thread changeThread = new ChangeObjectThread();
            //重构后，如果要用stopMe方法 不能用Thread来声明，因为Thread中并没有stopMe方法
            ChangeObjectThread changeThread = new ChangeObjectThread();
            changeThread.start();
            Thread.sleep(150);
            changeThread.stopMe();
        }
    }
}
