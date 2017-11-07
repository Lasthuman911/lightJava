package proxy.dynamic;

import proxy.Sell;
import proxy.Vender;

import java.lang.reflect.Proxy;

/**
 * 动态代理测试
 * @author light
 */
public class DynamicClient {
    public static void main(String[] args) {
        DynamicProxy inter = new DynamicProxy(new Vender());
        //加上这句将会产生一个$Proxy0.class文件，这个文件即为动态生成的代理类文件
//        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        /*
            loader：定义了代理类的ClassLoder;
            interfaces：代理类实现的接口列表
            h：调用处理器，也就是我们上面定义的实现了InvocationHandler接口的类实例
         */
        Sell sell = (Sell) Proxy.newProxyInstance(Sell.class.getClassLoader(),new Class[]{Sell.class},inter);
       sell.ad();
       sell.sell();
    }
}
